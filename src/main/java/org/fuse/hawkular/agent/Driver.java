package org.fuse.hawkular.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.hawkular.agent.model.SubsystemType;
import org.hawkular.agent.model.RemoteJmxType;
import org.hawkular.agent.model.StorageType;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration;
import org.hawkular.agent.monitor.extension.MonitorServiceConfiguration.EndpointConfiguration;
import org.fuse.hawkular.agent.MonitorService;
import org.fuse.hawkular.agent.monitor.extension.MonitorServiceConfigurationBuilder;

import org.fuse.hawkular.agent.monitor.protocol.EndpointService;
import org.fuse.hawkular.agent.monitor.protocol.ProtocolService;
import org.fuse.hawkular.agent.monitor.protocol.ProtocolServices;

import org.hawkular.agent.monitor.protocol.jmx.JMXNodeLocation;
import org.hawkular.agent.monitor.protocol.jmx.JMXSession;

public class Driver {
	
	public static final String WILDFLYAGENT_CONFIG_FILE = "subsystem.xml";
	public static final String HAWKULAR_SERVER_PROPERTIES = "hawkular-server.properties";
	private String _pathToConfig;
	
	
	public Driver(String pathToConfig) {
		_pathToConfig = pathToConfig;
	}
	
	public Properties readProperties() throws IOException {
		Properties props = new Properties();
		File configFile = new File(_pathToConfig, HAWKULAR_SERVER_PROPERTIES);
		FileInputStream fis = new FileInputStream(configFile);
		props.load(fis);
		fis.close();
		return props;
	}
	
	public SubsystemType readConfiguration() throws JAXBException, IOException {
        StringWriter writer = new StringWriter();
        File configFile = new File(_pathToConfig, WILDFLYAGENT_CONFIG_FILE);
        if (configFile.isFile() && configFile.canRead()) {
            FileReader fr = new FileReader(configFile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                writer.write(line);
            }
            fr.close();
        }
        JAXBContext jaxbContext = JAXBContext.newInstance(SubsystemType.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SubsystemType sub = (SubsystemType) unmarshaller.unmarshal(configFile);
        return sub;
	}
	
	public MonitorService getMonitorService(SubsystemType sub) throws Exception {
		MonitorService ms = new MonitorService(sub);
		ms.start();
		return ms;
	}
	
	public void configureDefaultStorageAdapter(SubsystemType st, Properties props) {
		StorageType store = st.getStorageAdapter();
		
		if (store == null) {
			store = new StorageType();
		}
		
		if (props.getProperty("username") != null) {
		   store.setUsername(props.getProperty("username"));
		}
		if (props.getProperty("password") != null) {
		    store.setPassword(props.getProperty("password"));
		}
		if (props.getProperty("url") != null) {
			store.setUrl(props.getProperty("url"));
			store.setUseSsl(props.getProperty("url").startsWith("https"));	
		}
		if (store.getTenantId() == null) {
			store.setTenantId("hawkular");
		}
		if (store.getInventoryContext() == null) {
			store.setInventoryContext("/hawkular/inventory/");
		}
		if (store.getMetricsContext() == null) {
			store.setMetricsContext("/hawkular/metrics/");
		}
		if (store.getFeedcommContext() == null) {
			store.setFeedcommContext("/hawkular/command-gateway/");
		}
		if (store.getConnectTimeoutSecs() == null) {
			store.setConnectTimeoutSecs(10);
		}
		if (store.getReadTimeoutSecs() == null) {
			store.setReadTimeoutSecs(120);
		}
		
		if (store.getFeedId() == null) {
			store.setFeedId("autogenerate");
		}
	}
	
	public void performRuntime() throws Exception {
        SubsystemType sub = readConfiguration();
        Properties hawkServerProperties = readProperties();        		
        System.out.println(sub.toString());
        configureDefaultStorageAdapter(sub, hawkServerProperties);
        MonitorServiceConfiguration config = new MonitorServiceConfigurationBuilder(sub).build();
        
		MonitorService monitorService = getMonitorService(sub);
		monitorService.setConfiguration(config);
        monitorService.startMonitorService();
        
        for (RemoteJmxType rjt : sub.getManagedServers().getRemoteJmx()) {

            String newTenantId = rjt.getTenantId();
        	if (newTenantId != null) {
                try {
                    monitorService.registerFeed(newTenantId, 0);
                } catch (Exception e) {
                	e.printStackTrace();
                }
        	}
        }        
        
        String newEndpointName = "My Remote JMX";
        	
        // create a new endpoint service
        ProtocolServices newServices = monitorService.createProtocolServicesBuilder()
                .jmxProtocolService(config.getJmxConfiguration()).build();
        EndpointService<JMXNodeLocation, JMXSession> endpointService = newServices.getJmxProtocolService()
                .getEndpointServices().get(newEndpointName);

        // put the new endpoint service in the original protocol services container
        ProtocolService<JMXNodeLocation, JMXSession> jmxService = monitorService.getProtocolServices()
                .getJmxProtocolService();
        
        jmxService.add(endpointService);
        jmxService.discoverAll();
	}
	
	public static void main(String args[]) {
		Driver d = new Driver(".");
		try {
			d.performRuntime();
			Thread.sleep(15000);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
