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
package org.fuse.hawkular.agent;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.apache.log4j.Logger;

import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;

import org.fuse.hawkular.agent.monitor.diagnostics.JBossLoggingReporter;
import org.fuse.hawkular.agent.monitor.diagnostics.StorageReporter;
import org.fuse.hawkular.agent.monitor.scheduler.SchedulerConfiguration;


import org.hawkular.agent.model.RemoteJmxType;
import org.hawkular.agent.model.SubsystemType;
import org.hawkular.agent.model.TimeUnitsType;
import org.fuse.hawkular.agent.monitor.cmd.FeedCommProcessor;
import org.fuse.hawkular.agent.monitor.cmd.WebSocketClientBuilder;
import org.fuse.hawkular.agent.monitor.diagnostics.Diagnostics;
import org.fuse.hawkular.agent.monitor.diagnostics.DiagnosticsImpl;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration;
import org.hawkular.agent.monitor.extension.StorageAttributes;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.AbstractEndpointConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.DynamicEndpointConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.EndpointConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.StorageAdapterConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.StorageReportTo;

import org.hawkular.agent.monitor.inventory.AvailType;
import org.hawkular.agent.monitor.inventory.MeasurementInstance;
import org.hawkular.agent.monitor.inventory.Resource;
import org.hawkular.agent.monitor.inventory.ResourceManager;
import org.hawkular.agent.monitor.log.AgentLoggers;
import org.hawkular.agent.monitor.log.MsgLogger;
import org.hawkular.agent.monitor.protocol.EndpointService;
import org.hawkular.agent.monitor.protocol.ProtocolService;
import org.fuse.hawkular.agent.monitor.protocol.ProtocolServices;
import org.hawkular.agent.monitor.storage.AvailDataPoint;
import org.fuse.hawkular.agent.monitor.storage.AvailStorageProxy;
import org.fuse.hawkular.agent.monitor.storage.HawkularStorageAdapter;
import org.fuse.hawkular.agent.monitor.storage.HttpClientBuilder;
import org.fuse.hawkular.agent.monitor.storage.InventoryStorageProxy;
import org.fuse.hawkular.agent.monitor.storage.MetricStorageProxy;
import org.fuse.hawkular.agent.monitor.storage.StorageAdapter;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.fuse.hawkular.agent.monitor.scheduler.SchedulerService;
import org.hawkular.agent.monitor.util.Util;
import org.hawkular.inventory.api.model.Feed;

/**
 * The main Agent service.
 *
 * @author John Mazzitelli
 * @author <a href="https://github.com/cunningt">Tom Cunningham</a>
 * @author <a href="https://github.com/ppalaga">Peter Palaga</a>
 */
public class MonitorService {
    private static final MsgLogger log = AgentLoggers.getLogger(MonitorService.class);

    private static SSLContext getSslContext(Map<String, SSLContext> trustOnlySSLContextValues) {
        SSLContext result = null;
        return result;
    }

    private boolean started = false;

    private SubsystemType subsystemConfiguration = null;
    private MonitorServiceConfiguration config = null;

	// this is used to identify us to the Hawkular environment as a particular feed
    private String feedId;

    // used to report our own internal metrics
    private Diagnostics diagnostics;
    private ScheduledReporter diagnosticsReporter;

    // used to send monitored data for storage
    private HawkularStorageAdapter storageAdapter;
    private HttpClientBuilder httpClientBuilder;

    // used to send/receive data to the server over the feed communications channel
    private WebSocketClientBuilder webSocketClientBuilder;
    private FeedCommProcessor feedComm;

    // scheduled metric and avail collections
    private SchedulerService schedulerService;

    // proxies that are exposed via JNDI so external apps can emit their own inventory, metrics, and avail checks
    private final MetricStorageProxy metricStorageProxy = new MetricStorageProxy();
    private final AvailStorageProxy availStorageProxy = new AvailStorageProxy();
    private final InventoryStorageProxy inventoryStorageProxy = new InventoryStorageProxy();

    // contains endpoint services for all the different protocols that are supported (dmr, jmx, prometheus, platform)
    private ProtocolServices protocolServices;

    private final Map<String, SSLContext> trustOnlySSLContextValues = new HashMap<>();

    
    public MonitorService() {
        super();
    }
    
    public MonitorService(SubsystemType st) {
    	super();
    	subsystemConfiguration = st;
    }

    public void setConfiguration(MonitorServiceConfiguration config) {
		this.config = config;
	}
    
    public MonitorService getValue() {
        return this;
    }

    /**
     * When this service is being built, this method is called to allow this service
     * to add whatever dependencies it needs.
     *
     * @param target the service target
     * @param bldr the service builder used to add dependencies
     */
    public void addDependencies() {
    }

    private void addSslContext(String securityRealm) {
    }

    /**
     * @return true if this service is {@link #startMonitorService() started};
     *         false if this service is {@link #stopMonitorService() stopped}.
     */
    public boolean isMonitorServiceStarted() {
        return started;
    }

    public void start() throws Exception {
        final AtomicReference<Thread> startThread = new AtomicReference<Thread>();

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startMonitorService();
                } catch (Throwable t) {
                }
            }
        }, "Hawkular WildFly Agent Startup Thread");
        newThread.setDaemon(true);

        Thread oldThread = startThread.getAndSet(newThread);
        if (oldThread != null) {
            oldThread.interrupt();
        }

        newThread.start();
    }

    public void stop() {
        stopMonitorService();
    }

    /**
     * Starts this service. If the service is already started, this method is a no-op.
     */
    public void startMonitorService() {
        if (isMonitorServiceStarted()) {
            return; // we are already started
        }

        try {
            log.infoStarting();

            // prepare the builder that will create our HTTP/REST clients to the hawkular server infrastructure
            SSLContext ssl = getSslContext(this.trustOnlySSLContextValues);
            
            boolean useSsl = subsystemConfiguration.getStorageAdapter().getUrl().matches("https");
            this.httpClientBuilder = new HttpClientBuilder(
            		subsystemConfiguration.getStorageAdapter().getUsername(),
            		subsystemConfiguration.getStorageAdapter().getPassword(), 
            		useSsl, ssl,
            		subsystemConfiguration.getStorageAdapter().getKeystorePath(), 
            		subsystemConfiguration.getStorageAdapter().getKeystorePassword(), 
            		subsystemConfiguration.getStorageAdapter().getConnectTimeoutSecs(),
            		subsystemConfiguration.getStorageAdapter().getReadTimeoutSecs());

            if (subsystemConfiguration.getStorageAdapter().getFeedId() != null) {
                this.feedId = subsystemConfiguration.getStorageAdapter().getFeedId();
            } else {
                throw new Exception("Could not obtain local feed ID");
            }

            // build the diagnostics object that will be used to track our own performance
            final MetricRegistry metricRegistry = new MetricRegistry();
            this.diagnostics = new DiagnosticsImpl(subsystemConfiguration.getDiagnostics(), metricRegistry, feedId);

            // We need the tenantIds to register our feed (in Hawkular mode) and to schedule pings
            Set<String> tenantIds = getTenantIds();

            // Before we go on, we must make sure the Hawkular Server is up and ready
            waitForHawkularServer();

            // perform some things that are dependent upon what mode the agent is in
            if (this.subsystemConfiguration.getStorageAdapter().getType().toString().equals("HAWKULAR")) {
                    // if we are participating in a full Hawkular environment, we need to do some additional things:
                    // 1. register our feed ID
                    // 2. connect to the server's feed comm channel
                    try {
                        registerFeed(tenantIds);
                    } catch (Exception e) {
                        log.errorCannotDoAnythingWithoutFeed(e);
                        throw new Exception("Agent needs a feed to run");
                    }

                    // try to connect to the server via command-gateway channel; keep going on error
                    try {
                        this.webSocketClientBuilder = new WebSocketClientBuilder(
                                this.subsystemConfiguration.getStorageAdapter(), ssl);
                        this.feedComm = new FeedCommProcessor(this.webSocketClientBuilder, this.subsystemConfiguration,
                                this.feedId, null);
                        this.feedComm.connect();
                    } catch (Exception e) {
                        if (e instanceof InterruptedException) {
                            Thread.currentThread().interrupt();
                        }
                        log.errorCannotEstablishFeedComm(e);
                    }
            } else if (this.subsystemConfiguration.getStorageAdapter().getType().toString().equals("METRICS")) {
                    // nothing special needs to be done
            } else {
                    throw new IllegalStateException(
                            "Unknown storage adapter type: " + this.subsystemConfiguration.getStorageAdapter().getType());
            }

            // start the storage adapter
            try {
                startStorageAdapter();
            } catch (Exception e) {
                log.errorCannotStartStorageAdapter(e);
                throw new Exception("Agent cannot start storage adapter");
            }

            try {
                startScheduler(tenantIds);
            } catch (Exception e) {
                log.errorCannotInitializeScheduler(e);
                throw new Exception("Agent cannot initialize scheduler");
            }

            // build the protocol services
            ProtocolServices ps = createProtocolServicesBuilder()
                    .jmxProtocolService(config.getJmxConfiguration())
            //        .platformProtocolService(config)
                    .autoDiscoveryScanPeriodSecs(subsystemConfiguration.getAutoDiscoveryScanPeriodSecs())
                    .build();
            ps.addInventoryListener(inventoryStorageProxy);
            ps.addInventoryListener(schedulerService);
            protocolServices = ps;

            // start all protocol services - this should perform the initial discovery scans
            protocolServices.start();

            started = true;
            
        } catch (Throwable t) {
            if (t instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }

            log.errorFailedToStartAgent(t);

            // artificially shutdown the agent - agent will be disabled now
            started = true;
            stopMonitorService();
        }
    }

    /**
     * @return tenant IDs of the agent and its monitored endpoints (even if those monitored endpoints are not enabled)
     */
    private Set<String> getTenantIds() {
        Set<String> tenantIds = new HashSet<String>();
        List<AbstractEndpointConfiguration> endpoints = new ArrayList<>();
        for (RemoteJmxType rjt : subsystemConfiguration.getManagedServers().getRemoteJmx()) {
        	if (rjt.getTenantId() != null) {
                tenantIds.add(rjt.getTenantId());
        	}
        }
        
        if (subsystemConfiguration.getStorageAdapter().getTenantId() != null) {
            tenantIds.add(subsystemConfiguration.getStorageAdapter().getTenantId()); // always register agent's global tenant ID
        }
        return tenantIds;
    }

    /**
     * Stops this service. If the service is already stopped, this method is a no-op.
     */
    public void stopMonitorService() {
        if (!isMonitorServiceStarted()) {
            log.infoStoppedAlready();
            return; // we are already stopped
        }

        log.infoStopping();

        AtomicReference<Throwable> error = new AtomicReference<>(null);  // will hold the first error we encountered

        try {
            // We must do a few things first before we can shutdown the scheduler.
            // But we also must make sure we shutdown the scheduler so we kill its threads.
            // Otherwise we hang the shutdown of the entire server. So make sure we get to "stopScheduler".

            // disconnect from the feed comm channel
            try {
                if (feedComm != null) {
                    feedComm.destroy();
                    feedComm = null;
                }
            } catch (Throwable t) {
                error.compareAndSet(null, t);
                log.debug("Cannot shutdown feed comm but will continue shutdown", t);
            }

            // stop our normal protocol services
            Map<EndpointService<?, ?>, List<MeasurementInstance<?, AvailType<?>>>> availsToChange = null;

            try {
                if (protocolServices != null) {
                    availsToChange = getAvailsToChange();
                    protocolServices.stop();
                    protocolServices.removeInventoryListener(inventoryStorageProxy);
                    protocolServices.removeInventoryListener(schedulerService);
                    protocolServices = null;
                }
            } catch (Throwable t) {
                error.compareAndSet(null, t);
                log.debug("Cannot shutdown protocol services but will continue shutdown", t);
            }

            // shutdown scheduler and then the storage adapter - make sure we always attempt both
            try {
                if (schedulerService != null) {
                    schedulerService.stop();
                    schedulerService = null;
                }
            } catch (Throwable t) {
                error.compareAndSet(null, t);
                log.debug("Cannot shutdown scheduler but will continue shutdown", t);
            }

            // now stop the storage adapter
            try {
                if (storageAdapter != null) {
                	changeAvails();
                    //changeAvails(availsToChange); // notice we do this AFTER we shutdown the scheduler!
                    storageAdapter.shutdown();
                    storageAdapter = null;
                }
            } catch (Throwable t) {
                error.compareAndSet(null, t);
                log.debug("Cannot shutdown storage adapter but will continue shutdown", t);
            }

            // stop diagnostic reporting and spit out a final diagnostics report
            if (diagnosticsReporter != null) {
                diagnosticsReporter.stop();
                if (subsystemConfiguration.getDiagnostics().isEnabled()) {
                    diagnosticsReporter.report();
                }
                diagnosticsReporter = null;
            }

            // We attempted to clean everything we could. If we hit an error, throw it to log our shutdown wasn't clean
            if (error.get() != null) {
                throw error.get();
            }
        } catch (Throwable t) {
            log.warnFailedToStopAgent(t);
        } finally {
            started = false;
        }
    }

    private void changeAvails() {
    }

    private Map<EndpointService<?, ?>, List<MeasurementInstance<?, AvailType<?>>>> getAvailsToChange() {
    	return null;
    }

    /**
     * @return the directory where our agent service can write data files. This directory survives restarts.
     */
    private File getDataDirectory() {
        File dataDir;

        // TODO use the host environment once its available: https://issues.jboss.org/browse/WFCORE-1506
        dataDir = new File(System.getProperty("datadir"));

        File agentDataDir = new File(dataDir, "hawkular-agent");

        agentDataDir.mkdirs();
        return agentDataDir;
    }

    /**
     * Creates and starts the storage adapter that will be used to store our inventory data and monitoring data.
     *
     * @throws Exception if failed to start the storage adapter
     */
    private void startStorageAdapter() throws Exception {
        // create the storage adapter that will write our metrics/inventory data to backend storage on server
        this.storageAdapter = new HawkularStorageAdapter();
        this.storageAdapter.initialize(feedId, subsystemConfiguration.getStorageAdapter(), diagnostics, httpClientBuilder);

        // provide our storage adapter to the proxies - allows external apps to use them to store its own data
        metricStorageProxy.setStorageAdapter(storageAdapter);
        availStorageProxy.setStorageAdapter(storageAdapter);
        inventoryStorageProxy.setStorageAdapter(storageAdapter);

        // determine where we are to store our own diagnostic reports
        switch (subsystemConfiguration.getDiagnostics().getReportTo()) {
            case LOG: {
                this.diagnosticsReporter = JBossLoggingReporter.forRegistry(this.diagnostics.getMetricRegistry())
                        .convertRatesTo(TimeUnit.SECONDS)
                        .convertDurationsTo(MILLISECONDS)
                        .outputTo(Logger.getLogger(getClass()))
                        .withLoggingLevel("DEBUG")
                        .build();
                break;
            }
            case STORAGE: {
                this.diagnosticsReporter = StorageReporter
                        .forRegistry(this.diagnostics.getMetricRegistry(), subsystemConfiguration.getDiagnostics(),
                                storageAdapter)
                        .feedId(feedId)
                        .convertRatesTo(TimeUnit.SECONDS)
                        .convertDurationsTo(MILLISECONDS)
                        .build();
                break;
            }
            default: {
                throw new Exception("Invalid diagnostics type: " + subsystemConfiguration.getDiagnostics().getReportTo());
            }
        }

        if (this.subsystemConfiguration.getDiagnostics().isEnabled()) {
        	TimeUnitsType tut = this.subsystemConfiguration.getDiagnostics().getTimeUnits();
        	TimeUnit tu = TimeUnit.valueOf(tut.value().toUpperCase());
            diagnosticsReporter.start(this.subsystemConfiguration.getDiagnostics().getInterval(),
            		tu);
        }

    }

    /**
     * Builds the scheduler's configuration and starts the scheduler.
     *
     * @param tenantIds the tenants our feed is using
     * @throws Exception on error
     */
    private void startScheduler(Set<String> tenantIds) throws Exception {
        if (this.schedulerService == null) {
            SchedulerConfiguration schedulerConfig = new SchedulerConfiguration();
            schedulerConfig.setDiagnosticsConfig(this.subsystemConfiguration.getDiagnostics());
            schedulerConfig.setStorageAdapterConfig(this.subsystemConfiguration.getStorageAdapter());
            schedulerConfig.setMetricDispatcherBufferSize(this.subsystemConfiguration.getMetricDispatcherBufferSize());
            schedulerConfig.setMetricDispatcherMaxBatchSize(this.subsystemConfiguration.getMetricDispatcherMaxBatchSize());
            schedulerConfig.setAvailDispatcherBufferSize(this.subsystemConfiguration.getAvailDispatcherBufferSize());
            schedulerConfig.setAvailDispatcherMaxBatchSize(this.subsystemConfiguration.getAvailDispatcherMaxBatchSize());
            schedulerConfig.setPingDispatcherPeriodSeconds(this.subsystemConfiguration.getPingPeriodSecs());
            schedulerConfig.setFeedId(this.feedId);
            schedulerConfig.setTenantIds(tenantIds);

            this.schedulerService = new SchedulerService(schedulerConfig, this.diagnostics, this.storageAdapter);
        }

        this.schedulerService.start();    	
    }

    /**
     * Registers the feed with the Hawkular system under the given tenants.
     * Note, it is OK to re-register the same feed/tenant combinations.
     *
     * This will not return until the feed is properly registered under all tenants.
     * If the Hawkular server is not up, this could mean we are stuck here for a long time.
     *
     * @param tenantIds the feed is registered under the given tenantIds
     * @throws Exception if failed to register feed
     */
    private void registerFeed(Set<String> tenantIds) throws Exception {
        int retryMillis;
        try {
            retryMillis = Integer.parseInt(System.getProperty("hawkular.agent.feed.registration.retry", "60000"));
        } catch (Exception e) {
            retryMillis = 60000;
        }

        try {
            for (String tenantId : tenantIds) {
                registerFeed(tenantId, retryMillis);
            }
        } catch (Throwable t) {
            throw new Exception(String.format("Cannot register feed ID [%s]", this.feedId), t);
        }
    }

    private void waitForHawkularServer() throws Exception {
        OkHttpClient httpclient = this.httpClientBuilder.getHttpClient();

        String statusUrl = Util.getContextUrlString(subsystemConfiguration.getStorageAdapter().getUrl(),
        		subsystemConfiguration.getStorageAdapter().getMetricsContext()).append("status").toString();
        Request request = this.httpClientBuilder.buildJsonGetRequest(statusUrl, null);
        while (true) {
            Response response = null;
            try {
                response = httpclient.newCall(request).execute();
                if (response.code() != 200) {
                    log.debugf("Hawkular Metrics is not ready yet: %d/%s", response.code(), response.message());
                } else {
                    log.debugf("Hawkular Metrics is ready: %s", response.body().string());
                    break;
                }
            } catch (Exception e) {
                log.debugf("Hawkular Metrics is not ready yet: %s", e.toString());
            } finally {
                if (response != null) {
                    response.body().close();
                }
            }
            Thread.sleep(5000L);
        }

        if (this.subsystemConfiguration.getStorageAdapter().getType().toString().equals("HAWKULAR")) {
            statusUrl = Util.getContextUrlString(subsystemConfiguration.getStorageAdapter().getUrl(),
            		subsystemConfiguration.getStorageAdapter().getInventoryContext()).append("status").toString();
            request = this.httpClientBuilder.buildJsonGetRequest(statusUrl, null);
            while (true) {
                Response response = null;
                try {
                    response = httpclient.newCall(request).execute();
                    if (response.code() != 200) {
                        log.debugf("Hawkular Inventory is not ready yet: %d/%s", response.code(), response.message());
                    } else {
                        log.debugf("Hawkular Inventory is ready: %s", response.body().string());
                        break;
                    }
                } catch (Exception e) {
                    log.debugf("Hawkular Inventory is not ready yet: %s", e.toString());
                } finally {
                    if (response != null) {
                        response.body().close();
                    }
                }
                Thread.sleep(5000L);
            }
        }
    }
    
    /**
     * Registers the feed with the Hawkular system under the given tenant.
     * Note, it is OK to re-register the same feed/tenant combinations.
     *
     * If retryMillis > 0 then this will not return until the feed is properly registered.
     * If the Hawkular server is not up, this could mean we are stuck here for a long time.
     *
     * @param tenantId the feed is registered under the given tenantId
     * @param retryMillis if >0 the amount of millis to elapse before retrying
     * @throws Exception if failed to register feed
     */
    public void registerFeed(String tenantId, int retryMillis) throws Exception {
        // get the payload in JSON format
        Feed.Blueprint feedPojo = new Feed.Blueprint(this.feedId, null);
        String jsonPayload = Util.toJson(feedPojo);

        // build the REST URL...
        // start with the protocol, host, and port, plus context
        StringBuilder url = Util.getContextUrlString(subsystemConfiguration.getStorageAdapter().getUrl(),
        		subsystemConfiguration.getStorageAdapter().getInventoryContext());
        
        // rest of the URL says we want the feeds API
        url.append("entity/feed");

        // now send the REST requests - one for each tenant to register
        OkHttpClient httpclient = this.httpClientBuilder.getHttpClient();

        Map<String, String> header = Collections.singletonMap("Hawkular-Tenant", tenantId);
        Request request = this.httpClientBuilder.buildJsonPostRequest(url.toString(), header, jsonPayload);

        boolean keepRetrying = (retryMillis > 0);
        do {
            try {
                // note that we retry if newCall.execute throws an exception (assuming we were told to retry)
                Response httpResponse = httpclient.newCall(request).execute();

                try {
                    // HTTP status of 201 means success; 409 means it already exists, anything else is an error
                    if (httpResponse.code() == 201) {
                        keepRetrying = false;
                        final String feedObjectFromServer = httpResponse.body().string();
                        final Feed feed = Util.fromJson(feedObjectFromServer, Feed.class);
                        if (this.feedId.equals(feed.getId())) {
                            log.infoUsingFeedId(feed.getId(), tenantId);
                        } else {
                            // do not keep retrying - this is a bad error; we need to abort
                            log.errorUnwantedFeedId(feed.getId(), this.feedId, tenantId);
                            throw new Exception(String.format("Received unwanted feed [%s]", feed.getId()));
                        }
                    } else if (httpResponse.code() == 409) {
                        keepRetrying = false;
                        log.infoFeedIdAlreadyRegistered(this.feedId, tenantId);
                    } else if (httpResponse.code() == 404) {
                        // the server is probably just starting to come up - wait for it if we were told to retry
                        keepRetrying = (retryMillis > 0);
                        throw new Exception(String.format("Is the Hawkular Server booting up? (%d=%s)",
                                httpResponse.code(),
                                httpResponse.message()));
                    } else {
                        // futile to keep retrying and getting the same 500 or whatever error
                        keepRetrying = false;
                        throw new Exception(String.format("status-code=[%d], reason=[%s]",
                                httpResponse.code(),
                                httpResponse.message()));
                    }
                } finally {
                    httpResponse.body().close();
                }
            } catch (Exception e) {
                log.warnCannotRegisterFeed(this.feedId, tenantId, request.urlString(), e.toString());
                if (keepRetrying) {
                    Thread.sleep(retryMillis);
                } else {
                    throw e;
                }
            }
        } while (keepRetrying);
    }

    /**
     * @return feed ID of the agent if the agent has started and the feed was registered; null otherwise
     */
    public String getFeedId() {
        return this.feedId;
    }

    /**
     * @return tenant ID of the agent
     */
    public String getTenantId() {
        return "";
    }

    public SchedulerService getSchedulerService() {
        return schedulerService;
    }

    /**
     * @return the current set of protocol services
     */
    public ProtocolServices getProtocolServices() {
        return protocolServices;
    }

    /**
     * @return builder that let's you create protocol services and their endpoints
     */
    public ProtocolServices.Builder createProtocolServicesBuilder() {
        return ProtocolServices.builder(feedId, diagnostics, trustOnlySSLContextValues);
    }
    
}
