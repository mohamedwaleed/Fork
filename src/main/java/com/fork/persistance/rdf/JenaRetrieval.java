package com.fork.persistance.rdf;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.Router;
import com.fork.domain.Rule;
import com.fork.domain.Zone;
import com.hp.hpl.jena.query.ResultSet;

public class JenaRetrieval {
	private String URL;
	private String prefix;

	public JenaRetrieval() {
		this.URL = RdfUtilty.OWL_URL;
		this.prefix = "PREFIX foaf: <" + this.URL + ">\n";
	}

	public void addZone(Zone zone) {
		String zoneURL = getZoneURL(zone);
		String[] query = new String[4];
		query[0] = "INSERT DATA {\n";
		query[1] = zoneURL + " a foaf:Zone.\n";
		query[2] = zoneURL + " foaf:zoneName \"" + zone.getName() + "\".\n";
		query[3] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 4; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
	}

	public void addDevice(Device device) {
		String deviceURL = getDeviceURL(device);
		String deviceType = getDeviceType(device);
		String[] query = new String[6];
		query[0] = "INSERT DATA {\n";
		query[1] = deviceURL + " a foaf:" + deviceType + ".\n";
		query[2] = deviceURL + " foaf:deviceID \"" + device.getID() + "\".\n";
		query[3] = deviceURL + " foaf:deviceIP \"" + device.getIP() + "\".\n";
		query[4] = deviceURL + " foaf:deviceHostName \"" + device.getHostName()
				+ "\".\n";
		query[5] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 6; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
	}

	public void addInterface(Interface intrface) {
		String interfaceURL = getInterfaceURL(intrface);
		String[] query = new String[4];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceURL + " a foaf:Interface.\n";
		query[2] = interfaceURL + " foaf:interfaceName \""
				+ intrface.getInterfaceName() + "\".\n";
		query[3] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 4; ++i)
			insertQuery += query[i];
		System.out.println(insertQuery);
		fireInsertQuery(insertQuery);
		addInterfaceData(intrface);
		addInterfaceInterfaceDataRelation(intrface);
	}

	private void addInterfaceData(Interface intrface) {
		String interfaceDataURL = getInterfaceDataURL(intrface);
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
		fireInsertQuery(insertQuery);
	}

	public void addZoneDeviceRelation(Zone zone, Device device) {
		String zoneURL = getZoneURL(zone);
		String deviceURL = getDeviceURL(device);
		String[] query = new String[3];
		query[0] = "INSERT DATA {\n";
		query[1] = zoneURL + " foaf:hasDevice " + deviceURL + ".\n";
		query[2] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 3; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
	}

	public void addDeviceInterfaceRelation(Device device, Interface intrface) {
		String deviceURL = getDeviceURL(device);
		String interfaceURL = getInterfaceURL(intrface);
		String[] query = new String[3];
		query[0] = "INSERT DATA {\n";
		query[1] = deviceURL + " foaf:hasInterface " + interfaceURL + ".\n";
		query[2] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 3; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
	}

	private void addInterfaceInterfaceDataRelation(Interface intrface) {
		String interfaceURL = getInterfaceURL(intrface);
		String interfaceDataURL = getInterfaceDataURL(intrface);
		String[] query = new String[3];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceURL + " foaf:hasInterfaceData " + interfaceDataURL
				+ ".\n";
		query[2] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 3; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
	}

	public void deleteZone(Zone zone) {
		String zoneURL = getZoneURL(zone);
		String[] query = new String[4];
		query[0] = "DELETE WHERE {\n";
		query[1] = zoneURL + " a foaf:Zone.\n";
		query[2] = zoneURL + " foaf:zoneName ?zoneName.\n";
		query[3] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 4; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	public void deleteDevice(Device device) {
		String deviceURL = getDeviceURL(device);
		String deviceType = getDeviceType(device);
		String[] query = new String[6];
		query[0] = "DELETE WHERE {\n";
		query[1] = deviceURL + " foaf:deviceID ?ID.\n";
		query[2] = deviceURL + " foaf:deviceIP ?IP.\n";
		query[3] = deviceURL + " foaf:deviceHostName ?hostName.\n";
		query[4] = deviceURL + " a <" + this.URL + deviceType + ">.\n";
		query[5] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 6; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	public void deleteInterface(Interface intrface) {
		String interfaceURL = getInterfaceURL(intrface);
		String[] query = new String[4];
		query[0] = "DELETE WHERE {\n";
		query[1] = interfaceURL + " a foaf:Interface.\n";
		query[2] = interfaceURL + " foaf:interfaceName \""
				+ intrface.getInterfaceName() + "\".\n";
		query[3] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 4; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
		deleteInterfaceData(intrface);
		deleteInterfaceInterfaceDataRelation(intrface);
	}

	private void deleteInterfaceData(Interface intrface) {
		String interfaceDataURL = getInterfaceDataURL(intrface);
		String query[] = new String[6];
		query[0] = "DELETE WHERE {\n";
		query[1] = interfaceDataURL + "a foaf:InterfaceData.\n";
		query[2] = interfaceDataURL + " foaf:time ?tm.\n";
		query[3] = interfaceDataURL + " foaf:traffic_in ?ti.\n";
		query[4] = interfaceDataURL + " foaf:traffic_out ?to.\n";
		query[5] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 6; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	public void deleteAllZoneRelations(Zone zone) {
		String zoneURL = getZoneURL(zone);
		String[] query = new String[3];
		query[0] = "DELETE WHERE {\n";
		query[1] = zoneURL + " foaf:hasDevice ?device.\n";
		query[2] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 3; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	public void deleteZoneDeviceRelation(Device device) {
		String deviceURL = getDeviceURL(device);
		String[] query = new String[3];
		query[0] = "DELETE WHERE {\n";
		query[1] = "?zone foaf:hasDevice " + deviceURL + ".\n";
		query[2] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 3; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	public void deleteDeviceInterfaceRelation(Device device, Interface intrface) {
		String deviceURL = getDeviceURL(device);
		String interfaceURL = getInterfaceURL(intrface);
		String[] query = new String[3];
		query[0] = "DELETE WHERE {\n";
		query[1] = deviceURL + " foaf:hasInterface " + interfaceURL + ".\n";
		query[2] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 3; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	private void deleteInterfaceInterfaceDataRelation(Interface intrface) {
		String interfaceURL = getInterfaceURL(intrface);
		String interfaceDataURL = getInterfaceDataURL(intrface);
		String[] query = new String[3];
		query[0] = "DELETE WHERE {\n";
		query[1] = interfaceURL + " foaf:hasInterfaceData " + interfaceDataURL
				+ ".\n";
		query[2] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 3; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
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
		return new ParseResultSet().getZones(fireSelectQuery(selectQuery));
	}

	public List<Device> getZoneDevices(Zone zone) {
		String zoneURL = getZoneURL(zone);
		String[] query = new String[8];
		query[0] = "SELECT ?className ?deviceID ?deviceIP ?deviceHostName\n";
		query[1] = "WHERE {\n";
		query[2] = zoneURL + " foaf:hasDevice ?device.\n";
		query[3] = "?device a ?className.\n";
		query[4] = "?device foaf:deviceID ?deviceID.\n";
		query[5] = "?device foaf:deviceIP ?deviceIP.\n";
		query[6] = "?device foaf:deviceHostName ?deviceHostName.\n";
		query[7] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 8; ++i)
			selectQuery += query[i];
		ResultSet result = fireSelectQuery(selectQuery);
		List<Device> devices = new ParseResultSet().getDevices(result);
		for (int i = 0; i < devices.size(); ++i)
			devices.get(i).setInterfaces(getDeviceInterfaces(devices.get(i)));
		return devices;
	}

	public List<Device> getAllDevices() {
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
		ResultSet result = fireSelectQuery(selectQuery);
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
		ResultSet result = fireSelectQuery(selectQuery);
		List<Device> devices = new ParseResultSet().getDevices(result);
		for (int i = 0; i < devices.size(); ++i)
			devices.get(i).setInterfaces(getDeviceInterfaces(devices.get(i)));
		return devices;
	}

	public List<Interface> getDeviceInterfaces(Device device) {
		String deviceURL = getDeviceURL(device);
		String[] query = new String[9];
		query[0] = "SELECT ?interface ?interfaceName ?time ?trafficIn ?trafficOut\n";
		query[1] = "WHERE {\n";
		query[2] = deviceURL + " foaf:hasInterface ?interface.\n";
		query[3] = "?interface foaf:interfaceName ?interfaceName.\n";
		query[4] = "?interface foaf:hasInterfaceData ?interfaceData.\n";
		query[5] = "?interfaceData foaf:time ?time.\n";
		query[6] = "?interfaceData foaf:traffic_in ?trafficIn.\n";
		query[7] = "?interfaceData foaf:traffic_out ?trafficOut.\n";
		query[8] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 9; ++i)
			selectQuery += query[i];
		return new ParseResultSet().getInterfaces(fireSelectQuery(selectQuery));
	}

	private String removeSpaces(String old) {
		String ret = "";
		for (int i = 0; i < old.length(); ++i)
			if (old.charAt(i) == ' ')
				ret += "_";
			else
				ret += old.charAt(i);
		return ret;
	}

	private String getZoneURL(Zone zone) {
		return "<" + this.URL + removeSpaces(zone.getName()) + ">";
	}

	private String getDeviceURL(Device device) {
		return "<" + this.URL + device.getID() + ">";
	}

	private String getInterfaceURL(Interface intrface) {
		return "<" + this.URL + intrface.getInterfaceID() + ">";
	}

	private String getInterfaceDataURL(Interface intrface) {
		return "<" + this.URL + intrface.getInterfaceID() + "Data>";
	}

	private String getDeviceType(Device device) {
		if (device instanceof Router)
			return DeviceType.Router.toString();
		return DeviceType.Switch.toString();
	}

	private void fireInsertQuery(String insertQuery) {
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(insertQuery);
		rdfq.execute(QueryType.INSERT);
	}

	private void fireDeleteQuery(String deleteQuery) {
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(deleteQuery);
		rdfq.execute(QueryType.DELETE);
	}

	private ResultSet fireSelectQuery(String selectQuery) {
		RdfQuery rdfq = RdfQueryFactory.getQuery();
		rdfq.setPrefix(this.prefix);
		rdfq.setStmt(selectQuery);
		return rdfq.execute(QueryType.SELECT);
	}

	public boolean fireRule(Rule rule) {
		int size = rule.getDevicesName().size();
		for (int i = 0; i < size; ++i) {
			String[] query = new String[18];
			query[0] = "SELECT ?device WHERE {\n";
			query[1] = "?className rdfs:subClassOf <" + this.URL + "Device>.\n";
			query[2] = "?device a ?className.\n";
			query[3] = "?device foaf:deviceHostName ?deviceHostName.\n";
			query[4] = "?device foaf:hasInterface ?interface.\n";
			query[5] = "?interface foaf:interfaceName ?interfaceName.\n";
			query[6] = "?interface foaf:hasInterfaceData ?interfaceData.\n";
			query[7] = "?interfaceData foaf:traffic_in ?trafficIn.\n";
			query[8] = "?interfaceData foaf:traffic_out ?trafficOut.\n";
			query[9] = "FILTER(\n";
			query[10] = "?deviceHostName = \"" + rule.getDevicesName().get(i)
					+ "\"\n";
			query[11] = "&& ?interfaceName = \""
					+ rule.getInterfacesName().get(i) + "\"\n";
			query[12] = "&& ?trafficIn >= " + rule.getInMn().get(i) + "\n";
			query[13] = "&& ?trafficIn <= " + rule.getInMx().get(i) + "\n";
			query[14] = "&& ?trafficOut >= " + rule.getOutMn().get(i) + "\n";
			query[15] = "&& ?trafficOut <= " + rule.getOutMx().get(i) + "\n";
			query[16] = ").\n";
			query[17] = "}\n";
			String selectQuery = "";
			for (int j = 0; j < 18; ++j)
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
