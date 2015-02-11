package ch.zhaw.mas8i.pendlersupport.model;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

public class TimeTest {

	@Test
	public void testIsPassed() {
		Time time1 = new Time();
		Time time2 = new Time();
		time1.subtractMinutes(2);
		time2.addMinutes(2);
		assertTrue(time1.isPassed());
		assertFalse(time2.isPassed());
	}

	@Test
	public void testSetFromString() {
		Time time1 = new Time();
		Time time2 = new Time();
		try {
			time1.setFromString("2014-01-01", "yyyy-MM-dd");
			time2.setFromString("2012-12-30 19:45:32", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			fail("ParseException occured");
		}
		assertEquals("2014-01-01", time1.toString("yyyy-MM-dd"));
		assertEquals("2012-12-30 19:45:32", time2.toString("yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void testAddMinutes() {
		Time time1 = new Time();
		Time time2 = new Time();
		try {
			time1.setFromString("2016-11-02 01:12:59", "yyyy-MM-dd HH:mm:ss");
			time2.setFromString("2016-11-02 01:16:59", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			fail("ParseException occured");
		}
		time1.addMinutes(4);
		assertEquals(time2.getTime(), time1.getTime());
	}

	@Test
	public void testSubtractMinutes() {
		Time time1 = new Time();
		Time time2 = new Time();
		try {
			time1.setFromString("2011-01-26 16:58:02", "yyyy-MM-dd HH:mm:ss");
			time2.setFromString("2011-01-26 17:02:02", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			fail("ParseException occured");
		}
		time2.subtractMinutes(4);
		assertEquals(time2.getTime(), time1.getTime());
	}

	@Test
	public void testGetDiffSeconds() {
		Time time1 = new Time();
		Time time2 = new Time();
		try {
			time1.setFromString("1992-04-28 12:32:01", "yyyy-MM-dd HH:mm:ss");
			time2.setFromString("1992-04-28 12:32:48", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			fail("ParseException occured");
		}
		assertEquals(47, time2.getDiffSeconds(time1));
	}

	@Test
	public void testToString() {
		Time time = new Time();
		try {
			time.setFromString("2032-01-02 23:54:12", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			fail("ParseException occured");
		}
		assertEquals("23:54", time.toString());
	}

	@Test
	public void testToStringString() {
		Time time = new Time();
		try {
			time.setFromString("2002-07-12 22:21:10", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			fail("ParseException occured");
		}
		assertEquals("12.07.2002", time.toString("dd.MM.yyyy"));
		assertEquals("22:21:10", time.toString("HH:mm:ss"));
	}

}
