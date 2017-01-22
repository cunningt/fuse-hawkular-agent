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
package org.fuse.hawkular.agent.monitor.cmd;

import javax.net.ssl.SSLContext;

import org.hawkular.agent.model.StorageType;
import org.hawkular.agent.monitor.util.BaseHttpClientGenerator;

public class WebSocketClientBuilder extends BaseHttpClientGenerator {

    public WebSocketClientBuilder(StorageType storageAdapter, SSLContext sslContext) {
        super(new BaseHttpClientGenerator.Configuration.Builder()
                .username(storageAdapter.getUsername())
                .password(storageAdapter.getPassword())
                .useSsl(storageAdapter.getUrl().startsWith("https"))
                .sslContext(sslContext)
                .keystorePath(storageAdapter.getKeystorePath())
                .keystorePassword(storageAdapter.getKeystorePassword())
                .connectTimeout(storageAdapter.getConnectTimeoutSecs())
                .readTimeout(0)
                .build());
    }

}
