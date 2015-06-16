package com.fork.domain;

public class Device extends Object{
	private String IP;
	private String ID;
	private String hostName;

	public void setIP(String IP) {
		this.IP = IP;
	}

	public String getIP() {
		return IP;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostName() {
		return hostName;
	}

	@Override
	public String toString() {
		return hostName;
	}

	@Override
	public boolean equals(Object s) {
		if (ID.equals(((Device) s).getID()))
			return true;
		else
			return false;
	}

}