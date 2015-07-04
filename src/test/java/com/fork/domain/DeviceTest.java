package com.fork.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ClassUtils.Interfaces;
import org.junit.Test;

public class DeviceTest {

	Device device;

	@Test
	public void getHostNameTest() {
		device = new Device();
		device.setHostName("host1");
		assertEquals("host1", device.getHostName());
	}

	@Test
	public void getIDTest() {
		device = new Device();
		device.setID("1");
		assertEquals("1", device.getID());
	}

	@Test
	public void getIPTest() {
		device = new Device();
		device.setIP("12.12.0.1");
		assertEquals("12.12.0.1", device.getIP());
	}

	@Test
	public void getInterfacesTest() {
		device = new Device();
		Interface interFace1 = new Interface();
		interFace1.setID("1");
		Interface interFace2 = new Interface();
		interFace1.setID("2");
		List<Interface> interFaces = new ArrayList<Interface>();
		interFaces.add(interFace1);
		interFaces.add(interFace2);
		device.setInterfaces(interFaces);
		assertEquals(interFaces, device.getInterfaces());
	}
}
