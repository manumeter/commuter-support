package ch.zhaw.mas8i.pendlersupport.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class OpenDataLocationTest {
	
	@Test
	public void testGetSetStations() {
		OpenDataLocation openDataLocation = new OpenDataLocation();
		
		OpenDataLocation.Stations station = openDataLocation.new Stations();
		station.setName("A");
		
		List<OpenDataLocation.Stations> stations = new ArrayList<>();
		stations.add(station);
		
		openDataLocation.setStations(stations);
		List<OpenDataLocation.Stations> stationsReturn = openDataLocation.getStations();
	
		assertEquals(stationsReturn.get(0), stations.get(0));
	}

	@Test
	public void testToString() {
		OpenDataLocation openDataLocation = new OpenDataLocation();
		
		OpenDataLocation.Stations station = openDataLocation.new Stations();
		station.setName("A");
		
		List<OpenDataLocation.Stations> stations = new ArrayList<>();
		stations.add(station);
		
		openDataLocation.setStations(stations);
		
		assertEquals(openDataLocation.toString(), "Location [stations=[Stations [coordinate=null, distance=null, id=null, name=A, score=null]]]");
	}

}
