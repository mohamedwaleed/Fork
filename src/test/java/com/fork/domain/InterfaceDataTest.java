package com.fork.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InterfaceDataTest {

	InterfaceData interfaceData;
	@Test
	public void getDataSourcesTest() {
		interfaceData = new InterfaceData();
		DataSource datasource1 = new DataSource();
		datasource1.setID("1");
		DataSource datasource2 = new DataSource();
		datasource2.setID("2");
		List<DataSource> datasources = new ArrayList<DataSource>();
		datasources.add(datasource1);
		datasources.add(datasource2);
		interfaceData.setDataSources(datasources);
		assertEquals(datasources,interfaceData.getDataSources());
	}
	
	@Test
	public void getID(){
		interfaceData = new InterfaceData();
		interfaceData.setID("1");
		assertEquals("1", interfaceData.getID());
	}

}
