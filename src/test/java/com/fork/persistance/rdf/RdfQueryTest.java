package com.fork.persistance.rdf;

import static org.junit.Assert.*;

import org.junit.Test;

public class RdfQueryTest {

	RdfQuery rdf;

	@Test
	public void getStmtTest() {
		rdf = new RdfQuery();
		rdf.setStmt("statment");
		assertEquals("statment", rdf.getStmt());
	}

	@Test
	public void getPrefixTest() {
		rdf = new RdfQuery();
		rdf.setPrefix("prefix");;
		assertEquals("prefix", rdf.getPrefix());
	}
}
