package com.fork.persistance;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

	public static Connection getDatabaseConnection() {
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:ForkDatabase.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return con;
	}

	public static void buildDatabase() {
		File file = new File("ForkDatabase.db");
		if (!file.exists()) {
			Connection con = getDatabaseConnection();
			if (con != null) {
				Statement stmt = null;
				try {
					stmt = con.createStatement();
					String scriptTable = "CREATE TABLE Script "
							+ "(ID INT PRIMARY KEY     AUTOINCREMENT,"
							+ " NAME           TEXT    NOT NULL, "
							+ " SCRIPT         TEXT     NOT NULL)";
					stmt.executeUpdate(scriptTable);

					String ruleTable = "CREATE TABLE Rule "
							+ "(ID INT PRIMARY KEY     AUTOINCREMENT,"
							+ " NAME           TEXT    NOT NULL, "
							+ " RULE         TEXT     NOT NULL,"
							+ " STATE         INT     NOT NULL)";
					stmt.executeUpdate(ruleTable);

					stmt.close();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
