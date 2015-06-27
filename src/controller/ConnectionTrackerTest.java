package ch.zhaw.mas8i.pendlersupport.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.zhaw.mas8i.pendlersupport.model.Configuration;
import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.ConnectionStatus;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.view.Messages;
import ch.zhaw.mas8i.pendlersupport.view.TrafficLight;

public class ConnectionTrackerTest {
	private final String filePath;
	private boolean observerNotifiedMessage;
	private ConnectionStatus observerNotifiedStatus;
	
	public ConnectionTrackerTest() {
		filePath = System.getProperty("java.io.tmpdir") + "/junittest";
	}

	@Test
	public void testObserverMessages() {
		OpenData.clearInstance();
		TransportData data = OpenData.getInstance();
		Configuration config = new Configuration(new Connection(new Location("Zürich HB"), new Location("Zürich HB")), 1, 1, 1);
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		ConnectionTracker tracker = ConnectionTracker.getInstance(data, configHandler);
		Messages observer = new Observer();
		
		observerNotifiedMessage = false;
		tracker.run();
		assertFalse(observerNotifiedMessage);
		
		observerNotifiedMessage = false;
		tracker.addObserver(observer);
		configHandler.setConfiguration(config);
		tracker.run();
		tracker.run();
		assertTrue(observerNotifiedMessage);
		
		observerNotifiedMessage = false;
		tracker.removeObserver(observer);
		tracker.run();
		assertFalse(observerNotifiedMessage);
	}
	
	@Test
	public void testRunObserverStatus() {
		OpenData.clearInstance();
		TransportData data = OpenData.getInstance();
		ConfigurationSerializedFile.clearInstance();
		ConfigurationHandler configHandler = ConfigurationSerializedFile.getInstance(filePath);
		ConnectionTracker tracker = ConnectionTracker.getInstance(data, configHandler);
		TrafficLight observer = new Observer();
		
		observerNotifiedStatus = null;
		tracker.run();
		assertNull(observerNotifiedStatus);
		
		observerNotifiedStatus = null;
		tracker.addObserver(observer);
		tracker.run();
		assertEquals(ConnectionStatus.UNKNOWN, observerNotifiedStatus);
		
		observerNotifiedStatus = null;
		tracker.removeObserver(observer);
		tracker.run();
		assertNull(observerNotifiedStatus);
	}
	
	private class Observer implements Messages, TrafficLight {
		@Override
		public void setMessage(Exception e) {
			observerNotifiedMessage = true;
		}

		@Override
		public void setStatus(ConnectionStatus status, Time nextTime) {
			observerNotifiedStatus = status;
		}
	}
}
