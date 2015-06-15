package com.fork.core;

import java.util.Timer;
import java.util.TimerTask;

class UpdateJena extends TimerTask {
	
	public UpdateJena() {}
	public void run() {
		
		
		
		System.out.println("islcore");
		
		// check rules
		// 1- get all rules from ForkDatabase
		// 2- check for every rule 
		// 3- create window for all fired rules
		
		
	}
}


public class Core extends Thread {
	private Thread t;
	private String threadName;

	public Core(String name) {
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	public void run() {
		System.out.println("Running " + threadName);
		
		
		Timer timer = new Timer();
		UpdateJena update = new UpdateJena();
		timer.schedule(update, 0, 5000);
		
		
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
