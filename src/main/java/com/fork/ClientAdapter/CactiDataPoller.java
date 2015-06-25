package com.fork.ClientAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fork.domain.DataSource;
import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.InterfaceData;

public class CactiDataPoller implements IDataPoller {
	private String userName, passWord;

	public CactiDataPoller(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	/**
	 * Method for get data from rrd files in a given device
	 * 
	 * @param device
	 *            Device that you will poll data for it
	 * @return List<Interfaces> interfaces of each device
	 */
	public List<Interface> pollData(Device device) {
		// TODO Auto-generated method stub
		int ID = 0;
		List<Interface> interfaces = new ArrayList<Interface>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cacti", userName, passWord);
			Statement stmt = con.createStatement();
			String selectQuery = "select distinct rrd_path, field_value, data_local.id\n"
					+ "from data_local, poller_item, host_snmp_cache\n"
					+ "where  data_local.id = poller_item.local_data_id\n"
					+ "and  data_local.snmp_index = host_snmp_cache.snmp_index\n"
					+ "and data_local.host_id = "
					+ device.getID()
					+ "\nand (field_name = 'ifAlias' or field_name = 'ifDescr')\n"
					+ "ORDER BY data_local.id, field_name";
			ResultSet resultSet = stmt.executeQuery(selectQuery);
			CactiParser cactiParser = new CactiParser();
			while (resultSet.next()) {
				String rrdPath = resultSet.getString("rrd_path");
				// One interface has two names (ifAlias, ifDescr)
				String fieldValue1 = resultSet.getString("field_value");
				resultSet.next();
				String fieldValue2 = resultSet.getString("field_value");
				int local_data_id = resultSet.getInt("id");

				String selectDataSourceQuery = "select data_source_name from data_template_rrd where local_data_id = "
						+ local_data_id + "";
				Statement stmt2 = con.createStatement();
				ResultSet resultSet2 = stmt2
						.executeQuery(selectDataSourceQuery);
				List<String> dataSources = new ArrayList<String>();
				while (resultSet2.next())
					dataSources.add(resultSet2.getString("data_source_name"));
				resultSet2.close();
				stmt2.close();

				String interfaceName = fieldValue2 + fieldValue1;
				Interface intrface = new Interface();
				intrface.setID(device.getID() + "_" + Integer.toString(ID));
				intrface.setName(interfaceName);

				InterfaceData interfaceData = cactiParser.parse(rrdPath, dataSources);
				interfaceData.setID(intrface.getID() + "_Data");
				for (DataSource dataSource : interfaceData.getDataSources()) {
					String oldID = dataSource.getID();
					dataSource.setID(intrface.getID() + "_Data_" + oldID);
				}

				intrface.setData(interfaceData);
				interfaces.add(intrface);
				++ID;
			}
			resultSet.close();
			stmt.close();
			con.close();
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