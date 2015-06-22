package com.fork.persistance.rdf;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.Router;
import com.fork.domain.Rule;
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
			query[i + 5] = deviceURL + " foaf:hasInterface " + interfaceURL
					+ ".\n";
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
				+ "Data>";
		addInterfaceData(intrface);
		String[] query = new String[4];
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

	private void addInterfaceData(Interface intrface) {
		String interfaceDataURL = "<" + this.URL + intrface.getInterfaceName()
				+ "Data>";
		String[] query = new String[6];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceDataURL + " a foaf:InterfaceData.\n";
		query[2] = interfaceDataURL + " foaf:time "
				+ intrface.getInterfaceData().getTime() + ".\n";
		query[3] = interfaceDataURL + " foaf:traffic_in "
				+ intrface.getInterfaceData().getInBound() + ".\n";
		query[4] = interfaceDataURL + " foaf:traffic_out "
				+ intrface.getInterfaceData().getOutBound() + ".\n";
		query[5] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 6; ++i)
			insertQuery += query[i];
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(insertQuery);
		rdfq.execute(QueryType.INSERT);
	}

	public void deleteDevice(Device device) {
		String deviceType = "";
		if (device instanceof Router)
			deviceType = DeviceType.Router.toString();
		else if (device instanceof Switch)
			deviceType = DeviceType.Switch.toString();
		String deviceURL = "<" + this.URL + device.getID() + ">";
		String[] query = new String[13];
		query[0] = "DELETE WHERE {\n";
		query[1] = deviceURL + " foaf:deviceID ?ID.\n";
		query[2] = deviceURL + " foaf:deviceIP ?IP.\n";
		query[3] = deviceURL + " foaf:deviceHostName ?hostName.\n";
		query[4] = deviceURL + " foaf:hasInterface ?interface.\n";
		query[5] = deviceURL + " a <" + this.URL + deviceType + ">.\n";
		query[6] = "?interface foaf:hasInterfaceData ?interfaceData.\n";
		query[7] = "?interface a <" + this.URL + "Interface>.\n";
		query[8] = "?interfaceData a <" + this.URL + "InterfaceData>.\n";
		query[9] = "?interfaceData foaf:time ?time.\n";
		query[10] = "?interfaceData foaf:traffic_in ?trafficIn.\n";
		query[11] = "?interfaceData foaf:traffic_out ?trafficOut.\n";
		query[12] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 13; ++i)
			deleteQuery += query[i];
		System.out.println(deleteQuery);
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(deleteQuery);
		rdfq.execute(QueryType.UPDATE);
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
		String[] query = new String[8];
		query[0] = "SELECT ?className ?deviceID ?deviceIP ?deviceHostName\n";
		query[1] = "WHERE {\n";
		query[2] = "<" + this.URL + zone.getName()
				+ "> foaf:hasDevice ?device.\n";
		query[3] = "?device a ?className.\n";
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

	public boolean fireRule(Rule rule) {
		int size = rule.getDevicesName().size();
		for (int i = 0; i < size; ++i) {
			String[] query = new String[17];
			query[0] = "SELECT ?device WHERE {\n";
			query[1] = "?className rdfs:subClassOf <" + this.URL + "Device>.\n";
			query[2] = "?device a ?className.\n";
			query[3] = "?device foaf:deviceHostName ?deviceHostName.\n";
			query[4] = "?device foaf:hasInterface ?interface.\n";
			query[5] = "?interface foaf:hasInterfaceData ?interfaceData.\n";
			query[6] = "?interfaceData foaf:traffic_in ?trafficIn.\n";
			query[7] = "?interfaceData foaf:traffic_out ?trafficOut.\n";
			query[8] = "FILTER(\n";
			query[9] = "?deviceHostName = \"" + rule.getDevicesName().get(i)
					+ "\"\n";
			query[10] = "&& ?interface = <" + this.URL
					+ rule.getInterfacesName().get(i) + ">\n";
			query[11] = "&& ?trafficIn >= " + rule.getInMn().get(i) + "\n";
			query[12] = "&& ?trafficIn <= " + rule.getInMx().get(i) + "\n";
			query[13] = "&& ?trafficOut >= " + rule.getOutMn().get(i) + "\n";
			query[14] = "&& ?trafficOut <= " + rule.getOutMx().get(i) + "\n";
			query[15] = ").\n";
			query[16] = "}\n";
			String selectQuery = "";
			for (int j = 0; j < 17; ++j)
				selectQuery += query[j];
			RdfQuery rdfq = RdfQueryFactory.getQuery();
			rdfq.setPrefix(this.prefix);
			rdfq.setStmt(selectQuery);
			ResultSet result = rdfq.execute(QueryType.SELECT);
			if (!result.hasNext())
				return false;
		}
		return true;
	}
}
