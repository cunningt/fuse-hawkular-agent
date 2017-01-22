# Fuse Hawkular Agent

### Steps for setting up Fuse-EAP :

1) Unzip EAP :
<br/>
[http://porkchop.redhat.com/released/JBEAP-6/6.4.12/](http://porkchop.redhat.com/released/JBEAP-6/6.4.12/) (choose the -full-build.zip version)
<br/>
2) Download Fuse EAP installer : 
<br/>
[http://porkchop.redhat.com/released/JBossFuse/6.3.0/fuse-eap-installer-6.3.0.redhat-187.jar](fuse-eap-installer)
<br/>
3) cd to the `${EAP_HOME}` directory and :
```
java -jar fuse-eap-installer-6.3.0.redhat-187.jar
```
4) cd `${EAP_HOME}/bin`, then
```
./add-user.sh - choose management user, add to group admin
```
6) Start EAP (
```
./standalone.sh -Djboss.socket.binding.port-offset=100
```
(It's important to use the port-offset so we don't conflict with the hawkular server)
<br/>
7) If everything is set up correctly, you should be able to browse to [http://localhost:8180/hawtio/](http://localhost:8180/hawtio/) and log in using the management user you created
<br/>
8) Install the example-camel-cdi-2.4.0.redhat-630187.war quickstart into `${EAP_HOME}/standalone/deployments`:
<br/>
[http://origin-repository.jboss.org/nexus/content/groups/ea/org/wildfly/camel/example-camel-cdi/2.4.0.redhat-630189/example-camel-cdi-2.4.0.redhat-630189.war](example-camel-cdi-2.4.0.redhat-630189.war)
<br/>
9) Browse to [http://localhost:8180/example-camel-cdi/?name=Kermit](http://localhost:8180/example-camel-cdi/?name=Kermit) so that we Camel exchanges now have a message count.

### Set up Hawkular 

1) Build [https://github.com/hawkular/hawkular-services](https://github.com/hawkular/hawkular-services) master branch and use the the distribution there (need Jolokia support).
<br/>
2) Run through the steps here [http://www.hawkular.org/hawkular-services/docs/installation-guide/](http://www.hawkular.org/hawkular-services/docs/installation-guide/), and enable the wildfly agent
<br/>
3) Add the following to the `standalone.xml` config - see to `<managed-servers>` :
```
                <remote-jmx name="My Remote JMX"
                  enabled="true"
                  resource-type-sets="FuseJMX,MainJMX,CamelTracer,CamelServices,Camel,CamelProcessor,CamelEventNotifiers,CamelErrorHandlers,CamelEndpoints,CamelConsumers,CamelComponents"
                  metric-tags="server=%ManagedServerName,host=127.0.0.1"
                  metric-id-template="%ResourceName_%MetricTypeName"
                  username="admin"
                  password="PASSWORD"
                  url="http://localhost:8180/hawtio/jolokia"/>
```
4) Add the following to `standalone.xml` in the `<resource-type-set-jmx/>` section:
```
            <resource-type-set-jmx name="FuseJMX" enabled="true">
                <resource-type-jmx name="Fuse MBean" resource-name-template="Fuse Server [%type%]"
                  object-name="jboss.as:core-service=patching,layer=fuse">
                </resource-type-jmx>
            </resource-type-set-jmx>
            
           <resource-type-set-jmx name="Camel" enabled="true">
                <resource-type-jmx name="Camel Context" parents="Fuse MBean" metric-sets="CamelProcessorMetrics"
                  object-name="org.apache.camel:type=context,name=*,context=*"
                  resource-name-template="Camel Context %name%"/>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelComponents" enabled="true">
                <resource-type-jmx name="Camel Components" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=components"
                  resource-name-template="Component %name%">
                  <resource-config-jmx name="CamelId" attribute="CamelId"/>
                  <resource-config-jmx name="ManagementName" attribute="CamelManagementName"/>
                  <resource-config-jmx name="ComponentName" attribute="ComponentName"/>
                  <resource-config-jmx name="State" attribute="State"/>
                </resource-type-jmx>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelConsumers" enabled="true">
                <resource-type-jmx name="Camel Consumers" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=consumers"
                  metric-sets="CamelRouteMetrics"
                  resource-name-template="Consumer %name%">
                  <resource-config-jmx name="CamelId" attribute="CamelId"/>
                  <resource-config-jmx name="ManagementName" attribute="CamelManagementName"/>
                  <resource-config-jmx name="RouteId" attribute="RouteId"/>
                  <resource-config-jmx name="ServiceType" attribute="ServiceType"/>
                  <resource-config-jmx name="State" attribute="State"/>
                  <resource-config-jmx name="StaticService" attribute="StaticService"/>
                  <resource-config-jmx name="SupportSuspension" attribute="SupportSuspension"/>
                  <resource-config-jmx name="Suspended" attribute="Suspended"/>
                </resource-type-jmx>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelEndpoints" enabled="true">
                <resource-type-jmx name="Camel Endpoints" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=endpoints"
                  resource-name-template="Endpoint %name%">
                  <resource-config-jmx name="CamelId" attribute="CamelId"/>
                  <resource-config-jmx name="ManagementName" attribute="CamelManagementName"/>
                  <resource-config-jmx name="EndpointUri" attribute="EndpointUri"/>
                  <resource-config-jmx name="Singleton" attribute="Singleton"/>
                  <resource-config-jmx name="State" attribute="State"/>
                </resource-type-jmx>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelErrorHandlers" enabled="true">
                <resource-type-jmx name="Camel Error Handlers" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=errorhandlers"
                  resource-name-template="Endpoint %name%">
                  <resource-config-jmx name="AllowRedeliveryWhileStopping" attribute="AllowRedeliveryWhileStopping"/>
                  <resource-config-jmx name="BackOffMultiplier" attribute="BackOffMultiplier"/>
                  <resource-config-jmx name="CamelId" attribute="CamelId"/>
                  <resource-config-jmx name="ManagementName" attribute="CamelManagementName"/>
                  <resource-config-jmx name="CollisionAvoidanceFactor" attribute="CollisionAvoidanceFactor"/>
                  <resource-config-jmx name="CollisionAvoidancePercent" attribute="CollisionAvoidancePercent"/>
                  <resource-config-jmx name="DeadLetterChannel" attribute="DeadLetterChannel"/>
                  <resource-config-jmx name="DeadLetterChannelEndpointUri" attribute="DeadLetterChannelEndpointUri"/>
                  <resource-config-jmx name="DeadLetterHandleNewException" attribute="DeadLetterHandleNewException"/>
                  <resource-config-jmx name="DeadLetterUseOriginalMessage" attribute="DeadLetterUseOriginalMessage"/>
                  <resource-config-jmx name="DelayPattern" attribute="DelayPattern"/>
                  <resource-config-jmx name="LogContinued" attribute="LogContinued"/>
                  <resource-config-jmx name="LogExhausted" attribute="LogExhausted"/>
                  <resource-config-jmx name="LogExhaustedMessageBody" attribute="LogExhaustedMessageBody"/>
                  <resource-config-jmx name="LogExhaustedMessageHistory" attribute="LogExhaustedMessageHistory"/>
                  <resource-config-jmx name="LogHandled" attribute="LogHandled"/>
                  <resource-config-jmx name="LogNewException" attribute="LogNewException"/>
                  <resource-config-jmx name="LogRetryStackTrace" attribute="LogRetryStackTrace"/>
                  <resource-config-jmx name="LogStackTrace" attribute="LogStackTrace"/>
                  <resource-config-jmx name="MaximumRedeliveries" attribute="MaximumRedeliveries"/>
                  <resource-config-jmx name="MaximumRedeliveryDelay" attribute="MaximumRedeliveryDelay"/>
                  <resource-config-jmx name="PendingRedeliveryCount" attribute="PendingRedeliveryCount"/>
                  <resource-config-jmx name="RedeliveryDelay" attribute="RedeliveryDelay"/>
                  <resource-config-jmx name="RetriesExhaustedLogLevel" attribute="RetriesExhaustedLogLevel"/>
                  <resource-config-jmx name="RetryAttemptedLogLevel" attribute="RetryAttemptedLogLevel"/>
                  <resource-config-jmx name="SupportRedelivery" attribute="SupportRedelivery"/>
                  <resource-config-jmx name="SupportTransactions" attribute="SupportTransactions"/>
                  <resource-config-jmx name="UseCollisionAvoidance" attribute="UseCollisionAvoidance"/>
                  <resource-config-jmx name="UseExponentialBackOff" attribute="UseExponentialBackOff"/>
                </resource-type-jmx>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelEventNotifiers" enabled="true">
                <resource-type-jmx name="Camel Event Notifiers" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=eventnotifiers"
                  resource-name-template="Endpoint %name%">
                  <resource-config-jmx name="IgnoreCamelContextEvents" attribute="IgnoreCamelContextEvents"/>
                  <resource-config-jmx name="IgnoreExchangeCompletedEvent" attribute="IgnoreExchangeCompletedEvent"/>
                  <resource-config-jmx name="IgnoreExchangeCreatedEvent" attribute="IgnoreExchangeCreatedEvent"/>
                  <resource-config-jmx name="IgnoreExchangeEvents" attribute="IgnoreExchangeEvents"/>
                  <resource-config-jmx name="IgnoreExchangeFailedEvents" attribute="IgnoreExchangeFailedEvents"/>
                  <resource-config-jmx name="IgnoreExchangeRedeliveryEvents" attribute="IgnoreExchangeRedeliveryEvents"/>
                  <resource-config-jmx name="IgnoreExchangeSendingEvents" attribute="IgnoreExchangeSendingEvents"/>
                  <resource-config-jmx name="IgnoreExchangeSentEvents" attribute="IgnoreExchangeSentEvents"/>
                  <resource-config-jmx name="IgnoreRouteEvents" attribute="IgnoreRouteEvents"/>
                  <resource-config-jmx name="IgnoreServiceEvents" attribute="IgnoreServiceEvents"/>
                </resource-type-jmx>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelProcessor" enabled="true">
                <resource-type-jmx name="Camel Processor" metric-sets="CamelProcessorMetrics" parents="Camel Context"
                  object-name="org.apache.camel:type=processors,name=*,context=*"
                  resource-name-template="Processor %name%"/>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelServices" enabled="true">
                <resource-type-jmx name="Camel Services" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=services"
                  resource-name-template="Service %name%">
                  <resource-config-jmx name="CamelId" attribute="CamelId"/>
                  <resource-config-jmx name="CamelManagementName" attribute="CamelManagementName"/>
                  <resource-config-jmx name="DynamicSize" attribute="DynamicSize"/>
                  <resource-config-jmx name="MaximumCacheSize" attribute="MaximumCacheSize"/>
                  <resource-config-jmx name="Route" attribute="Route"/>
                  <resource-config-jmx name="ServiceType" attribute="ServiceType"/>
                  <resource-config-jmx name="Size" attribute="Size"/>
                  <resource-config-jmx name="Source" attribute="Source"/>
                  <resource-config-jmx name="State" attribute="State"/>
                  <resource-config-jmx name="StaticService" attribute="StaticService"/>
                  <resource-config-jmx name="StaticSize" attribute="StaticSize"/>
                  <resource-config-jmx name="SupportSuspension" attribute="SupportSuspension"/>
                  <resource-config-jmx name="Suspended" attribute="Suspended"/>
                </resource-type-jmx>
            </resource-type-set-jmx>

            <resource-type-set-jmx name="CamelTracer" enabled="true">
                <resource-type-jmx name="Camel Tracer" parents="Camel Context"
                  object-name="org.apache.camel:context=*,name=*,type=tracer"
                  resource-name-template="Tracer %name%">
                  <resource-config-jmx name="BacklogSize" attribute="BacklogSize"/>
                  <resource-config-jmx name="BodyIncludeFiles" attribute="BodyIncludeFiles"/>
                  <resource-config-jmx name="BodyIncludeStreams" attribute="BodyIncludeStreams"/>
                  <resource-config-jmx name="BodyMaxChars" attribute="BodyMaxChars"/>
                  <resource-config-jmx name="CamelId" attribute="CamelId"/>
                  <resource-config-jmx name="CamelManagementName" attribute="CamelManagementName"/>
                  <resource-config-jmx name="Enabled" attribute="Enabled"/>
                  <resource-config-jmx name="FallbackTimeout" attribute="FallbackTimeout"/>
                  <resource-config-jmx name="LoggingLevel" attribute="LoggingLevel"/>
                  <resource-config-jmx name="RemoveOnDump" attribute="RemoveOnDump"/>
                  <resource-config-jmx name="SingleStepMode" attribute="SingleStepMode"/>
                  <resource-config-jmx name="TraceCounter" attribute="TraceCounter"/>
                  <resource-config-jmx name="TraceFilter" attribute="TraceFilter"/>
                  <resource-config-jmx name="TracePattern" attribute="TracePattern"/>
                </resource-type-jmx>
            </resource-type-set-jmx>
```
5. Add the following to `standalone.xml` in the `<metric-set-jmx/>` section:
```
            <metric-set-jmx name="CamelProcessorMetrics">
            <metric-jmx name="ExchangesCompleted" metric-type="counter" interval="30" attribute="ExchangesCompleted"/>
            <metric-jmx name="ExchangesFailed" metric-type="counter" interval="30" attribute="ExchangesFailed"/>
            <metric-jmx name="ExchangesTotal" metric-type="counter" interval="30" attribute="ExchangesTotal"/>
            <metric-jmx name="MinProcessingTime" metric-type="gauge" interval="30" attribute="MinProcessingTime"/>
            <metric-jmx name="MaxProcessingTime" metric-type="gauge" interval="30" attribute="MaxProcessingTime"/>
            <metric-jmx name="MeanProcessingTime" metric-type="gauge" interval="30" attribute="MeanProcessingTime"/>
            <metric-jmx name="TotalProcessingTime" metric-type="gauge" interval="30" attribute="TotalProcessingTime"/>
            <metric-jmx name="LastProcessingTime" metric-type="gauge" interval="30" attribute="LastProcessingTime"/>
            </metric-set-jmx>
            <metric-set-jmx name="CamelRouteMetrics">
                <metric-jmx name="InflightExchanges" metric-type="counter" interval="30" attribute="InflightExchanges"/>
                <metric-jmx name="OldestInflightExchangeDuration" metric-type="gauge" interval="30" attribute="OldestInflightExchangeDuration"/>
            </metric-set-jmx>
```

