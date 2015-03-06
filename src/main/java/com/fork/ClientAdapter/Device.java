package com.fork.ClientAdapter;

public class Device {
	private String IP;
	private String hostName;

	void setIP(String IP) {
		this.IP = IP;
	}

	String getIP() {
		return IP;
	}

	void setHostName(String hostName) {
		this.hostName = hostName;
	}

	String getHostName() {
		return hostName;
	}

}