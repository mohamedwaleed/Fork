package com.fork.backgroundProcess;

import java.util.Timer;

public class ForkBackgroundProcess extends Thread {
	private Thread t;
	private String threadName;
	private final int INTERVAL = 300000;

	public ForkBackgroundProcess(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);

		Timer timer = new Timer();
		ForkRuntime forkRuntime = new ForkRuntime();
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
