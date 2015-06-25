package com.fork.domain;

public class Interface extends Object {
	private String ID;
	private String name;
	private InterfaceData data;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InterfaceData getData() {
		return data;
	}

	public void setData(InterfaceData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object s) {
		if (name.equals(((Interface) s).getName()))
			return true;
		else
			return false;
	}
}
