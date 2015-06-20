package com.fork.persistance.rdf;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.InterfaceData;
import com.fork.domain.Router;
import com.fork.domain.Switch;
import com.fork.domain.Zone;
import com.hp.hpl.jena.query.ResultSet;

public class JenaRetrieval {
	private String URL;
	private String prefix;

	public String getOntURL() {
		return URL;
	}

	public void setOntURL(String ontURL) {
		this.URL = ontURL;
		setPrefix("PREFIX foaf: <" + ontURL + ">\n");
	}

	public String getPrefix() {
		return prefix;
	}

	private void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void addZone(Zone zone, List<Device> devices) {
		String zoneURL = "<" + this.URL + zone.getName() + ">";
		String[] query = new String[4 + devices.size()];
		query[0] = "INSERT DATA {\n";
		query[1] = zoneURL + " a foaf:Zone.\n";
		query[2] = zoneURL + " foaf:zoneName \"" + zone.getName() + "\".\n";
		for (int i = 0; i < devices.size(); ++i) {
			String deviceURL = "<" + this.URL + devices.get(i).getID() + ">";
			query[i + 3] = zoneURL + " foaf:hasDevice " + deviceURL + ".\n";
		}
		query[3 + devices.size()] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 4 + devices.size(); ++i)
			insertQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(insertQuery);
		rdfq.execute(QueryType.INSERT);
	}

	public void addDevice(Device device) {
		String deviceType = "";
		if (device instanceof Router)
			deviceType = DeviceType.Router.toString();
		else if (device instanceof Switch)
			deviceType = DeviceType.Switch.toString();
		String deviceURL = "<" + this.URL + device.getID() + ">";
		List<Interface> interfaces = device.getInterfaces();
		String[] query = new String[6 + interfaces.size()];
		query[0] = "INSERT DATA {\n";
		query[1] = deviceURL + " a foaf:" + deviceType + ".\n";
		query[2] = deviceURL + " foaf:deviceID \"" + device.getID() + "\".\n";
		query[3] = deviceURL + " foaf:deviceIP \"" + device.getIP() + "\".\n";
		query[4] = deviceURL + " foaf:deviceHostName \"" + device.getHostName()
				+ "\".\n";
		for (int i = 0; i < interfaces.size(); ++i) {
			addInterface(interfaces.get(i));
			String interfaceName = interfaces.get(i).getInterfaceName();
			String interfaceURL = "<" + this.URL + interfaceName + ">";
			query[i + 5] = deviceURL + " foaf:hasInterface " + interfaceURL;
		}
		query[5 + interfaces.size()] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 6 + interfaces.size(); ++i)
			insertQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(insertQuery);
		rdfq.execute(QueryType.INSERT);
	}

	private void addInterface(Interface intrface) {
		String interfaceURL = "<" + this.URL + intrface.getInterfaceName()
				+ ">";
		String interfaceDataURL = "<" + this.URL + intrface.getInterfaceName()
				+ ">";
		String[] query = new String[4];
		addInterfaceData(intrface.getInterfaceData(),
				intrface.getInterfaceName());
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceURL + " a foaf:Interface.\n";
		query[2] = interfaceURL + " foaf:hasInterfaceData " + interfaceDataURL
				+ ".\n";
		query[3] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 4; ++i)
			insertQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(insertQuery);
		rdfq.execute(QueryType.INSERT);
	}

	private void addInterfaceData(InterfaceData interfaceData, String ID) {
		String interfaceDataURL = "<" + this.URL + ID + ">";
		String[] query = new String[6];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceDataURL + " a foaf:InterfaceData.\n";
		query[2] = interfaceDataURL + " foaf:time " + interfaceData.getTime()
				+ ".\n";
		query[3] = interfaceDataURL + " foaf:traffic_in "
				+ interfaceData.getInBound() + ".\n";
		query[4] = interfaceDataURL + " foaf:traffic_out "
				+ interfaceData.getOutBound() + ".\n";
		query[5] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 6; ++i)
			insertQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(insertQuery);
		rdfq.execute(QueryType.INSERT);
	}

	public List<Zone> getZones() {
		String[] query = new String[4];
		query[0] = "SELECT ?zoneName\n";
		query[1] = "WHERE {\n";
		query[2] = "?zoneName a <" + this.URL + "Zone>.\n";
		query[3] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 4; ++i)
			selectQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(selectQuery);
		ResultSet result = rdfq.execute(QueryType.SELECT);
		return new ParseResultSet().getZones(result);
	}

	public List<Device> getZoneDevices(Zone zone) {
		String[] query = new String[9];
		query[0] = "SELECT ?className ?deviceID ?deviceIP ?deviceHostName\n";
		query[1] = "WHERE {\n";
		query[2] = "?zone a <" + this.URL + "Zone>.\n";
		query[3] = "?zone foaf:hasDevice ?device.\n";
		query[4] = "?device a ?className.\n";
		query[5] = "?device foaf:deviceID ?deviceID.\n";
		query[6] = "?device foaf:deviceIP ?deviceIP.\n";
		query[7] = "?device foaf:deviceHostName ?deviceHostName.\n";
		query[8] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 9; ++i)
			selectQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(selectQuery);
		ResultSet result = rdfq.execute(QueryType.SELECT);
		List<Device> devices = new ParseResultSet().getDevices(result);
		for (int i = 0; i < devices.size(); ++i)
			devices.get(i).setInterfaces(getDeviceInterfaces(devices.get(i)));
		return devices;
	}

	public List<Device> getDevices() {
		String[] query = new String[8];
		query[0] = "SELECT ?className ?deviceID ?deviceIP ?deviceHostName\n";
		query[1] = "WHERE {\n";
		query[2] = "?className rdfs:subClassOf <" + this.URL + "Device>.\n";
		query[3] = "?device a ?classname.\n";
		query[4] = "?device foaf:deviceID ?deviceID.\n";
		query[5] = "?device foaf:deviceIP ?deviceIP.\n";
		query[6] = "?device foaf:deviceHostName ?deviceHostName.\n";
		query[7] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 8; ++i)
			selectQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(selectQuery);
		ResultSet result = rdfq.execute(QueryType.SELECT);
		List<Device> devices = new ParseResultSet().getDevices(result);
		for (int i = 0; i < devices.size(); ++i)
			devices.get(i).setInterfaces(getDeviceInterfaces(devices.get(i)));
		return devices;
	}

	public List<Device> getFreeDevices() {
		String[] query = new String[10];
		query[0] = "SELECT ?className ?deviceID ?deviceIP ?deviceHostName\n";
		query[1] = "WHERE {\n";
		query[2] = "{?className rdfs:subClassOf <" + this.URL + "Device>.\n";
		query[3] = "?device a ?className.}\n";
		query[4] = "MINUS\n";
		query[5] = "{?zone foaf:hasDevice ?device.}\n";
		query[6] = "?device foaf:deviceID ?deviceID.\n";
		query[7] = "?device foaf:deviceIP ?deviceIP.\n";
		query[8] = "?device foaf:deviceHostName ?deviceHostName.\n";
		query[9] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 10; ++i)
			selectQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(selectQuery);
		ResultSet result = rdfq.execute(QueryType.SELECT);
		List<Device> devices = new ParseResultSet().getDevices(result);
		for (int i = 0; i < devices.size(); ++i)
			devices.get(i).setInterfaces(getDeviceInterfaces(devices.get(i)));
		return devices;
	}

	private List<Interface> getDeviceInterfaces(Device device) {
		String deviceURL = "<" + this.URL + device.getID() + ">";
		String[] query = new String[8];
		query[0] = "SELECT ?interfaceName ?time ?trafficIn ?trafficOut\n";
		query[1] = "WHERE {\n";
		query[2] = deviceURL + " foaf:hasInterface ?interfaceName.\n";
		query[3] = "?interfaceName foaf:hasInterfaceData ?interfaceData.\n";
		query[4] = "?interfaceData foaf:time ?time.\n";
		query[5] = "?interfaceData foaf:traffic_in ?trafficIn.\n";
		query[6] = "?interfaceData foaf:traffic_out ?trafficOut.\n";
		query[7] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 8; ++i)
			selectQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(selectQuery);
		ResultSet result = rdfq.execute(QueryType.SELECT);
		return new ParseResultSet().getInterfaces(result);
	}
}
