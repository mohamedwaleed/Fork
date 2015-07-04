package com.fork.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZoneTest {

	Zone zone;

	@Test
	public void getID() {
		zone = new Zone();
		zone.setZoneID("1");
		assertEquals("1", zone.getZoneID());
	}

	@Test
	public void getName() {
		zone = new Zone();
		zone.setName("script1");
		assertEquals("script1", zone.getName());
	}

}
