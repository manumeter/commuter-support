package ch.zhaw.mas8i.pendlersupport.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.zhaw.mas8i.pendlersupport.model.OpenDataConnection.Checkpoint;

public class OpenDataConnectionTest {

	@Test
	public void testGetSetConnections() {
		// Create OpenDataLocation Start
		OpenDataLocation openDataLocationStart = new OpenDataLocation();
		
		OpenDataLocation.Stations stationStart = openDataLocationStart.new Stations();
		stationStart.setName("A");
		
		List<OpenDataLocation.Stations> stationsStart = new ArrayList<>();
		stationsStart.add(stationStart);
		
		openDataLocationStart.setStations(stationsStart);
		
		// Create OpenDataLocation Destination
		OpenDataLocation openDataLocationDestination = new OpenDataLocation();
		
		OpenDataLocation.Stations stationDestination = openDataLocationDestination.new Stations();
		stationDestination.setName("B");
		
		List<OpenDataLocation.Stations> stationsDestination = new ArrayList<>();
		stationsDestination.add(stationDestination);
		
		openDataLocationDestination.setStations(stationsDestination);
		
		// Crate OpenDataConnection
		OpenDataConnection openDataConnection = new OpenDataConnection();
		
		List<OpenDataConnection.Connections> connections = new ArrayList<>();
		OpenDataConnection.Connections connection = openDataConnection.new Connections();
		
		Checkpoint from = openDataConnection.new Checkpoint();
		from.setStation(openDataLocationStart);
		connection.setFrom(from);
		
		Checkpoint to = openDataConnection.new Checkpoint();
		to.setStation(openDataLocationStart);
		connection.setTo(to);
		
		connections.add(connection);
		openDataConnection.setConnections(connections);
		
		// Asserts
		List<OpenDataConnection.Connections> connectionsReturn = openDataConnection.getConnections();
		assertEquals(connectionsReturn.get(0), connections.get(0));
	}

}
