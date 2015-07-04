package com.fork.outputController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.fork.gui.MainWindow;
import com.fork.gui.ZonePanel;

public class GUILogic {
	public static JFrame launchGUI() {
		MainWindow window = new MainWindow();
		if (!tryMysqlConnection("cacti", "12345")) {
			boolean flag = false;
			while (!flag) {
				int ret = window.showMySqlAuth();
				if (ret == 0)
					flag = true;
				else if (ret == 2) {
					System.exit(0);
				}
			}
		}
		window.frame.setVisible(true);
		return window.frame;
	}

	public static boolean tryMysqlConnection(String userName, String passWord) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/cacti", userName, passWord);
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String getUsername() {
		return MainWindow.username;
	}

	public static String getPassowrd() {
		return MainWindow.password;
	}

	public static void updateZonePanelList() {
		ZonePanel.updateList();
	}

	public static void showWrongAuth() {
		MainWindow.showWrongAuth();
	}
}
