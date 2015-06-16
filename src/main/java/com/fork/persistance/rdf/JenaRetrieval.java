package com.fork.persistance.rdf;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.Zone;

public class JenaRetrieval {
	private String prefix;

	public static List<Device> getAvailableDevices() {
		return null;

	}

	public static void createZone(List<Device> devices, String name) {

	}

	public static List<Zone> getAllZones() {
		return null;

	}

	public static List<Device> getZoneDevices(String zoneName) {
		return null;

	}

	public static List<Interface> getDeviceInterfaces(String deviceName) {
		return null;

	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
