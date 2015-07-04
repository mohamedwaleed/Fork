package com.fork.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataSourceTest {

	DataSource dataSource;
	@Test
	public void getDataSourceNameTest() {
		dataSource = new DataSource();
		dataSource.setDataSourceName("Source1");
		assertEquals("Source1", dataSource.getDataSourceName());
	}
	
	@Test
	public void getID() {
		dataSource = new DataSource();
		dataSource.setID("1");
		assertEquals("1", dataSource.getID());
	}

}
