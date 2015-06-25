package com.fork.domain;

import java.util.List;

public class Device extends Object {
	private String ID;
	private String IP;
	private String hostName;
	private List<Interface> interfaces;

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public String getIP() {
		return IP;
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
		if (hostName.equals(((Device) s).getHostName()))
			return true;
		else
			return false;
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}
}