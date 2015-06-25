package com.fork.domain;

import java.util.ArrayList;
import java.util.List;

public class Rule extends Object {
	private int ID;
	private int state;
	private String name;
	private String rule;
	private List<String> devicesName, interfacesName;
	private List<List<String>> dataSourceName, minValue, maxValue;

	public Rule() {
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
		splitRule();
	}

	private void splitRule() {
		devicesName = new ArrayList<String>();
		interfacesName = new ArrayList<String>();
		setDataSourceName(new ArrayList<List<String>>());
		minValue = new ArrayList<List<String>>();
		maxValue = new ArrayList<List<String>>();
		String[] condations = this.rule.split("&");
		for (int i = 0; i < condations.length; ++i) {
			String[] parts = condations[i].split("#");
			devicesName.add(parts[0]);
			interfacesName.add(parts[1]);
			List<String> curDataSourceName = new ArrayList<String>();
			List<String> curMinValue = new ArrayList<String>();
			List<String> curMaxValue = new ArrayList<String>();
			for (int j = 2; j < parts.length; j += 3) {
				curDataSourceName.add(parts[j]);
				curMinValue.add(parts[j + 1]);
				curMaxValue.add(parts[j + 2]);
			}
			dataSourceName.add(curDataSourceName);
			minValue.add(curMinValue);
			maxValue.add(curMaxValue);
		}
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object s) {
		if (ID == ((Rule) s).getID())
			return true;
		else
			return false;
	}

	public List<String> getDevicesName() {
		return devicesName;
	}

	public void setDevicesName(List<String> devicesName) {
		this.devicesName = devicesName;
	}

	public List<String> getInterfacesName() {
		return interfacesName;
	}

	public void setInterfacesName(List<String> interfacesName) {
		this.interfacesName = interfacesName;
	}

	public List<List<String>> getMinValue() {
		return minValue;
	}

	public void setMinValue(List<List<String>> minValue) {
		this.minValue = minValue;
	}

	public List<List<String>> getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(List<List<String>> maxValue) {
		this.maxValue = maxValue;
	}

	public List<List<String>> getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(List<List<String>> dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
}
