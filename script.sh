#!/bin/sh
java -cp /Users/cunningt/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/cunningt/.m2/repository/junit/junit/4.11/junit-4.11.jar:target/test-classes:target/hawkular-agent.jar org.fuse.hawkular.agent.JMXLocationResolverTest


java -cp /Users/cunningt/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/cunningt/.m2/repository/junit/junit/4.11/junit-4.11.jar:target/test-classes:target/hawkular-agent.jar org.fuse.hawkular.agent.monitor.inventory.NamedObjectTest

java -cp /Users/cunningt/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:/Users/cunningt/.m2/repository/junit/junit/4.11/junit-4.11.jar:target/test-classes:target/hawkular-agent.jar org.fuse.hawkular.agent.monitor.inventory.InventoryIdUtilTest
