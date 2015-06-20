package com.fork.domain;

public class Zone extends Object {
	private String name;
	public Zone(){}
	public String getName(){
		return name;
	}
	public void setName(String h){
		this.name = h;
	}
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object s) {
		if (name.equals(((Zone) s).getName()))
			return true;
		else
			return false;
	}
}
