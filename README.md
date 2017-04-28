# Using the Hawkular Java Agent with Fuse

This document details the steps necessary to configure and run Fuse with the Hawkular java agent.

# SpringBoot in FIS

- Add the following sections to your application's pom.xml :

```
      <plugin>
        <artifactId>maven-dependency-plugin</artifactId>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
              <goal>copy</goal>
            </goals>
            <configuration>
               <useBaseVersion>true</useBaseVersion>
              <artifactItems>
              <artifactItem>
               <groupId>org.hawkular.agent</groupId>
               <artifactId>hawkular-javaagent</artifactId>
               <version>0.28.2.Final-SNAPSHOT</version>
               <outputDirectory>${project.build.directory}/docker/${project.artifactId}/latest/build/maven/</outputDirectory>
              </artifactItem>
              </artifactItems>
            </configuration>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <artifactId>maven-resources-plugin</artifactId>
        <executions>
          <execution>
            <id>copy-resources</id>
            <phase>prepare-package</phase>
            <goals>
              <goal>copy-resources</goal>
            </goals>
            <configuration>
              <outputDirectory>${project.build.directory}/docker/${project.artifactId}/latest/build/maven/</outputDirectory>
              <resources>
                <resource>
                  <directory>src/main/resources</directory>
                  <includes>
                    <include>simple.yaml</include>
                  </includes>
                  <filtering>false</filtering>
                </resource>
              </resources>
            </configuration>
          </execution>
        </executions>
      </plugin>
```

- Copy simple.yaml to src/main/resources of your application.    You may need to edit the connection information in the "storage-adapter:" section in order to reflect the location and credentials for your Hawkular server.

- Edit deployment.yml and replace <YOUR-JAR_NAME> with the finalName of your JAR.  Copy deployment.yml to src/main/fabric8.


# Attaching the agent to Karaf

- Copy the Hawkular Java Agent to somewhere within the Fuse distribution

- Copy simple.yaml to the Fuse distribution

- Edit the bin/karaf script and add the folowing inside of

``
    -javaagent:<PATH-TO-HAWKULAR-JAR>/hawkular-javaagent-0.29.4.Final-SNAPSHOT.jar=config=simple.yaml"
``

# Using Grafana to display results

After installing Grafana on a machine (http://www.hawkular.org/hawkular-clients/grafana/docs/quickstart-guide/) provides a number of good starting points, install the Hawkular Plugin for Grafana using the grafana-cli :

```
grafana-cli plugins install hawkular-datasource
```

## Configure a Data Source

Once the Grafana plugin is installed, go ahead and add a new datasource by choosing "Data Sources" from the drop down.   Fill out the details of your Hawkular installation.   Add the authorization credentials and tenant settings that match your Hawkular configuration file, and then Save & Test to make sure that your data source configuration works.

![Image of Grafana Data Source Configuration](https://github.com/cunningt/fuse-hawkular-agent/blob/master/hawkular-grafana.png?raw=true)

## Graph Metrics

Choose Dashboards from the main drop down, and create a new dashboard.  Choose Graph, click on the Panel title, and select edit.

For "Panel Data Source", select Hawkular.   Once you select the data source, you should be able to choose metrics to graph by selecting either Gauges or Counters, and then specifying the metric to graph in the "select metric" drop down box.   The metric names are a combination of the Hawkular Server name, the Camel Context name, and then the metric itself (Processor, etc) so that they are unique.    While the metric names are unwieldy, you can make your graph more readable by changing the graph settings to give better names.

You may also display by tag.  The sample Hawkular configuration provided provides a number of tags to search by.
