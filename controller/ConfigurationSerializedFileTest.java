package ch.zhaw.mas8i.pendlersupport.controller;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.view.ConnectionInfo;
import ch.zhaw.mas8i.pendlersupport.view.Messages;

public class ConfigurationSerializedFileTest {
	private final String filePath;
	private boolean observerNotifiedMessage;
	private boolean observerNotifiedConnection;
	
	public ConfigurationSerializedFileTest() {
		filePath = System.getProperty("java.io.tmpdir") + "/junittest";
	}

	@Test
	public void testConfigurationSerializedFile() {
		ConfigurationSerializedFile.clearInstance();
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		assertTrue(configHandler instanceof ConfigurationSerializedFile);
	}

	@Test
	public void testSetGetConfiguration() {
		ConfigurationSerializedFile.clearInstance();
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		Configuration config = new Configuration(new Connection(new Location("A"), new Location("B")), 2, 1, 2);
		configHandler.setConfiguration(config);
		assertEquals(config, configHandler.getConfiguration());
	}
	
	@Test
	public void testSaveLoad() {
		new File(filePath).delete();
		
		ConfigurationSerializedFile.clearInstance();
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		Configuration config = new Configuration(new Connection(new Location("A"), new Location("B")), 2, 1, 2);
		configHandler.setConfiguration(config);
		assertFalse(configHandler.hasSaved());
		configHandler.save();
		assertTrue(configHandler.hasSaved());
		
		ConfigurationSerializedFile.clearInstance();
		configHandler = ConfigurationSerializedFile.getInstance(filePath);
		assertFalse(configHandler.hasLoaded());
		configHandler.load();
		assertTrue(configHandler.hasLoaded());
		Configuration configReturn = configHandler.getConfiguration();
		
		assertEquals(config.getConnection().getStart().getName(), configReturn.getConnection().getStart().getName());
		assertEquals(config.getConnection().getDestination().getName(), configReturn.getConnection().getDestination().getName());
		assertEquals(config.getOffsetWalk(), configReturn.getOffsetWalk());
		assertEquals(config.getOffsetSprint(), configReturn.getOffsetSprint());
		assertEquals(config.getMaxWait(), configReturn.getMaxWait());
	}
	
	@Test
	public void testObserverMessages() {
		new File(filePath).delete();
		
		ConfigurationSerializedFile.clearInstance();
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		Messages observer = new Observer();
		
		observerNotifiedMessage = false;
		configHandler.load();
		assertFalse(observerNotifiedMessage);
		
		observerNotifiedMessage = false;
		configHandler.addObserver(observer);
		configHandler.load();
		assertTrue(observerNotifiedMessage);
		
		observerNotifiedMessage = false;
		configHandler.removeObserver(observer);
		configHandler.load();
		assertFalse(observerNotifiedMessage);
	}
	
	@Test
	public void testObserverConnectionInfo() {
		ConfigurationSerializedFile.clearInstance();
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		Configuration config = new Configuration(new Connection(new Location("E"), new Location("F")), 7, 4, 5);
		ConnectionInfo observer = new Observer();
		
		observerNotifiedConnection = false;
		configHandler.setConfiguration(config);
		assertFalse(observerNotifiedConnection);
		
		observerNotifiedConnection = false;
		configHandler.addObserver(observer);
		configHandler.setConfiguration(config);
		assertTrue(observerNotifiedConnection);
		
		observerNotifiedConnection = false;
		configHandler.removeObserver(observer);
		configHandler.setConfiguration(config);
		assertFalse(observerNotifiedConnection);
	}
	
	private class Observer implements Messages, ConnectionInfo {
		@Override
		public void setMessage(Exception e) {
			observerNotifiedMessage = true;
		}
		
		@Override
		public void setConnection(Connection connection) {
			observerNotifiedConnection = true;
		}
	}
}
