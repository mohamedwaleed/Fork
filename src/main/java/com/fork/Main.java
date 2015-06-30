package com.fork;

import javax.swing.JFrame;

import com.fork.backgroundProcess.ForkBackgroundProcess;
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.GUILogic;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseLogic.buildSQLite();
		JFrame frame = GUILogic.launchGUI();
		initializeCoreThread(frame);
	}

	private static void initializeCoreThread(JFrame frame) {
		ForkBackgroundProcess core = new ForkBackgroundProcess("Thread-1",
				frame);
		core.start();
	}

}