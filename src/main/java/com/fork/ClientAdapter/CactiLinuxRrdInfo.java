package com.fork.ClientAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.InterfaceData;

public class CactiLinuxRrdInfo implements ICactiRrd {

	public List<Interface> getRrdsInfo(Device device) throws Exception {
		// TODO Auto-generated method stub
		int ID = 0;
		List<Interface> interfaces = new ArrayList<Interface>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cacti", "cacti", "12345");
			Statement stmt = con.createStatement();
			String selectQuery = "select distinct rrd_path, field_value\n"
					+ "from data_local, poller_item, host_snmp_cache\n"
					+ "where  data_local.id = poller_item.local_data_id\n"
					+ "and  data_local.snmp_index = host_snmp_cache.snmp_index\n"
					+ "and data_local.host_id = "
					+ device.getID()
					+ "\n"
					+ "and (field_name = 'ifAlias' or field_name = 'ifDescr')\n"
					+ "ORDER BY data_local.id, field_name";
			ResultSet resultSet = stmt.executeQuery(selectQuery);
			CactiParser cactiParser = new CactiParser();
			while (resultSet.next()) {
				String rrdPath = resultSet.getString("rrd_path");
				// One interface has two names (ifAlias, ifDescr)
				String fieldValue1 = resultSet.getString("field_value");
				resultSet.next();
				String fieldValue2 = resultSet.getString("field_value");

				String interfaceName = fieldValue2 + fieldValue1;
				InterfaceData interfaceData = cactiParser.parse(rrdPath);
				Interface intrface = new Interface();
				intrface.setInterfaceID(device.getID() + "_" + Integer.toString(ID));
				intrface.setInterfaceName(interfaceName);
				intrface.setInterfaceData(interfaceData);
				interfaces.add(intrface);
				++ID;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return interfaces;
	}
}
