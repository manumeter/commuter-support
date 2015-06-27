package ch.zhaw.mas8i.pendlersupport.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {

	@Test
	public void testLocation() {
		Location location = new Location("Location Name");
		assertEquals("Location Name", location.getName());
	}

	@Test
	public void testSetGetName() {
		Location location = new Location("Constructor Name");
		location.setName("Setted Name");
		assertEquals("Setted Name", location.getName());
	}

	@Test
	public void testToString() {
		Location location = new Location("String Name");
		assertEquals("String Name", location.toString());
	}

}
