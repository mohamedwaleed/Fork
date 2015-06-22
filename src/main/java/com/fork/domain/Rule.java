package com.fork.domain;

import java.util.ArrayList;
import java.util.List;

public class Rule extends Object {
	private int id;
	private int state;
	private String name;
	private String rule;
	private List<String> devicesName, interfacesName;
	private List<String> inMn, inMx, outMn, outMx;

	public Rule() {
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		inMn = new ArrayList<String>();
		inMx = new ArrayList<String>();
		outMn = new ArrayList<String>();
		outMx = new ArrayList<String>();
		String[] condations = this.rule.split("&");
		for (int i = 0; i < condations.length; ++i) {
			String[] parts = condations[i].split("?");
			devicesName.add(parts[0]);
			interfacesName.add(parts[1]);
			inMn.add(parts[2]);
			inMx.add(parts[3]);
			outMn.add(parts[4]);
			outMx.add(parts[5]);
		}
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object s) {
		if (id == ((Rule) s).getId())
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

	public List<String> getInMn() {
		return inMn;
	}

	public void setInMn(List<String> inMn) {
		this.inMn = inMn;
	}

	public List<String> getInMx() {
		return inMx;
	}

	public void setInMx(List<String> inMx) {
		this.inMx = inMx;
	}

	public List<String> getOutMn() {
		return outMn;
	}

	public void setOutMn(List<String> outMn) {
		this.outMn = outMn;
	}

	public List<String> getOutMx() {
		return outMx;
	}

	public void setOutMx(List<String> outMx) {
		this.outMx = outMx;
	}
}
