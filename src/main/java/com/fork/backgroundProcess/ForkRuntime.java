package com.fork.backgroundProcess;

import java.util.TimerTask;

public class ForkRuntime extends TimerTask {

	public ForkRuntime() {
	}

	public void run() {

		// create an abject of ForkLifecycle
		// call functions stack
		
		IForkLifecycle appLifecycle  = new ForkLifecycle();
	
	}
}
