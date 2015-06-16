package com.fork.domain;

public class Interface extends Object {
	private String name;

	public Interface() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
