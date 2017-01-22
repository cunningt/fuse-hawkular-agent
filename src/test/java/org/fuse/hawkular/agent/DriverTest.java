package org.fuse.hawkular.agent;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import org.fuse.hawkular.agent.Driver;
import org.hawkular.agent.model.SubsystemType;

public class DriverTest {
	@Test
	public void testDriver() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(Driver.WILDFLYAGENT_CONFIG_FILE).getFile());
                Assert.assertNotNull(file);
		Assert.assertNotNull(file.getParent());
		
		Driver d = new Driver(file.getParent());
		SubsystemType st = d.readConfiguration();
		Assert.assertNotNull(st);
                Assert.assertEquals(st.getManagedServers().getRemoteJmx().size(), 1);		
		
		d.performRuntime();
		Thread.sleep(15000);
	}
}
