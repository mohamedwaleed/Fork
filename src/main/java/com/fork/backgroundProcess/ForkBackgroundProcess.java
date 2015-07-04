package com.fork.backgroundProcess;

import java.util.Timer;

import javax.swing.JFrame;

public class ForkBackgroundProcess extends Thread {
	private Thread t;
	private String threadName;
	private final int INTERVAL = 120000;
	private JFrame frame;

	public ForkBackgroundProcess(String name, JFrame frame) {
		this.frame = frame;
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);

		Timer timer = new Timer();
		ForkRuntime forkRuntime = new ForkRuntime(frame);
		timer.schedule(forkRuntime, 0, INTERVAL);

		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
