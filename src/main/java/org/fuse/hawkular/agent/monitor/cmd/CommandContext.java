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
package org.fuse.hawkular.agent.monitor.cmd;

import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration;
import org.hawkular.agent.monitor.service.MonitorService;
import org.hawkular.agent.model.SubsystemType;

public class CommandContext {
    private final FeedCommProcessor feedComm;
    private final SubsystemType config;
    private final MonitorService discoveryService;

    public CommandContext(
            FeedCommProcessor feedCommProcessor,
            SubsystemType config,
            MonitorService discoveryService) {
        this.feedComm = feedCommProcessor;
        this.config = config;
        this.discoveryService = discoveryService;
    }

    /**
     * @return the object that can be used to send data back to the server
     */
    public FeedCommProcessor getFeedCommProcessor() {
        return feedComm;
    }

    public SubsystemType getMonitorServiceConfiguration() {
        return config;
    }

    public MonitorService getDiscoveryService() {
        return discoveryService;
    }
}
