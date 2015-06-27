package ch.zhaw.mas8i.pendlersupport.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import ch.zhaw.mas8i.pendlersupport.model.Connection;
import ch.zhaw.mas8i.pendlersupport.model.Location;
import ch.zhaw.mas8i.pendlersupport.model.Time;
import ch.zhaw.mas8i.pendlersupport.model.TransportDataException;

public class OpenDataTest {

	@Test
	public void testGetTimeList() {
		SortedSet<Time> times = new TreeSet<>();
		Connection connection = new Connection(new Location("Zürich HB"), new Location("Winterthur"));
		
		try {
			times = OpenData.getInstance().getTimeList(connection, new Time());
		} catch (TransportDataException e) {
			fail("Connection to OpenData impossible");
		}
		
		assertTrue(times.size() >= 1);
		assertTrue(times.first() instanceof Time);
	}

	@Test
	public void testGetPossibleLocationList() {
		List<Location> locations = new ArrayList<>();
		
		try {
			locations = OpenData.getInstance().getPossibleLocationList("züri");
		} catch (TransportDataException e) {
			fail("Connection to OpenData impossible");
		}
		
		assertTrue(locations.size() > 3);
		assertEquals("Zürich", locations.get(0).getName());
	}

}
