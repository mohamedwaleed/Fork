package com.fork.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScriptTest {

	Script script;

	@Test
	public void getID() {
		script = new Script();
		script.setId(1);
		assertEquals(1, script.getId());
	}

	@Test
	public void getName() {
		script = new Script();
		script.setName("script1");
		assertEquals("script1", script.getName());
	}

}
