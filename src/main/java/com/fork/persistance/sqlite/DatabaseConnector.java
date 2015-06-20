package com.fork.persistance.sqlite;

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
							+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
							+ " NAME           TEXT    NOT NULL, "
							+ " SCRIPT         TEXT    NOT NULL)";
					stmt.executeUpdate(scriptTable);

					String ruleTable = "CREATE TABLE Rule "
							+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
							+ " NAME           TEXT    NOT NULL, "
							+ " RULE         TEXT      NOT NULL,"
							+ " STATE         INT      NOT NULL)";
					stmt.executeUpdate(ruleTable);

					String ruleScript = "CREATE TABLE Rule_Script "
							+ "(ID INTEGER PRIMARY KEY     AUTOINCREMENT,"
							+ " SCRIPT_ID           INT    NOT NULL, "
							+ " RULE_ID         INT      NOT NULL)";
					stmt.executeUpdate(ruleScript);

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
