package com.fork.persistance.sqlite;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseConnectorTest {

	@Test
	public void getDatabaseConnectionTest() {
		assertNotEquals(null, DatabaseConnector.getDatabaseConnection());
	}

}
