package com.fork.ClientAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Device;

public class CactiDevicePoller implements IDevicePoller {
	private String userName, passWord;

	public CactiDevicePoller(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

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
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cacti", userName, passWord);
			Statement stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery("select * from host");
			while (resultSet.next()) {
				Device device = new Device();
				device.setID(String.valueOf(resultSet.getInt("id")));
				device.setIP(resultSet.getString("hostname"));
				device.setHostName(resultSet.getString("description"));
				devices.add(device);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return devices;
	}
}