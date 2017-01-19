# fuse-hawkular-agent
Fuse Hawkular Agent

Steps for setting up Fuse

1) Unzip EAP : http://porkchop.redhat.com/released/JBEAP-6/6.4.12/ (choose the -full-build.zip version)
2) Download Fuse EAP installer : 
http://porkchop.redhat.com/released/JBossFuse/6.3.0/fuse-eap-installer-6.3.0.redhat-187.jar
3) cd to the EAP_HOME directory (where you installed EAP) and :
java -jar fuse-eap-installer-6.3.0.redhat-187.jar
4) cd $EAP_HOME/bin
5) ./add-user.sh - choose management user, add to group admin
6) Start EAP (./standalone.sh -Djboss.socket.binding.port-offset=100
It's important to use the port-offset so we don't conflict with the hawkular server
7) If everything is set up correctly, you should be able to browse to http://localhost:8180/hawtio/ and log in using the management user you created
8) Install the example-camel-cdi-2.4.0.redhat-630187.war quickstart into ${EAP_HOME}/standalone/deployments 
http://origin-repository.jboss.org/nexus/content/groups/ea/org/wildfly/camel/example-camel-cdi/2.4.0.redhat-630189/example-camel-cdi-2.4.0.redhat-630189.war


Then we set up Hawkular 
1) Build https://github.com/hawkular/hawkular-services master branch and used the the distribution there since the Jolokia support isn't in the latest released distribution.  I ran through the steps here (http://www.hawkular.org/hawkular-services/docs/installation-guide/), and enabled the wildfly agent, then added the config you can find in 

https://github.com/cunningt/fuse-hawkular-agent/blob/master/standalone.xml

Browse to http://localhost:8080/example-camel-cdi/?name=Kermit.

