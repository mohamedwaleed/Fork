package com.fork;

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
		window.frame.setVisible(true);

		initializeCoreThread();	
	}

	private static void initializeCoreThread() {
		ForkBackgroundProcess core = new ForkBackgroundProcess("Thread-1");
		core.start();
	}

}