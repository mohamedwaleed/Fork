package com.fork.domain;

public class Rule extends Object {
	private int id;
	private String name;
	private String rule;
	private int state;

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

}
