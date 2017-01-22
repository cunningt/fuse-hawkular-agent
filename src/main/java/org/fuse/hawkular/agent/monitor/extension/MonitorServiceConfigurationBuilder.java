/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fuse.hawkular.agent.monitor.extension;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.management.MalformedObjectNameException;

import org.hawkular.agent.model.AvailJmxType;
import org.hawkular.agent.model.AvailSetJmxType;
import org.hawkular.agent.model.DiagnosticsType;
import org.hawkular.agent.model.ManagedServersType;
import org.hawkular.agent.model.MetricJmxType;
import org.hawkular.agent.model.MetricSetJmxType;
import org.hawkular.agent.model.OperationJmxType;
import org.hawkular.agent.model.PlatformType;
import org.hawkular.agent.model.RemoteJmxType;
import org.hawkular.agent.model.ResourceConfigJmxType;
import org.hawkular.agent.model.ResourceTypeJmxType;
import org.hawkular.agent.model.ResourceTypeSetJmxType;
import org.hawkular.agent.model.StorageType;
import org.hawkular.agent.model.SubsystemType;
import org.hawkular.agent.monitor.api.Avail;
import org.hawkular.agent.monitor.dynamicprotocol.MetricMetadata;
import org.hawkular.agent.monitor.dynamicprotocol.MetricSetMetadata;
import org.hawkular.agent.monitor.extension.JMXMetricAttributes;
import org.hawkular.agent.monitor.extension.PlatformAttributes;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.DiagnosticsConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.DiagnosticsReportTo;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.DynamicEndpointConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.DynamicProtocolConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.EndpointConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.GlobalConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.ProtocolConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.StorageAdapterConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.StorageReportTo;
import org.hawkular.agent.monitor.inventory.AttributeLocation;
import org.hawkular.agent.monitor.inventory.AvailType;
import org.hawkular.agent.monitor.inventory.ConnectionData;
import org.hawkular.agent.monitor.inventory.ID;
import org.hawkular.agent.monitor.inventory.Interval;
import org.hawkular.agent.monitor.inventory.MetricType;
import org.hawkular.agent.monitor.inventory.Name;
import org.hawkular.agent.monitor.inventory.Operation;
import org.hawkular.agent.monitor.inventory.ResourceConfigurationPropertyType;
import org.hawkular.agent.monitor.inventory.ResourceType;
import org.hawkular.agent.monitor.inventory.ResourceType.Builder;
import org.hawkular.agent.monitor.inventory.TypeSet;
import org.hawkular.agent.monitor.inventory.TypeSet.TypeSetBuilder;
import org.hawkular.agent.monitor.inventory.TypeSets;
import org.hawkular.agent.monitor.log.AgentLoggers;
import org.hawkular.agent.monitor.log.MsgLogger;
import org.hawkular.agent.monitor.protocol.dmr.DMRNodeLocation;
import org.hawkular.agent.monitor.protocol.jmx.JMXNodeLocation;
import org.hawkular.agent.monitor.protocol.platform.Constants;
import org.hawkular.agent.monitor.protocol.platform.Constants.PlatformMetricType;
import org.hawkular.agent.monitor.protocol.platform.Constants.PlatformResourceType;
import org.hawkular.agent.monitor.protocol.platform.PlatformNodeLocation;
import org.hawkular.agent.monitor.protocol.platform.PlatformPath;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.client.helpers.MeasurementUnit;

/**
 * Builds a {@link MonitorServiceConfiguration} object from the service's model.
 */
public class MonitorServiceConfigurationBuilder {

    private ProtocolConfiguration.Builder<JMXNodeLocation> jmxConfigBuilder;
    private ProtocolConfiguration.Builder<PlatformNodeLocation> platformConfigBuilder;

    private DiagnosticsConfiguration diagnostics;
    private StorageAdapterConfiguration storageAdapter;

    private GlobalConfiguration globalConfiguration;

    public MonitorServiceConfigurationBuilder(SubsystemType config) throws Exception {
        this.globalConfiguration = determineGlobalConfig(config);
        this.storageAdapter = determineStorageAdapterConfig(config);
        this.diagnostics = determineDiagnosticsConfig(config);

        jmxConfigBuilder = ProtocolConfiguration.builder();
        platformConfigBuilder = ProtocolConfiguration.builder();

        TypeSets.Builder<JMXNodeLocation> jmxTypeSetsBuilder = TypeSets.builder();

        determineMetricSetJmx(config, jmxTypeSetsBuilder);
        determineAvailSetJmx(config, jmxTypeSetsBuilder);

        // make sure to call this AFTER the metric sets and avail sets have been determined
        determineResourceTypeSetJmx(config, jmxTypeSetsBuilder);

        jmxConfigBuilder.typeSets(jmxTypeSetsBuilder.build());

        TypeSets<PlatformNodeLocation> platformTypeSets = buildPlatformTypeSets(config);
        platformConfigBuilder.typeSets(platformTypeSets);
        if (!platformTypeSets.isDisabledOrEmpty()) {
            String machineId = determinePlatformMachineId(config);
            EndpointConfiguration endpoint = new EndpointConfiguration("platform", true, null, null, null, Avail.DOWN,
                    null, null, null, Collections.singletonMap(Constants.MACHINE_ID.toString(), machineId));
            platformConfigBuilder.endpoint(endpoint);
        }

        // make sure to call this AFTER the resource type sets have been determined
        this.determineManagedServers(config);

    }

    public MonitorServiceConfiguration build() {
        return new MonitorServiceConfiguration(globalConfiguration,
                diagnostics, storageAdapter, null, jmxConfigBuilder.build(),
                platformConfigBuilder.build(), null);
    }

    private static org.hawkular.metrics.client.common.MetricType getMetricType(String metricTypeStr) {
        if (metricTypeStr == null) {
            return org.hawkular.metrics.client.common.MetricType.GAUGE;
        } else {
            return org.hawkular.metrics.client.common.MetricType.valueOf(metricTypeStr.toUpperCase(Locale.ENGLISH));
        }
    }

    private static MeasurementUnit getMeasurementUnit(String metricUnitsStr) {
        if (metricUnitsStr == null) {
            return MeasurementUnit.NONE;
        } else {
            return MeasurementUnit.valueOf(metricUnitsStr.toUpperCase(Locale.ENGLISH));
        }
    }

    private static TimeUnit getTimeUnit(String metricTimeUnitsStr) {
        return TimeUnit.valueOf(metricTimeUnitsStr.toUpperCase());
    }

    private static void determineMetricSetJmx(org.hawkular.agent.model.SubsystemType config,
            TypeSets.Builder<JMXNodeLocation> typeSetsBuilder)
            throws Exception {

        boolean enabled = false;

        List<MetricSetJmxType>metricSetsList = config.getMetricSetJmx();
        for (org.hawkular.agent.model.MetricSetJmxType metricSetProperty : metricSetsList) {
            String metricSetName = metricSetProperty.getName();
            if (metricSetName.indexOf(',') > -1) {
                //log.warnCommaInName(metricSetName);
            }

            boolean metricEnabled = (metricSetProperty.isEnabled() != null) ? metricSetProperty.isEnabled() : false;
            TypeSetBuilder<MetricType<JMXNodeLocation>> typeSetBuilder = TypeSet
                    .<MetricType<JMXNodeLocation>> builder()
                    .name(new Name(metricSetName))
                    .enabled(metricEnabled);

            List<MetricJmxType> metricsList = metricSetProperty.getMetricJmx();
            for (org.hawkular.agent.model.MetricJmxType metricProperty : metricsList) {
                String metricId = metricSetName + "~" + metricProperty.getName();
                String metricName = metricProperty.getName();

                String objectName = metricProperty.getObjectName();
                String metricIdTemplate = metricProperty.getMetricIdTemplate();

                Map<String, String> metricTags = getMapFromString(metricProperty.getMetricTags());

                try {
                    AttributeLocation<JMXNodeLocation> location = new AttributeLocation<>(
                                    new JMXNodeLocation(objectName),
                                    metricProperty.getAttribute());

                    String metricType = (metricProperty.getMetricType() != null) ? metricProperty.getMetricType().value() : null;
                    TimeUnit timeUnit = null;
                    if (metricProperty.getTimeUnits() == null) {
                    	timeUnit = null;
                    } else {
                        timeUnit = getTimeUnit(metricProperty.getTimeUnits().toString());
                    }
                    
                    MeasurementUnit measurementUnit = null;
                    if (metricProperty.getMetricUnits() == null) {
                    	measurementUnit = MeasurementUnit.NONE;
                    } else { 
                        measurementUnit = getMeasurementUnit(metricProperty.getMetricUnits().toString());
                    }
                    MetricType<JMXNodeLocation> metric = new MetricType<JMXNodeLocation>(
                                    new ID(metricId),
                                    new Name(metricName),
                                    location,
                                    new Interval(metricProperty.getInterval().intValue(),
                                    		timeUnit),
                                    measurementUnit,
                                    getMetricType(metricType),
                                    metricIdTemplate,
                                    metricTags);
                    typeSetBuilder.type(metric);
                } catch (MalformedObjectNameException e) {
                    //log.warnMalformedJMXObjectName(objectName, e);
                }
            }

            TypeSet<MetricType<JMXNodeLocation>> typeSet = typeSetBuilder.build();
            enabled = enabled || !typeSet.isDisabledOrEmpty();
            typeSetsBuilder.metricTypeSet(typeSet);
       }
   }

    private static void determineAvailSetJmx(org.hawkular.agent.model.SubsystemType config,
            TypeSets.Builder<JMXNodeLocation> typeSetsBuilder)
            throws Exception {

        boolean enabled = false;

        List<AvailSetJmxType> availSetsList = config.getAvailSetJmx();
        for (org.hawkular.agent.model.AvailSetJmxType availSetProperty : availSetsList) {
            String availSetName = availSetProperty.getName();
            if (availSetName.indexOf(',') > -1) {
                //log.warnCommaInName(availSetName);
            }

            TypeSetBuilder<AvailType<JMXNodeLocation>> typeSetBuilder = TypeSet
                .<AvailType<JMXNodeLocation>> builder() //
                .name(new Name(availSetName)) //
                .enabled(availSetProperty.isEnabled());
            List<AvailJmxType> availsList = availSetProperty.getAvailJmx();
            for (org.hawkular.agent.model.AvailJmxType availProperty : availsList) {
                String availId = availSetName + "~" + availProperty.getName();
                String availName = availProperty.getName();
                String objectName = availProperty.getObjectName();
                String metricIdTemplate = availProperty.getMetricIdTemplate();
                Map<String, String> metricTags = getMapFromString(availProperty.getMetricTags());

                try {
                    AttributeLocation<JMXNodeLocation> location = new AttributeLocation<>(
                        new JMXNodeLocation(objectName),
                        availProperty.getAttribute());
                        AvailType<JMXNodeLocation> avail = null;

                        TimeUnit timeunit =  getTimeUnit(availProperty.getTimeUnits().name());
                        Interval interval = new Interval(availProperty.getInterval().intValue(), timeunit);

                        avail = new AvailType<JMXNodeLocation> (
                                new ID(availId),
                                new Name(availName),
                                location,
                                interval,
                                Pattern.compile(availProperty.getUpRegex()),
//                              Pattern.compile(availProperty.getUpRegex(), context, JMXAvailAttributes.UP_REGEX),
                                metricIdTemplate,
                                metricTags);
                        typeSetBuilder.type(avail);
                } catch (MalformedObjectNameException e) {
                    //log.warnMalformedJMXObjectName(objectName, e);
                }

                TypeSet<AvailType<JMXNodeLocation>> typeSet = typeSetBuilder.build();
                enabled = enabled || !typeSet.isDisabledOrEmpty();
                typeSetsBuilder.availTypeSet(typeSet);
                }
            }
        if (!enabled) {
            //log.infoNoEnabledAvailsConfigured("JMX");
        }
    }

    private static String determinePlatformMachineId(org.hawkular.agent.model.SubsystemType config)
            throws OperationFailedException {
        PlatformType platform = config.getPlatform();
        if (platform == null) {
            return null; // not monitoring platform, so we don't collect machine ID
        } else {
            return platform.getMachineId();
        }
    }

    private static TypeSets<PlatformNodeLocation> buildPlatformTypeSets(org.hawkular.agent.model.SubsystemType config)
            throws OperationFailedException {

        // assume they are disabled unless configured otherwise

        if (config.getPlatform() == null) {
            //log.infoNoPlatformConfig();
            return TypeSets.empty();
        }

        PlatformType platform = config.getPlatform();

        // since platform monitoring is enabled, we will always have at least the root OS type
        final Name osName = PlatformResourceType.OPERATING_SYSTEM.getResourceTypeName();
        TypeSets.Builder<PlatformNodeLocation> typeSetsBuilder = TypeSets.builder();
        Builder<?, PlatformNodeLocation> rootTypeBldr = ResourceType.<PlatformNodeLocation> builder()
                .name(osName)
                .location(new PlatformNodeLocation(
                        PlatformPath.builder().any(PlatformResourceType.OPERATING_SYSTEM).build()))
                .resourceNameTemplate("%s");

        ResourceConfigurationPropertyType<PlatformNodeLocation> machineIdConfigType = //
                new ResourceConfigurationPropertyType<>(ID.NULL_ID,
                        new Name(Constants.MACHINE_ID),
                        new AttributeLocation<>(
                                new PlatformNodeLocation(PlatformPath.empty()), Constants.MACHINE_ID));

        rootTypeBldr.resourceConfigurationPropertyType(machineIdConfigType);

        // OS top-level metrics

        int platformInterval = (platform.getInterval() != null) ? platform.getInterval() : 10;
        String timeUnitStr = (platform.getTimeUnits() != null) ? platform.getTimeUnits().name() : TimeUnit.SECONDS.name();
        Interval osInterval = new Interval( platformInterval,
            getTimeUnit(timeUnitStr));

        MetricType<PlatformNodeLocation> systemCpuLoad = new MetricType<PlatformNodeLocation>(
                PlatformMetricType.OS_SYS_CPU_LOAD.getMetricTypeId(),
                PlatformMetricType.OS_SYS_CPU_LOAD.getMetricTypeName(),
                new AttributeLocation<>(
                        new PlatformNodeLocation(PlatformPath.empty()),
                        PlatformMetricType.OS_SYS_CPU_LOAD.getMetricTypeId().getIDString()),
                osInterval,
                MeasurementUnit.PERCENTAGE,
                org.hawkular.metrics.client.common.MetricType.GAUGE,
                null,
                null);

        MetricType<PlatformNodeLocation> systemLoadAverage = new MetricType<PlatformNodeLocation>(
                PlatformMetricType.OS_SYS_LOAD_AVG.getMetricTypeId(),
                PlatformMetricType.OS_SYS_LOAD_AVG.getMetricTypeName(),
                new AttributeLocation<>(
                        new PlatformNodeLocation(PlatformPath.empty()),
                        PlatformMetricType.OS_SYS_LOAD_AVG.getMetricTypeId().getIDString()),
                osInterval,
                MeasurementUnit.NONE,
                org.hawkular.metrics.client.common.MetricType.GAUGE,
                null,
                null);

        MetricType<PlatformNodeLocation> processCount = new MetricType<PlatformNodeLocation>(
                PlatformMetricType.OS_PROCESS_COUNT.getMetricTypeId(),
                PlatformMetricType.OS_PROCESS_COUNT.getMetricTypeName(),
                new AttributeLocation<>(
                        new PlatformNodeLocation(PlatformPath.empty()),
                        PlatformMetricType.OS_PROCESS_COUNT.getMetricTypeId().getIDString()),
                osInterval,
                MeasurementUnit.NONE,
                org.hawkular.metrics.client.common.MetricType.GAUGE,
                null,
                null);

        TypeSet<MetricType<PlatformNodeLocation>> osMetrics = TypeSet
                .<MetricType<PlatformNodeLocation>> builder()
                .name(PlatformResourceType.OPERATING_SYSTEM.getResourceTypeName())
                .type(systemCpuLoad)
                .type(systemLoadAverage)
                .type(processCount)
                .build();

        typeSetsBuilder.metricTypeSet(osMetrics);

        rootTypeBldr.metricSetName(osMetrics.getName());
        populateMetricAndAvailTypesForResourceType(rootTypeBldr, typeSetsBuilder);

        ResourceType<PlatformNodeLocation> rootType = rootTypeBldr.build();
        TypeSet<ResourceType<PlatformNodeLocation>> rootTypeSet = TypeSet
                .<ResourceType<PlatformNodeLocation>> builder()
                .enabled(true)
                .name(osName)
                .type(rootType)
                .build();

        typeSetsBuilder.resourceTypeSet(rootTypeSet);

        // now add children types if they are enabled

        org.hawkular.agent.model.PlatformMetricType pfmt = platform.getFileStores();
        boolean enabled = pfmt.isEnabled();
        if (enabled) {
            Interval interval = new Interval(pfmt.getInterval().intValue(),
                TimeUnit.valueOf(pfmt.getTimeUnits().name().toUpperCase()));

            MetricType<PlatformNodeLocation> usableSpace = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.FILE_STORE_USABLE_SPACE.getMetricTypeId(),
                    PlatformMetricType.FILE_STORE_USABLE_SPACE.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.FILE_STORE_USABLE_SPACE.getMetricTypeId().getIDString()),
                    interval,
                    MeasurementUnit.BYTES,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            MetricType<PlatformNodeLocation> totalSpace = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.FILE_STORE_TOTAL_SPACE.getMetricTypeId(),
                    PlatformMetricType.FILE_STORE_TOTAL_SPACE.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.FILE_STORE_TOTAL_SPACE.getMetricTypeId().getIDString()),
                    interval,
                    MeasurementUnit.BYTES,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            TypeSet<MetricType<PlatformNodeLocation>> fileStoreMetrics = TypeSet
                    .<MetricType<PlatformNodeLocation>> builder()
                    .name(PlatformResourceType.FILE_STORE.getResourceTypeName())
                    .type(usableSpace)
                    .type(totalSpace)
                    .build();


            typeSetsBuilder.metricTypeSet(fileStoreMetrics);

            PlatformNodeLocation fileStoreLocation = new PlatformNodeLocation(
                    PlatformPath.builder().any(PlatformResourceType.FILE_STORE).build());
            Builder<?, PlatformNodeLocation> fileStoreBldr = ResourceType.<PlatformNodeLocation> builder()
                       .name(PlatformResourceType.FILE_STORE.getResourceTypeName())
                       .location(fileStoreLocation)
                       .resourceNameTemplate(PlatformResourceType.FILE_STORE.getResourceTypeName().getNameString() + " [%s]")
                       .parent(rootType.getName())
                       .metricSetName(fileStoreMetrics.getName());

            populateMetricAndAvailTypesForResourceType(fileStoreBldr, typeSetsBuilder);

            ResourceType<PlatformNodeLocation> fileStore = fileStoreBldr.build();
            TypeSet<ResourceType<PlatformNodeLocation>> typeSet = TypeSet
                    .<ResourceType<PlatformNodeLocation>> builder()
                    .name(PlatformResourceType.FILE_STORE.getResourceTypeName())
                    .type(fileStore)
                    .build();

            typeSetsBuilder.resourceTypeSet(typeSet);
        }

        org.hawkular.agent.model.PlatformMetricType memoryMetric = platform.getMemory();
        boolean memEnabled = memoryMetric.isEnabled();
        if (memEnabled) {
            Interval interval = new Interval(memoryMetric.getInterval().intValue(),
                            TimeUnit.valueOf(memoryMetric.getTimeUnits().name()));

            MetricType<PlatformNodeLocation> available = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.MEMORY_AVAILABLE.getMetricTypeId(),
                    PlatformMetricType.MEMORY_AVAILABLE.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.MEMORY_AVAILABLE.getMetricTypeId().getIDString()),
                    interval,
                    MeasurementUnit.BYTES,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            MetricType<PlatformNodeLocation> total = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.MEMORY_TOTAL.getMetricTypeId(),
                    PlatformMetricType.MEMORY_TOTAL.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.MEMORY_TOTAL.getMetricTypeId().getIDString()),
                    interval,
                    MeasurementUnit.BYTES,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            TypeSet<MetricType<PlatformNodeLocation>> memoryMetrics = TypeSet
                    .<MetricType<PlatformNodeLocation>> builder()
                    .name(PlatformResourceType.MEMORY.getResourceTypeName())
                    .type(available)
                    .type(total)
                    .build();


            typeSetsBuilder.metricTypeSet(memoryMetrics);

            PlatformNodeLocation memoryLocation = new PlatformNodeLocation(
                PlatformPath.builder().any(PlatformResourceType.MEMORY).build());
                    Builder<?, PlatformNodeLocation> memoryBldr = ResourceType.<PlatformNodeLocation> builder()
                            .name(PlatformResourceType.MEMORY.getResourceTypeName())
                            .parent(rootType.getName())
                            .location(memoryLocation)
                            .metricSetName(memoryMetrics.getName())
                            .resourceNameTemplate(PlatformResourceType.MEMORY.getResourceTypeName().getNameString());

            populateMetricAndAvailTypesForResourceType(memoryBldr, typeSetsBuilder);

            ResourceType<PlatformNodeLocation> memory = memoryBldr.build();
            TypeSet<ResourceType<PlatformNodeLocation>> typeSet = TypeSet
               .<ResourceType<PlatformNodeLocation>> builder()
               .name(PlatformResourceType.MEMORY.getResourceTypeName())
               .type(memory)
               .build();

            typeSetsBuilder.resourceTypeSet(typeSet);
        }

        org.hawkular.agent.model.PlatformMetricType processor = platform.getProcessors();
        boolean procEnabled = processor.isEnabled();
        if (procEnabled) {
            Interval interval = new Interval(processor.getInterval().intValue(),
                 TimeUnit.valueOf(processor.getTimeUnits().name()
                 .toUpperCase()));

            // this is the Processor.getProcessorCpuLoadBetweenTicks value
            MetricType<PlatformNodeLocation> cpuUsage = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.PROCESSOR_CPU_USAGE.getMetricTypeId(),
                    PlatformMetricType.PROCESSOR_CPU_USAGE.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.PROCESSOR_CPU_USAGE.getMetricTypeId().getIDString()),
                    interval,
                    MeasurementUnit.PERCENTAGE,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            TypeSet<MetricType<PlatformNodeLocation>> processorMetrics = TypeSet
                    .<MetricType<PlatformNodeLocation>> builder()
                    .name(PlatformResourceType.PROCESSOR.getResourceTypeName())
                    .type(cpuUsage)
                    .build();

            typeSetsBuilder.metricTypeSet(processorMetrics);

            PlatformNodeLocation processorsLocation = new PlatformNodeLocation(
                PlatformPath.builder().any(PlatformResourceType.PROCESSOR).build());
                    Builder<?, PlatformNodeLocation> processorBldr = ResourceType.<PlatformNodeLocation> builder()
                            .name(PlatformResourceType.PROCESSOR.getResourceTypeName())
                            .parent(rootType.getName())
                            .location(processorsLocation)
                            .metricSetName(processorMetrics.getName())
                            .resourceNameTemplate(PlatformResourceType.PROCESSOR.getResourceTypeName().getNameString() + " [%s]");

            populateMetricAndAvailTypesForResourceType(processorBldr, typeSetsBuilder);

            ResourceType<PlatformNodeLocation> proc = processorBldr.build();
                    TypeSet<ResourceType<PlatformNodeLocation>> typeSet = TypeSet
                            .<ResourceType<PlatformNodeLocation>> builder()
                            .name(PlatformResourceType.PROCESSOR.getResourceTypeName())
                            .type(proc)
                            .build();

            typeSetsBuilder.resourceTypeSet(typeSet);
        }

        org.hawkular.agent.model.PlatformMetricType powerSourcesNode = platform.getPowerSources();
        boolean pwrEnabled = powerSourcesNode.isEnabled();
        if (pwrEnabled) {
            Interval interval = new Interval(powerSourcesNode.getInterval().intValue(),
                TimeUnit.valueOf(powerSourcesNode.getTimeUnits().name()));

            MetricType<PlatformNodeLocation> remainingCap = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.POWER_SOURCE_REMAINING_CAPACITY.getMetricTypeId(),
                    PlatformMetricType.POWER_SOURCE_REMAINING_CAPACITY.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.POWER_SOURCE_REMAINING_CAPACITY.getMetricTypeId()
                                    .getIDString()),
                    interval,
                    MeasurementUnit.PERCENTAGE,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            MetricType<PlatformNodeLocation> timeRemaining = new MetricType<PlatformNodeLocation>(
                    PlatformMetricType.POWER_SOURCE_TIME_REMAINING.getMetricTypeId(),
                    PlatformMetricType.POWER_SOURCE_TIME_REMAINING.getMetricTypeName(),
                    new AttributeLocation<>(
                            new PlatformNodeLocation(PlatformPath.empty()),
                            PlatformMetricType.POWER_SOURCE_TIME_REMAINING.getMetricTypeId().getIDString()),
                    interval,
                    MeasurementUnit.SECONDS,
                    org.hawkular.metrics.client.common.MetricType.GAUGE,
                    null,
                    null);

            TypeSet<MetricType<PlatformNodeLocation>> powerSourceMetrics = TypeSet
                    .<MetricType<PlatformNodeLocation>> builder()
                    .name(PlatformResourceType.POWER_SOURCE.getResourceTypeName())
                    .type(remainingCap)
                    .type(timeRemaining)
                    .build();

            typeSetsBuilder.metricTypeSet(powerSourceMetrics);

            PlatformNodeLocation powerSourcesLocation = new PlatformNodeLocation(
                PlatformPath.builder().any(PlatformResourceType.POWER_SOURCE).build());
            Builder<?, PlatformNodeLocation> powerSourceBldr = ResourceType.<PlatformNodeLocation> builder()
                .name(PlatformResourceType.POWER_SOURCE.getResourceTypeName())
                .parent(rootType.getName())
                .location(powerSourcesLocation)
                .metricSetName(powerSourceMetrics.getName())
                .resourceNameTemplate(
                 PlatformResourceType.POWER_SOURCE.getResourceTypeName().getNameString() + " [%s]");

            populateMetricAndAvailTypesForResourceType(powerSourceBldr, typeSetsBuilder);

            ResourceType<PlatformNodeLocation> powerSource = powerSourceBldr.build();
                TypeSet<ResourceType<PlatformNodeLocation>> typeSet = TypeSet
                .<ResourceType<PlatformNodeLocation>> builder()
                .name(PlatformResourceType.POWER_SOURCE.getResourceTypeName())
                .type(powerSource)
                .build();

            typeSetsBuilder.resourceTypeSet(typeSet);
        }

        return typeSetsBuilder.build();
    }

    private static DiagnosticsConfiguration determineDiagnosticsConfig(org.hawkular.agent.model.SubsystemType config)
            throws OperationFailedException {
        DiagnosticsType diagnostics = config.getDiagnostics();
        if (config == null) {
            //log.infoNoDiagnosticsConfig();
            return DiagnosticsConfiguration.EMPTY;
        }

        String reportToStr = diagnostics.getReportTo().name();
        DiagnosticsReportTo reportTo = MonitorServiceConfiguration.DiagnosticsReportTo.valueOf(reportToStr
                .toUpperCase());
        boolean enabled = diagnostics.isEnabled();
        int interval = diagnostics.getInterval().intValue();
        String diagnosticsTimeUnitsStr = diagnostics.getTimeUnits().name();
        TimeUnit timeUnits = TimeUnit.valueOf(diagnosticsTimeUnitsStr.toUpperCase());
        return new DiagnosticsConfiguration(enabled, reportTo, interval, timeUnits);
    }

    private static org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.StorageAdapterConfiguration determineStorageAdapterConfig(org.hawkular.agent.model.SubsystemType config) {

        if (config.getStorageAdapter() == null) {
            throw new IllegalArgumentException("Missing storage adapter configuration: " + config.toString());
        }
        StorageType storage = config.getStorageAdapter();

        String url = storage.getUrl();
        boolean useSSL = false;
        if (url != null) {
            useSSL = url.startsWith("https");
            //log.infoUsingSSL(url, useSSL);
        } else {
            useSSL = storage.isUseSsl();
        }
        String securityRealm = storage.getSecurityRealm();
        String keystorePath = storage.getKeystorePath();
        String keystorePassword = storage.getKeystorePassword();
        String serverOutboundSocketBindingRef = storage.getServerOutboundSocketBindingRef();
        String tenantId = storage.getTenantId();
        String feedId = storage.getFeedId();
        String inventoryContext = storage.getInventoryContext();
        String metricsContext = storage.getMetricsContext();
        String feedcommContext = storage.getFeedcommContext();
        String username = storage.getUsername();
        String password = storage.getPassword();
        String typeStr = storage.getType().name();
        StorageReportTo type = MonitorServiceConfiguration.StorageReportTo.valueOf(typeStr.toUpperCase());
        int connectTimeoutSeconds = (storage.getConnectTimeoutSecs() != null) ? storage.getConnectTimeoutSecs() : 16;
        int readTimeoutSeconds = (storage.getReadTimeoutSecs() != null) ? storage.getReadTimeoutSecs().intValue() : 256;

        if (useSSL) {
            if (securityRealm == null) {
                if (keystorePath == null) {
                    throw new IllegalArgumentException(
                            "In order to use SSL, a securityRealm or keystorePath must be specified");
                }
                if (keystorePassword == null) {
                    throw new IllegalArgumentException(
                            "In order to use SSL, a securityRealm or keystorePassword must be specified");
                }
            }
        }

        if (username == null || password == null) {
            throw new IllegalArgumentException("Must have a username/password");
        }

        return new StorageAdapterConfiguration(type, username, password, tenantId, feedId, url, useSSL,
                serverOutboundSocketBindingRef, inventoryContext, metricsContext, feedcommContext,
                keystorePath, keystorePassword, securityRealm, connectTimeoutSeconds, readTimeoutSeconds);
    }

    private static GlobalConfiguration determineGlobalConfig(org.hawkular.agent.model.SubsystemType config) {
        boolean subsystemEnabled = config.isEnabled().booleanValue();
        String apiJndi = config.getApiJndiName();
        int autoDiscoveryScanPeriodSecs = (config.getAutoDiscoveryScanPeriodSecs() != null) ? config.getAutoDiscoveryScanPeriodSecs() : 3601;
        int numDmrSchedulerThreads = (config.getNumDmrSchedulerThreads() != null) ? config.getNumDmrSchedulerThreads() : 1;

        int metricDispatcherBufferSize = config.getMetricDispatcherBufferSize().intValue();
        int metricDispatcherMaxBatchSize = config.getMetricDispatcherMaxBatchSize().intValue();
        int availDispatcherBufferSize = config.getAvailDispatcherBufferSize().intValue();
        int pingDispatcherPeriodSeconds = config.getPingPeriodSecs().intValue();
        int availDispatcherMaxBatchSize = config.getAvailDispatcherMaxBatchSize().intValue();

        return new GlobalConfiguration(subsystemEnabled, apiJndi, autoDiscoveryScanPeriodSecs,
                numDmrSchedulerThreads, metricDispatcherBufferSize, metricDispatcherMaxBatchSize,
                availDispatcherBufferSize, availDispatcherMaxBatchSize, pingDispatcherPeriodSeconds);
    }


    private static void determineResourceTypeSetJmx(org.hawkular.agent.model.SubsystemType config,
            TypeSets.Builder<JMXNodeLocation> typeSetsBuilder) {
        boolean enabled = false;

        List<ResourceTypeSetJmxType> resourceTypeSetsList = config.getResourceTypeSetJmx();
        for (ResourceTypeSetJmxType resourceTypeSetProperty : resourceTypeSetsList) {
            String resourceTypeSetName = resourceTypeSetProperty.getName();

            boolean resourceTypeEnabled;
            if (resourceTypeSetProperty.isEnabled() == null) {
            	resourceTypeEnabled = true; 
            } else {
            	resourceTypeEnabled = resourceTypeSetProperty.isEnabled().booleanValue();
            }
            TypeSetBuilder<ResourceType<JMXNodeLocation>> typeSetBuilder = TypeSet
                    .<ResourceType<JMXNodeLocation>> builder()
                    .name(new Name(resourceTypeSetName))
                    .enabled(resourceTypeEnabled);

            if (resourceTypeSetName.indexOf(',') > -1) {
                //log.warnCommaInName(resourceTypeSetName);
            }
            List<ResourceTypeJmxType> resourceTypesList = resourceTypeSetProperty.getResourceTypeJmx();
            for (org.hawkular.agent.model.ResourceTypeJmxType resourceTypeProperty : resourceTypesList) {
                String resourceTypeName = resourceTypeProperty.getName();

                String objectName = resourceTypeProperty.getObjectName();
                try {
                    Builder<?, JMXNodeLocation> resourceTypeBuilder = ResourceType.<JMXNodeLocation> builder()
                        .id(ID.NULL_ID)
                        .name(new Name(resourceTypeName))
                        .location(new JMXNodeLocation(objectName))
                        .resourceNameTemplate(resourceTypeProperty.getResourceNameTemplate())
                        .parents(getNameListFromString(resourceTypeProperty.getParents()));

                    List<Name> metricSets = getNameListFromString(resourceTypeProperty.getMetricSets().toString());
                    List<Name> availSets = getNameListFromString(resourceTypeProperty.getAvailSets());

                    resourceTypeBuilder.metricSetNames(metricSets)
                        .availSetNames(availSets);

                    // get operations
                    List<OperationJmxType> operationList = resourceTypeProperty.getOperationJmx();
                    for (org.hawkular.agent.model.OperationJmxType operationProperty : operationList) {
                        String operationName = operationProperty.getName();

                        Operation<JMXNodeLocation> op = new Operation<>(ID.NULL_ID,
                            new Name(operationName),
                            new JMXNodeLocation(operationProperty.getObjectName()),
                            operationProperty.getInternalName(), null);
                        resourceTypeBuilder.operation(op);
                    }

                    List<ResourceConfigJmxType> configList = resourceTypeProperty.getResourceConfigJmx();
                    for (org.hawkular.agent.model.ResourceConfigJmxType configProperty : configList) {
                        String configName = configProperty.getName();

                        String on = configProperty.getObjectName();
                        String attr = configProperty.getAttribute();
                        AttributeLocation<JMXNodeLocation> attribLoc = new AttributeLocation<JMXNodeLocation>(
                            new JMXNodeLocation(on), attr);
                        ResourceConfigurationPropertyType<JMXNodeLocation> configType = new ResourceConfigurationPropertyType<>(
                                            ID.NULL_ID, new Name(configName), attribLoc);
                        resourceTypeBuilder.resourceConfigurationPropertyType(configType);

                    }

                    populateMetricAndAvailTypesForResourceType(resourceTypeBuilder, typeSetsBuilder);

                    ResourceType<JMXNodeLocation> resourceType = resourceTypeBuilder.build();
                    typeSetBuilder.type(resourceType);
                } catch (MalformedObjectNameException e) {
                    //log.warnMalformedJMXObjectName(objectName, e);
                }

            }

            TypeSet<ResourceType<JMXNodeLocation>> typeSet = typeSetBuilder.build();
            enabled = enabled || !typeSet.isDisabledOrEmpty();
            typeSetsBuilder.resourceTypeSet(typeSet);

        }

        if (!enabled) {
            //log.infoNoEnabledResourceTypesConfigured("JMX");
        }

    }

    private void determineManagedServers(org.hawkular.agent.model.SubsystemType config) throws Exception {
        ManagedServersType mst = config.getManagedServers();
            // JMX

        if (mst.getRemoteJmx().size() > 0) {
            List<RemoteJmxType> remoteJMXsList = mst.getRemoteJmx();
            for (org.hawkular.agent.model.RemoteJmxType remoteJMXProperty : remoteJMXsList) {
                String name = remoteJMXProperty.getName();
                boolean enabled = remoteJMXProperty.isEnabled();
                String setAvailOnShutdownStr = remoteJMXProperty.getSetAvailOnShutdown();

                Avail setAvailOnShutdown = (setAvailOnShutdownStr == null) ? null
                    : Avail.valueOf(setAvailOnShutdownStr);
                String urlStr = remoteJMXProperty.getUrl();
                String username = remoteJMXProperty.getUsername();
                String password = remoteJMXProperty.getPassword();
                String securityRealm = remoteJMXProperty.getSecurityRealm();
                List<Name> resourceTypeSets = getNameListFromString(remoteJMXProperty.getResourceTypeSets());
                String tenantId = remoteJMXProperty.getTenantId();
                String metricIdTemplate = remoteJMXProperty.getMetricIdTemplate();
                Map<String, String> metricTags = getMapFromString(remoteJMXProperty.getMetricLabels());

                // make sure the URL is at least syntactically valid
                URI url;
                try {
                    url = new URI(urlStr);
                } catch (Exception e) {
                    throw new Exception("Invalid remote JMX URL: " + urlStr, e);
                }

                if (url.getScheme().equalsIgnoreCase("https") && securityRealm == null) {
                    //log.debugf("Using SSL with no security realm - will rely on the JVM truststore: " + name);
                }

                ConnectionData connectionData = new ConnectionData(url, username, password);
                EndpointConfiguration endpoint = new EndpointConfiguration(name, enabled, resourceTypeSets,
                    connectionData, securityRealm, setAvailOnShutdown, tenantId, metricIdTemplate, metricTags,
                    null);

                jmxConfigBuilder.endpoint(endpoint);
            }
        }

    }

    private static List<Name> getNameListFromString(String value) {
        if (value != null) {
            String[] stringArray = value.split(",");
            ArrayList<Name> names = new ArrayList<Name>(stringArray.length);
            for (String str : stringArray) {
                names.add(new Name(str));
            }
            return names;
        } else {
            return Collections.emptyList();
        }
    }

    private static Map<String, String> getMapFromString(String value) throws Exception {
        if (value != null) {
            Map<String, String> map = new HashMap<>();
            String commaSeparatedList = value;
            StringTokenizer strtok = new StringTokenizer(commaSeparatedList, ",");
            while (strtok.hasMoreTokens()) {
                String nameValueToken = strtok.nextToken().trim();
                String[] nameValueArr = nameValueToken.split("=");
                if (nameValueArr.length != 2) {
                    throw new Exception("missing '=' in name-value pair: " + commaSeparatedList);
                }
                map.put(nameValueArr[0].trim(), nameValueArr[1].trim());
            }
            return map;
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * Given a resource type builder, this will fill in its metric types and avail types.
     *
     * @param resourceTypeBuilder the type being built whose metric and avail types are to be filled in
     * @param typeSetsBuilder all type metadata - this is where our metrics and avails are
     */
    private static <L> void populateMetricAndAvailTypesForResourceType(
            ResourceType.Builder<?, L> resourceTypeBuilder,
            TypeSets.Builder<L> typeSetsBuilder) {

        Map<Name, TypeSet<MetricType<L>>> metricTypeSets = typeSetsBuilder.getMetricTypeSets();
        List<Name> metricSetNames = resourceTypeBuilder.getMetricSetNames();
        for (Name metricSetName : metricSetNames) {
            TypeSet<MetricType<L>> metricSet = metricTypeSets.get(metricSetName);
            if (metricSet != null && metricSet.isEnabled()) {
                resourceTypeBuilder.metricTypes(metricSet.getTypeMap().values());
            }
        }

        Map<Name, TypeSet<AvailType<L>>> availTypeSets = typeSetsBuilder.getAvailTypeSets();
        List<Name> availSetNames = resourceTypeBuilder.getAvailSetNames();
        for (Name availSetName : availSetNames) {
            TypeSet<AvailType<L>> availSet = availTypeSets.get(availSetName);
            if (availSet != null && availSet.isEnabled()) {
                resourceTypeBuilder.availTypes(availSet.getTypeMap().values());
            }
        }
    }
}
