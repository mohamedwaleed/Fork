package com.fork.persistance.rdf;

import java.util.ArrayList;
import java.util.List;

import com.fork.domain.DataSource;
import com.fork.domain.Device;
import com.fork.domain.DeviceType;
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
			zone.setZoneID(row.get("zoneID").toString());
			zone.setName(row.get("zoneName").toString());
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
			String interfaceID = row.get("interfaceID").toString();
			String interfaceName = row.get("interfaceName").toString();
			Interface intrface = new Interface();
			intrface.setID(interfaceID);
			intrface.setName(interfaceName);
			interfaces.add(intrface);
		}
		return interfaces;
	}

	public InterfaceData getInterfaceData(ResultSet result) {
		InterfaceData interfaceData = new InterfaceData();
		while (result.hasNext()) {
			QuerySolution row = result.nextSolution();
			String ID = row.get("interfaceID").toString() + "_Data";
			interfaceData.setID(ID);
		}
		return interfaceData;
	}

	public List<DataSource> getDataSources(ResultSet result) {
		List<DataSource> dataSources = new ArrayList<DataSource>();
		while (result.hasNext()) {
			QuerySolution row = result.nextSolution();
			String ID = row.get("dataSourceID").toString();
			String name = row.get("dataSourceName").toString();
			double value = row.getLiteral("dataSourceValue").getDouble();

			DataSource dataSource = new DataSource();
			dataSource.setID(ID);
			dataSource.setDataSourceName(name);
			dataSource.setDataSourceValue(value);

			dataSources.add(0, dataSource);
		}
		return dataSources;
	}
}
