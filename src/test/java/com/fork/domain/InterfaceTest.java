package com.fork.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterfaceTest {

	Interface interFace;

	@Test
	public void getIDTest() {
		interFace = new Interface();
		interFace.setID("1");
		assertEquals("1", interFace.getID());
	}

	@Test
	public void getNameTest() {
		interFace = new Interface();
		interFace.setName("interface1");
		assertEquals("interface1", interFace.getName());
	}

	@Test
	public void getInterfaceDataTest() {
		interFace = new Interface();
		InterfaceData interfaceData = new InterfaceData();
		interfaceData.setID("1");
		interFace.setData(interfaceData);
		assertEquals(interfaceData, interFace.getData());
	}
}
