package com.fork.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RuleTest {

	Rule rule;

	@Test
	public void getStatusTest() {
		rule = new Rule();
		rule.setState(1);
		assertEquals(1, rule.getState());
	}

	@Test
	public void getIDTest() {
		rule = new Rule();
		rule.setID(1);
		assertEquals(1, rule.getID());
	}

	@Test
	public void getNameTest() {
		rule = new Rule();
		rule.setName("rule1");
		assertEquals("rule1", rule.getName());
	}

	@Test
	public void getRuleTest() {
		rule = new Rule();
		rule.setRule("device1#interface1#source1#10#100&device2#interface2#source2#15#112");
		assertEquals(
				"device1#interface1#source1#10#100&device2#interface2#source2#15#112",
				rule.getRule());
	}

	@Test
	public void spliteRuleTest() {
		rule = new Rule();
		rule.setRule("device1#interface1#source1#10#100&device2#interface2#source2#15#112");
		List<String> devicesName = new ArrayList<String>();
		List<String> interfacesName = new ArrayList<String>();
		devicesName.add("device1");
		devicesName.add("device2");
		interfacesName.add("interface1");
		interfacesName.add("interface2");
		assertEquals(devicesName, rule.getDevicesName());
		assertEquals(interfacesName, rule.getInterfacesName());
	}

	@Test
	public void getDevicesNameTest() {
		rule = new Rule();
		List<String> devicesName = new ArrayList<String>();
		devicesName.add("device1");
		devicesName.add("device2");
		rule.setDevicesName(devicesName);
		assertEquals(devicesName, rule.getDevicesName());
	}

	@Test
	public void getInterfacesNameTest() {
		rule = new Rule();
		List<String> InterfacesName = new ArrayList<String>();
		InterfacesName.add("interface1");
		InterfacesName.add("interface2");
		rule.setInterfacesName(InterfacesName);
		assertEquals(InterfacesName, rule.getInterfacesName());
	}

	@Test
	public void getMinValuesTest() {
		rule = new Rule();
		List<List<String>> mins = new ArrayList<List<String>>();
		ArrayList<String> min1 = new ArrayList<String>();
		min1.add("12");
		min1.add("13");
		ArrayList<String> min2 = new ArrayList<String>();
		min1.add("16");
		min1.add("14");
		mins.add((ArrayList<String>) min1);
		mins.add((ArrayList<String>) min2);
		rule.setMinValue(mins);
		assertEquals(mins, rule.getMinValue());
	}

	@Test
	public void getMaxValuesTest() {
		rule = new Rule();
		List<List<String>> maxs = new ArrayList<List<String>>();
		ArrayList<String> max1 = new ArrayList<String>();
		max1.add("120");
		max1.add("130");
		ArrayList<String> max2 = new ArrayList<String>();
		max2.add("160");
		max2.add("140");
		maxs.add((ArrayList<String>) max1);
		maxs.add((ArrayList<String>) max2);
		rule.setMaxValue(maxs);
		assertEquals(maxs, rule.getMaxValue());
	}

	@Test
	public void getDatasourcesNameTest() {
		rule = new Rule();
		List<List<String>> dataSourceNames = new ArrayList<List<String>>();
		ArrayList<String> dataSourceName1 = new ArrayList<String>();
		dataSourceName1.add("source1");
		dataSourceName1.add("source2");
		ArrayList<String> dataSourceName2 = new ArrayList<String>();
		dataSourceName2.add("source3");
		dataSourceName2.add("source4");
		dataSourceNames.add((ArrayList<String>) dataSourceName1);
		dataSourceNames.add((ArrayList<String>) dataSourceName2);
		rule.setDataSourceName(dataSourceNames);
		assertEquals(dataSourceNames, rule.getDataSourceName());
	}

}
