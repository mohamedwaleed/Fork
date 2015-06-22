package com.fork.ClientAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Switch;

public class CactiDevicePoller implements IDevicePoller {

	/**
	 * Method for get devices data from Cacti sql database
	 * 
	 * @param NULL
	 *            
	 * @return List<Device> contains all the devices objects
	 */
	public List<Device> pollDevice() {
		List<Device> devices = new ArrayList<Device>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/cacti",
							"cacti", "12345");
			Statement stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from host");

			while (resultSet.next()) {
				//Device device = new Device();
				Device device = new Switch();
				device.setID(String.valueOf(resultSet.getInt("id")));
				device.setIP(resultSet.getString("hostname"));
				device.setHostName(resultSet.getString("description"));
				devices.add(device);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return devices;
	}
}