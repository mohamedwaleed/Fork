package com.fork.core;

import java.util.Timer;

public class Core extends Thread {
	private Thread t;
	private String threadName;
	private int interval;

	public Core(String name) {
		threadName = name;
		interval = 5000;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);

		// get devices

		Timer timer = new Timer();
		ForkRuntime forkRuntime = new ForkRuntime();
		timer.schedule(forkRuntime, 0, interval);

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
