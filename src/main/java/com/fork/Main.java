package com.fork;

import com.fork.core.Core;
import com.fork.gui.MainWindow;
import com.fork.persistance.sqlite.DatabaseConnector;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.frame.setVisible(true);

		DatabaseConnector.buildDatabase();
		initializeCoreThread();
	}

	private static void initializeCoreThread() {
		Core core = new Core("Thread-1");
		core.start();
	}

}