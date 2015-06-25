package com.fork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.fork.backgroundProcess.ForkBackgroundProcess;
import com.fork.gui.MainWindow;
import com.fork.persistance.sqlite.DatabaseConnector;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseConnector.buildDatabase();

		MainWindow window = new MainWindow();

		if (!tryMysqlConnection(MainWindow.username, MainWindow.password))
			if (!window.showMySqlAuth())
				System.exit(0);
		window.frame.setVisible(true);

		initializeCoreThread(window.frame);
	}

	private static boolean tryMysqlConnection(String userName, String passWord) {
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

	private static void initializeCoreThread(JFrame frame) {
		ForkBackgroundProcess core = new ForkBackgroundProcess("Thread-1", frame);
		core.start();
	}

}