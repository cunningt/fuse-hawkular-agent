/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.agent.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for subsystemType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="subsystemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diagnostics" type="{urn:org.hawkular.agent:agent:1.0}diagnosticsType" minOccurs="0"/>
 *         &lt;element name="storage-adapter" type="{urn:org.hawkular.agent:agent:1.0}storageType"/>
 *         &lt;element name="metric-set-dmr" type="{urn:org.hawkular.agent:agent:1.0}metricSetDmrType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="avail-set-dmr" type="{urn:org.hawkular.agent:agent:1.0}availSetDmrType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="resource-type-set-dmr" type="{urn:org.hawkular.agent:agent:1.0}resourceTypeSetDmrType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="metric-set-jmx" type="{urn:org.hawkular.agent:agent:1.0}metricSetJmxType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="avail-set-jmx" type="{urn:org.hawkular.agent:agent:1.0}availSetJmxType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="resource-type-set-jmx" type="{urn:org.hawkular.agent:agent:1.0}resourceTypeSetJmxType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="metric-set-prometheus" type="{urn:org.hawkular.agent:agent:1.0}metricSetPrometheusType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="managed-servers" type="{urn:org.hawkular.agent:agent:1.0}managedServersType"/>
 *         &lt;element name="platform" type="{urn:org.hawkular.agent:agent:1.0}platformType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="api-jndi-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="auto-discovery-scan-period-secs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="num-dmr-scheduler-threads" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="metric-dispatcher-buffer-size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="metric-dispatcher-max-batch-size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="avail-dispatcher-buffer-size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="avail-dispatcher-max-batch-size" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ping-period-secs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name="subsystem")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "subsystemType", propOrder = {
    "diagnostics",
    "storageAdapter",
    "metricSetDmr",
    "availSetDmr",
    "resourceTypeSetDmr",
    "metricSetJmx",
    "availSetJmx",
    "resourceTypeSetJmx",
    "metricSetPrometheus",
    "managedServers",
    "platform"
})
public class SubsystemType {

    protected DiagnosticsType diagnostics;
    @XmlElement(name = "storage-adapter", required = true)
    protected StorageType storageAdapter;
    @XmlElement(name = "metric-set-dmr")
    protected List<MetricSetDmrType> metricSetDmr;
    @XmlElement(name = "avail-set-dmr")
    protected List<AvailSetDmrType> availSetDmr;
    @XmlElement(name = "resource-type-set-dmr")
    protected List<ResourceTypeSetDmrType> resourceTypeSetDmr;
    @XmlElement(name = "metric-set-jmx")
    protected List<MetricSetJmxType> metricSetJmx;
    @XmlElement(name = "avail-set-jmx")
    protected List<AvailSetJmxType> availSetJmx;
    @XmlElement(name = "resource-type-set-jmx")
    protected List<ResourceTypeSetJmxType> resourceTypeSetJmx;
    @XmlElement(name = "metric-set-prometheus")
    protected List<MetricSetPrometheusType> metricSetPrometheus;
    @XmlElement(name = "managed-servers", required = true)
    protected ManagedServersType managedServers;
    protected PlatformType platform;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;
    @XmlAttribute(name = "api-jndi-name")
    protected String apiJndiName;
    @XmlAttribute(name = "auto-discovery-scan-period-secs")
    protected Integer autoDiscoveryScanPeriodSecs;
    @XmlAttribute(name = "num-dmr-scheduler-threads")
    protected Integer numDmrSchedulerThreads;
    @XmlAttribute(name = "metric-dispatcher-buffer-size")
    protected Integer metricDispatcherBufferSize;
    @XmlAttribute(name = "metric-dispatcher-max-batch-size")
    protected Integer metricDispatcherMaxBatchSize;
    @XmlAttribute(name = "avail-dispatcher-buffer-size")
    protected Integer availDispatcherBufferSize;
    @XmlAttribute(name = "avail-dispatcher-max-batch-size")
    protected Integer availDispatcherMaxBatchSize;
    @XmlAttribute(name = "ping-period-secs")
    protected Integer pingPeriodSecs;

    /**
     * Gets the value of the diagnostics property.
     *
     * @return
     *     possible object is
     *     {@link DiagnosticsType }
     *
     */
    public DiagnosticsType getDiagnostics() {
        return diagnostics;
    }

    /**
     * Sets the value of the diagnostics property.
     *
     * @param value
     *     allowed object is
     *     {@link DiagnosticsType }
     *
     */
    public void setDiagnostics(DiagnosticsType value) {
        this.diagnostics = value;
    }

    /**
     * Gets the value of the storageAdapter property.
     *
     * @return
     *     possible object is
     *     {@link StorageType }
     *
     */
    public StorageType getStorageAdapter() {
        return storageAdapter;
    }

    /**
     * Sets the value of the storageAdapter property.
     *
     * @param value
     *     allowed object is
     *     {@link StorageType }
     *
     */
    public void setStorageAdapter(StorageType value) {
        this.storageAdapter = value;
    }

    /**
     * Gets the value of the metricSetDmr property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metricSetDmr property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetricSetDmr().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetricSetDmrType }
     *
     *
     */
    public List<MetricSetDmrType> getMetricSetDmr() {
        if (metricSetDmr == null) {
            metricSetDmr = new ArrayList<MetricSetDmrType>();
        }
        return this.metricSetDmr;
    }

    /**
     * Gets the value of the availSetDmr property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availSetDmr property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailSetDmr().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AvailSetDmrType }
     *
     *
     */
    public List<AvailSetDmrType> getAvailSetDmr() {
        if (availSetDmr == null) {
            availSetDmr = new ArrayList<AvailSetDmrType>();
        }
        return this.availSetDmr;
    }

    /**
     * Gets the value of the resourceTypeSetDmr property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceTypeSetDmr property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceTypeSetDmr().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceTypeSetDmrType }
     *
     *
     */
    public List<ResourceTypeSetDmrType> getResourceTypeSetDmr() {
        if (resourceTypeSetDmr == null) {
            resourceTypeSetDmr = new ArrayList<ResourceTypeSetDmrType>();
        }
        return this.resourceTypeSetDmr;
    }

    /**
     * Gets the value of the metricSetJmx property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metricSetJmx property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetricSetJmx().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetricSetJmxType }
     *
     *
     */
    public List<MetricSetJmxType> getMetricSetJmx() {
        if (metricSetJmx == null) {
            metricSetJmx = new ArrayList<MetricSetJmxType>();
        }
        return this.metricSetJmx;
    }

    /**
     * Gets the value of the availSetJmx property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availSetJmx property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailSetJmx().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AvailSetJmxType }
     *
     *
     */
    public List<AvailSetJmxType> getAvailSetJmx() {
        if (availSetJmx == null) {
            availSetJmx = new ArrayList<AvailSetJmxType>();
        }
        return this.availSetJmx;
    }

    /**
     * Gets the value of the resourceTypeSetJmx property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceTypeSetJmx property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceTypeSetJmx().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceTypeSetJmxType }
     *
     *
     */
    public List<ResourceTypeSetJmxType> getResourceTypeSetJmx() {
        if (resourceTypeSetJmx == null) {
            resourceTypeSetJmx = new ArrayList<ResourceTypeSetJmxType>();
        }
        return this.resourceTypeSetJmx;
    }

    /**
     * Gets the value of the metricSetPrometheus property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metricSetPrometheus property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetricSetPrometheus().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MetricSetPrometheusType }
     *
     *
     */
    public List<MetricSetPrometheusType> getMetricSetPrometheus() {
        if (metricSetPrometheus == null) {
            metricSetPrometheus = new ArrayList<MetricSetPrometheusType>();
        }
        return this.metricSetPrometheus;
    }

    /**
     * Gets the value of the managedServers property.
     *
     * @return
     *     possible object is
     *     {@link ManagedServersType }
     *
     */
    public ManagedServersType getManagedServers() {
        return managedServers;
    }

    /**
     * Sets the value of the managedServers property.
     *
     * @param value
     *     allowed object is
     *     {@link ManagedServersType }
     *
     */
    public void setManagedServers(ManagedServersType value) {
        this.managedServers = value;
    }

    /**
     * Gets the value of the platform property.
     *
     * @return
     *     possible object is
     *     {@link PlatformType }
     *
     */
    public PlatformType getPlatform() {
        return platform;
    }

    /**
     * Sets the value of the platform property.
     *
     * @param value
     *     allowed object is
     *     {@link PlatformType }
     *
     */
    public void setPlatform(PlatformType value) {
        this.platform = value;
    }

    /**
     * Gets the value of the enabled property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setEnabled(Boolean value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the apiJndiName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApiJndiName() {
        return apiJndiName;
    }

    /**
     * Sets the value of the apiJndiName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApiJndiName(String value) {
        this.apiJndiName = value;
    }

    /**
     * Gets the value of the autoDiscoveryScanPeriodSecs property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getAutoDiscoveryScanPeriodSecs() {
        return autoDiscoveryScanPeriodSecs;
    }

    /**
     * Sets the value of the autoDiscoveryScanPeriodSecs property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setAutoDiscoveryScanPeriodSecs(Integer value) {
        this.autoDiscoveryScanPeriodSecs = value;
    }

    /**
     * Gets the value of the numDmrSchedulerThreads property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getNumDmrSchedulerThreads() {
        return numDmrSchedulerThreads;
    }

    /**
     * Sets the value of the numDmrSchedulerThreads property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setNumDmrSchedulerThreads(Integer value) {
        this.numDmrSchedulerThreads = value;
    }

    /**
     * Gets the value of the metricDispatcherBufferSize property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMetricDispatcherBufferSize() {
        return metricDispatcherBufferSize;
    }

    /**
     * Sets the value of the metricDispatcherBufferSize property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMetricDispatcherBufferSize(Integer value) {
        this.metricDispatcherBufferSize = value;
    }

    /**
     * Gets the value of the metricDispatcherMaxBatchSize property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMetricDispatcherMaxBatchSize() {
        return metricDispatcherMaxBatchSize;
    }

    /**
     * Sets the value of the metricDispatcherMaxBatchSize property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMetricDispatcherMaxBatchSize(Integer value) {
        this.metricDispatcherMaxBatchSize = value;
    }

    /**
     * Gets the value of the availDispatcherBufferSize property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getAvailDispatcherBufferSize() {
        return availDispatcherBufferSize;
    }

    /**
     * Sets the value of the availDispatcherBufferSize property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setAvailDispatcherBufferSize(Integer value) {
        this.availDispatcherBufferSize = value;
    }

    /**
     * Gets the value of the availDispatcherMaxBatchSize property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getAvailDispatcherMaxBatchSize() {
        return availDispatcherMaxBatchSize;
    }

    /**
     * Sets the value of the availDispatcherMaxBatchSize property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setAvailDispatcherMaxBatchSize(Integer value) {
        this.availDispatcherMaxBatchSize = value;
    }

    /**
     * Gets the value of the pingPeriodSecs property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getPingPeriodSecs() {
        return pingPeriodSecs;
    }

    /**
     * Sets the value of the pingPeriodSecs property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setPingPeriodSecs(Integer value) {
        this.pingPeriodSecs = value;
    }

}
