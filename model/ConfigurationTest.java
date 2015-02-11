package ch.zhaw.mas8i.pendlersupport.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigurationTest {

	@Test
	public void testConfiguration() {
		Connection connection = new Connection(new Location("A"), new Location("B"), new Location("C"));
		Configuration configuration = new Configuration(connection, 1, 2, 3);
		assertEquals(connection, configuration.getConnection());
		assertEquals(1, configuration.getOffsetWalk());
		assertEquals(2, configuration.getOffsetSprint());
		assertEquals(3, configuration.getMaxWait());
	}

	@Test
	public void testSetGetConnection() {
		Connection connection = new Connection(new Location("A"), new Location("B"), new Location("C"));
		Connection connectionNew = new Connection(new Location("D"), new Location("E"), new Location("F"));
		Configuration configuration = new Configuration(connection, 1, 2, 3);
		configuration.setConnection(connectionNew);
		assertEquals(connectionNew, configuration.getConnection());
	}

	@Test
	public void testSetGetOffsetWalk() {
		Connection connection = new Connection(new Location("A"), new Location("B"), new Location("C"));
		Configuration configuration = new Configuration(connection, 1, 2, 3);
		configuration.setOffsetWalk(4);
		assertEquals(4, configuration.getOffsetWalk());
	}

	@Test
	public void testSetGetOffsetSprint() {
		Connection connection = new Connection(new Location("A"), new Location("B"), new Location("C"));
		Configuration configuration = new Configuration(connection, 1, 2, 3);
		configuration.setOffsetSprint(4);
		assertEquals(4, configuration.getOffsetSprint());
	}

	@Test
	public void testSetGetMaxWait() {
		Connection connection = new Connection(new Location("A"), new Location("B"), new Location("C"));
		Configuration configuration = new Configuration(connection, 1, 2, 3);
		configuration.setMaxWait(4);
		assertEquals(4, configuration.getMaxWait());
	}
	
	@Test
	public void testToString() {
		Connection connection = new Connection(new Location("A"), new Location("B"), new Location("C"));
		Configuration configuration = new Configuration(connection, 1, 2, 3);
		assertEquals("Configuration [connection=Connection [start=A,destination=B,via=C],offsetWalk=1,offsetSprint=2,maxWait=3]", configuration.toString());
	}

}
