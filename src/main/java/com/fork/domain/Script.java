package com.fork.domain;

public class Script extends Object {
	private int id;
	private String name;
	private String script;

	public Script() {
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

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object s) {
		if (id == ((Script) s).getId())
			return true;
		else
			return false;
	}

}
