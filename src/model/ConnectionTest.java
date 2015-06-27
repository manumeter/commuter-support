package ch.zhaw.mas8i.pendlersupport.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectionTest {

	@Test
	public void testConnectionLocationLocation() {
		Location lStart = new Location("A");
		Location lDestination = new Location("B");
		Connection connection = new Connection(lStart, lDestination);
		assertEquals(lStart, connection.getStart());
		assertEquals(lDestination, connection.getDestination());
		assertNull(connection.getVia());
	}

	@Test
	public void testConnectionLocationLocationLocation() {
		Location lStart = new Location("A");
		Location lDestination = new Location("B");
		Location lVia = new Location("C");
		Connection connection = new Connection(lStart, lDestination, lVia);
		assertEquals(lStart, connection.getStart());
		assertEquals(lDestination, connection.getDestination());
		assertEquals(lVia, connection.getVia());
	}

	@Test
	public void testSetGetStart() {
		Location lStart = new Location("A");
		Location lDestination = new Location("B");
		Location lVia = new Location("C");
		Location lNew = new Location("D");
		Connection connection = new Connection(lStart, lDestination, lVia);
		connection.setStart(lNew);
		assertEquals(lNew, connection.getStart());
	}

	@Test
	public void testSetGetDestination() {
		Location lStart = new Location("A");
		Location lDestination = new Location("B");
		Location lVia = new Location("C");
		Location lNew = new Location("D");
		Connection connection = new Connection(lStart, lDestination, lVia);
		connection.setDestination(lNew);
		assertEquals(lNew, connection.getDestination());
	}

	@Test
	public void testSetGetVia() {
		Location lStart = new Location("A");
		Location lDestination = new Location("B");
		Location lVia = new Location("C");
		Location lNew = new Location("D");
		Connection connection = new Connection(lStart, lDestination, lVia);
		connection.setVia(lNew);
		assertEquals(lNew, connection.getVia());
	}
	
	@Test
	public void testToString() {
		Connection connection1 = new Connection(new Location("A"), new Location("B"));
		Connection connection2 = new Connection(new Location("C"), new Location("D"), new Location("E"));
		assertEquals("Connection [start=A,destination=B,via=null]", connection1.toString());
		assertEquals("Connection [start=C,destination=D,via=E]", connection2.toString());
	}

}
