# Hawkular OpenShift Agent and FIS

This document details the steps necessary to configure and monitor Fuse with the Hawkular OpenShift Agent.   I'm assuming that we'll be using "myproject" as a project namespace throughout the document, and the application I've used as for a test application is called "example".

# Minishift

I've been using Minishift locally - you need to use Minishift 1.0.1, because there are startup issues with metrics on the previous releases.

https://github.com/minishift/minishift/releases/tag/v1.0.1

Put the minishift executable in your path and then :

```
minishift addons install --defaults
minishift addons enable anyuid
minishift addons enable admin
```

Once you've enabled the admin and anyuid addons, you can start minishift up :

```
minishift start --metrics true
```

Next, we need to install the FIS templates.   Login as the admin user

```
oc login
```
(use admin/admin for credentials)


then

```
export BASEURL=https://raw.githubusercontent.com/jboss-fuse/application-templates/GA
oc replace --force -n openshift -f ${BASEURL}/fis-image-streams.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/karaf2-camel-amq-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/karaf2-camel-log-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/karaf2-camel-rest-sql-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/karaf2-cxf-rest-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-amq-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-config-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-drools-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-infinispan-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-rest-sql-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-teiid-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-camel-xml-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-cxf-jaxrs-template.json
oc replace --force -n openshift -f ${BASEURL}/quickstarts/spring-boot-cxf-jaxws-template.json
```

Then we need to deploy the Hawkular OpenShift Agent :

```
git clone https://github.com/hawkular/hawkular-openshift-agent.git
cd hack
HAWKULAR_OPENSHIFT_AGENT_NAMESPACE=myproject ./deploy-openshift-agent 
```

I've been using the spring-boot-camel-xml-archetype example as a test application.   You can generate one simply by:

```
mvn archetype:generate \
  -DarchetypeCatalog=https://maven.repository.redhat.com/ga/io/fabric8/archetypes/archetypes-catalog/2.2.195.redhat-000004/archetypes-catalog-2.2.195.redhat-000004-archetype-catalog.xml \
  -DarchetypeGroupId=org.jboss.fuse.fis.archetypes \
  -DarchetypeArtifactId=spring-boot-camel-xml-archetype \
  -DarchetypeVersion=2.2.195.redhat-000004
```

Once you've created your test application, you need to associate a config map that configures the Hawkular Openshift Agent with your test application.   Edit src/main/fabric8/deployment.yml :

```
spec:
  volumes:
  - name: hawkular-openshift-agent
    configMap:
      name: example-pod-config.yml
  template:
    spec:
      volumes:
      - name: hawkular-openshift-agent
        configMap:
          name: example-pod-config.yml
      container:
      - env:
          - name: AB_JOLOKIA_PASSWORD_RANDOM
            value: "false"
          - name: AB_JOLOKIA_PASSWORD
            valueFrom:
              secretKeyRef:
                name: hosa-secret
                key: password
```

Create a file called example-pod-config.yml - a complete config can be found here :

https://github.com/cunningt/fuse-hawkular-agent/blob/master/example-pod-config.yml

This page contains another good example and has annotations for what each section means:

http://www.hawkular.org/blog/2017/01/17/obst-hosa.html

See the section marked "The Hawkular OpenShift Agent" and the "Config Map for Hosa" figure. 


Then :

```
oc create -f example-pod-config.yml
```      

And deploy your example app :

```
mvn fabric8:deploy
```

# Using Grafana

Following the directions here : 

http://www.hawkular.org/blog/2017/01/17/obst-hosa.html

you can install Grafana to graph and monitor your metrics that are being collected.   Start in the "Display data with Grafana".   You'll need to install Grafana locally (http://docs.grafana.org/installation/) and then install the Hawkular data source plugin (http://www.hawkular.org/hawkular-clients/grafana/docs/quickstart-guide/) - I've found it easier to install the data source through the Grafana plugin directory :

```
grafana-cli plugins install hawkular-datasource
```

Then install a grafana datasource into Openshift :

```
oc new-app docker.io/hawkular/hawkular-grafana-datasource
```

Then, in your local Grafana instance, configure it to point to the Grafana data source you just created: 

[img]http://www.hawkular.org/img/blog/2017/OBST-Grafana-Datasource.png[/img]

I've used the temporary token generated through "oc whoami -t" as the token for Hawkular, but as a long term solution you should create the service account specified in the "Create a service account" section here : http://www.hawkular.org/blog/2017/01/17/obst-hosa.html

Once you set up the Hawkular data source, you should be able to set up graphs.
