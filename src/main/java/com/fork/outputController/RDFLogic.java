package com.fork.outputController;

import java.util.List;

import com.fork.domain.DataSource;
import com.fork.domain.Device;
import com.fork.domain.DeviceType;
import com.fork.domain.Interface;
import com.fork.domain.InterfaceData;
import com.fork.domain.Router;
import com.fork.domain.Rule;
import com.fork.domain.Zone;
import com.fork.persistance.rdf.ParseResultSet;
import com.fork.persistance.rdf.QueryType;
import com.fork.persistance.rdf.RdfQuery;
import com.fork.persistance.rdf.RdfQueryFactory;
import com.fork.persistance.rdf.RdfUtilty;
import com.hp.hpl.jena.query.ResultSet;

public class RDFLogic {
	private String URL;
	private String prefix;

	public RDFLogic() {
		this.URL = RdfUtilty.OWL_URL;
		this.prefix = "PREFIX foaf: <" + this.URL + ">\n";
	}

	public void addZone(Zone zone) {
		String zoneURL = getZoneURL(zone);
		String[] query = new String[5];
		query[0] = "INSERT DATA {\n";
		query[1] = zoneURL + " a foaf:Zone.\n";
		query[2] = zoneURL + " foaf:zoneID \"" + zone.getZoneID() + "\".\n";
		query[3] = zoneURL + " foaf:zoneName \"" + zone.getName() + "\".\n";
		query[4] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 5; ++i)
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
		String[] query = new String[5];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceURL + " a foaf:Interface.\n";
		query[2] = interfaceURL + " foaf:interfaceID \"" + intrface.getID()
				+ "\".\n";
		query[3] = interfaceURL + " foaf:interfaceName \"" + intrface.getName()
				+ "\".\n";
		query[4] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 5; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
		addInterfaceData(intrface.getData());
		addInterfaceInterfaceDataRelation(intrface, intrface.getData());
	}

	private void addInterfaceData(InterfaceData interfaceData) {
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
		String[] query = new String[3];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceDataURL + " a foaf:InterfaceData.\n";
		query[2] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 3; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
		List<DataSource> dataSources = interfaceData.getDataSources();
		for (DataSource dataSource : dataSources) {
			addDataSource(dataSource);
			addInterfaceDataSourceRelation(interfaceData, dataSource);
		}
	}

	private void addDataSource(DataSource dataSource) {
		String dataSourceURL = getDataSourceURL(dataSource);
		String[] query = new String[6];
		query[0] = "INSERT DATA {\n";
		query[1] = dataSourceURL + " a foaf:DataSource.\n";
		query[2] = dataSourceURL + " foaf:dataSourceID \"" + dataSource.getID()
				+ "\".\n";
		query[3] = dataSourceURL + " foaf:dataSourceName \""
				+ dataSource.getDataSourceName() + "\".\n";
		query[4] = dataSourceURL + " foaf:dataSourceValue "
				+ dataSource.getDataSourceValue() + ".\n";
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

	private void addInterfaceInterfaceDataRelation(Interface intrface,
			InterfaceData interfaceData) {
		String interfaceURL = getInterfaceURL(intrface);
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
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

	private void addInterfaceDataSourceRelation(InterfaceData interfaceData,
			DataSource dataSource) {
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
		String dataSourceURL = getDataSourceURL(dataSource);
		String[] query = new String[3];
		query[0] = "INSERT DATA {\n";
		query[1] = interfaceDataURL + " foaf:hasDataSource " + dataSourceURL
				+ ".\n";
		query[2] = "}\n";
		String insertQuery = "";
		for (int i = 0; i < 3; ++i)
			insertQuery += query[i];
		fireInsertQuery(insertQuery);
	}

	public void deleteZone(Zone zone) {
		String zoneURL = getZoneURL(zone);
		String[] query = new String[5];
		query[0] = "DELETE WHERE {\n";
		query[1] = zoneURL + " a foaf:Zone.\n";
		query[2] = zoneURL + " foaf:zoneID ?zoneID.\n";
		query[3] = zoneURL + " foaf:zoneName ?zoneName.\n";
		query[4] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 5; ++i)
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
		String[] query = new String[5];
		query[0] = "DELETE WHERE {\n";
		query[1] = interfaceURL + " a foaf:Interface.\n";
		query[2] = interfaceURL + " foaf:interfaceID \"" + intrface.getID()
				+ "\".\n";
		query[3] = interfaceURL + " foaf:interfaceName \"" + intrface.getName()
				+ "\".\n";
		query[4] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 5; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
		deleteInterfaceData(intrface.getData());
		deleteInterfaceInterfaceDataRelation(intrface, intrface.getData());
	}

	private void deleteInterfaceData(InterfaceData interfaceData) {
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
		String[] query = new String[3];
		query[0] = "DELETE WHERE {\n";
		query[1] = interfaceDataURL + "a foaf:InterfaceData.\n";
		query[2] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 3; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
		for (DataSource dataSource : interfaceData.getDataSources()) {
			deleteDataSource(dataSource);
			deleteInterfaceDataSourceRelation(interfaceData, dataSource);
		}
	}

	private void deleteDataSource(DataSource dataSource) {
		String dataSourceURL = getDataSourceURL(dataSource);
		String[] query = new String[6];
		query[0] = "DELETE WHERE {\n";
		query[1] = dataSourceURL + "a foaf:DataSource.\n";
		query[2] = dataSourceURL + " foaf:dataSourceID \"" + dataSource.getID()
				+ "\".\n";
		query[3] = dataSourceURL + " foaf:dataSourceName \""
				+ dataSource.getDataSourceName() + "\".\n";
		query[4] = dataSourceURL + " foaf:dataSourceValue "
				+ dataSource.getDataSourceValue() + ".\n";
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

	private void deleteInterfaceInterfaceDataRelation(Interface intrface,
			InterfaceData interfaceData) {
		String interfaceURL = getInterfaceURL(intrface);
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
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

	private void deleteInterfaceDataSourceRelation(InterfaceData interfaceData,
			DataSource dataSource) {
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
		String dataSourceURL = getDataSourceURL(dataSource);
		String[] query = new String[3];
		query[0] = "DELETE WHERE {\n";
		query[1] = interfaceDataURL + " foaf:hasInterfaceData " + dataSourceURL
				+ ".\n";
		query[2] = "}\n";
		String deleteQuery = "";
		for (int i = 0; i < 3; ++i)
			deleteQuery += query[i];
		fireDeleteQuery(deleteQuery);
	}

	public List<Zone> getZones() {
		String[] query = new String[6];
		query[0] = "SELECT ?zoneID ?zoneName\n";
		query[1] = "WHERE {\n";
		query[2] = "?zoneURL a <" + this.URL + "Zone>.\n";
		query[3] = "?zoneURL foaf:zoneID ?zoneID.\n";
		query[4] = "?zoneURL foaf:zoneName ?zoneName.\n";
		query[5] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 6; ++i)
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
		String[] query = new String[6];
		query[0] = "SELECT ?interfaceID ?interfaceName\n";
		query[1] = "WHERE {\n";
		query[2] = deviceURL + " foaf:hasInterface ?interface.\n";
		query[3] = "?interface foaf:interfaceID ?interfaceID.\n";
		query[4] = "?interface foaf:interfaceName ?interfaceName.\n";
		query[5] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 6; ++i)
			selectQuery += query[i];
		ResultSet result = fireSelectQuery(selectQuery);
		List<Interface> interfaces = new ParseResultSet().getInterfaces(result);
		for (Interface curInterface : interfaces)
			curInterface.setData(getInterfaceData(curInterface));
		return interfaces;
	}

	private InterfaceData getInterfaceData(Interface intrface) {
		String interfaceURL = getInterfaceURL(intrface);
		String[] query = new String[4];
		query[0] = "SELECT ?interfaceID\n";
		query[1] = "WHERE {\n";
		query[2] = interfaceURL + " foaf:interfaceID ?interfaceID.\n";
		query[3] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 4; ++i)
			selectQuery += query[i];
		ResultSet result = fireSelectQuery(selectQuery);
		InterfaceData interfaceData = new ParseResultSet()
				.getInterfaceData(result);
		interfaceData.setDataSources(getDataSources(interfaceData));
		return interfaceData;
	}

	private List<DataSource> getDataSources(InterfaceData interfaceData) {
		String interfaceDataURL = getInterfaceDataURL(interfaceData);
		String[] query = new String[7];
		query[0] = "SELECT ?dataSourceID ?dataSourceName ?dataSourceValue\n";
		query[1] = "WHERE {\n";
		query[2] = interfaceDataURL + " foaf:hasDataSource ?dataSource.\n";
		query[3] = "?dataSource foaf:dataSourceID ?dataSourceID.\n";
		query[4] = "?dataSource foaf:dataSourceName ?dataSourceName.\n";
		query[5] = "?dataSource foaf:dataSourceValue ?dataSourceValue.\n";
		query[6] = "}\n";
		String selectQuery = "";
		for (int i = 0; i < 7; ++i)
			selectQuery += query[i];
		System.out.println(selectQuery);
		ResultSet result = fireSelectQuery(selectQuery);
		return new ParseResultSet().getDataSources(result);
	}

	private String getZoneURL(Zone zone) {
		return "<" + this.URL + zone.getZoneID() + ">";
	}

	private String getDeviceURL(Device device) {
		return "<" + this.URL + device.getID() + ">";
	}

	private String getInterfaceURL(Interface intrface) {
		return "<" + this.URL + intrface.getID() + ">";
	}

	private String getInterfaceDataURL(InterfaceData interfaceData) {
		return "<" + this.URL + interfaceData.getID() + ">";
	}

	private String getDataSourceURL(DataSource dataSource) {
		return "<" + this.URL + dataSource.getID() + ">";
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
			String deviceName = rule.getDevicesName().get(i);
			String interfaceName = rule.getInterfacesName().get(i);
			String selectQuery = "";
			selectQuery += "SELECT ?device WHERE {\n";
			selectQuery += "?className rdfs:subClassOf <" + this.URL
					+ "Device>.\n";
			selectQuery += "?device a ?className.\n";
			selectQuery += "?device foaf:deviceHostName ?deviceHostName.\n";
			selectQuery += "?device foaf:hasInterface ?interface.\n";
			selectQuery += "?interface foaf:interfaceName ?interfaceName.\n";
			selectQuery += "?interface foaf:hasInterfaceData ?interfaceData.\n";
			selectQuery += "?interfaceData foaf:hasDataSource ?dataSource.\n";
			selectQuery += "?dataSource foaf:dataSourceName ?dataSourceName.\n";
			selectQuery += "?dataSource foaf:dataSourceValue ?dataSourceValue.\n";
			selectQuery += "FILTER (\n";
			selectQuery += "?deviceHostName = \"" + deviceName + "\"";
			selectQuery += " && ?interfaceName = \"" + interfaceName
					+ "\" && (\n";

			int dataSize = rule.getDataSourceName().get(i).size();
			for (int j = 0; j < dataSize; ++j) {
				if (j != 0)
					selectQuery += "|| ";
				selectQuery += "( ?dataSourceName = \""
						+ rule.getDataSourceName().get(i).get(j) + "\"";
				selectQuery += " && ?dataSourceValue >= "
						+ rule.getMinValue().get(i).get(j);
				selectQuery += " && ?dataSourceValue <= "
						+ rule.getMaxValue().get(i).get(j);
				selectQuery += ")\n";
			}
			selectQuery += ")\n).\n";
			selectQuery += "}\n";
			System.out.println(selectQuery);
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
