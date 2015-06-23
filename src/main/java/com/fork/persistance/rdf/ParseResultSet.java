package com.fork.persistance.rdf;

import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.InterfaceData;
import com.fork.domain.Router;
import com.fork.domain.Switch;
import com.fork.domain.Zone;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class ParseResultSet {
	public List<Zone> getZones(ResultSet result) {
		List<Zone> zones = new ArrayList<Zone>();
		while (result.hasNext()) {
			QuerySolution row = result.nextSolution();
			Zone zone = new Zone();
			zone.setName(row.get("zoneName").toString().split("#")[1]);
			zones.add(zone);
		}
		return zones;
	}

	public List<Device> getDevices(ResultSet result) {
		List<Device> devices = new ArrayList<Device>();
		while (result.hasNext()) {
			QuerySolution row = result.nextSolution();
			String deviceType = row.get("className").toString().split("#")[1];
			Device device = null;
			if (deviceType.equals(DeviceType.Router.toString()))
				device = new Router();
			else if (deviceType.equals(DeviceType.Switch.toString()))
				device = new Switch();
			device.setID(row.getLiteral("deviceID").getString());
			device.setIP(row.getLiteral("deviceIP").getString());
			device.setHostName(row.getLiteral("deviceHostName").getString());
			devices.add(device);
		}
		return devices;
	}

	public List<Interface> getInterfaces(ResultSet result) {
		List<Interface> interfaces = new ArrayList<Interface>();
		while (result.hasNext()) {
			QuerySolution row = result.nextSolution();
			String interfaceID = row.get("interface").toString().split("#")[1];
			String interfaceName = row.get("interfaceName").toString();
			Interface intrface = new Interface();
			intrface.setInterfaceID(interfaceID);
			intrface.setInterfaceName(interfaceName);
			InterfaceData interfaceData = new InterfaceData();
			interfaceData.setTime(row.getLiteral("time").getLong());
			interfaceData.setInBound(row.getLiteral("trafficIn").getDouble());
			interfaceData.setOutBound(row.getLiteral("trafficOut").getDouble());
			intrface.setInterfaceData(interfaceData);
			interfaces.add(intrface);
		}
		return interfaces;
	}
}
