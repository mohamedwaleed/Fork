package com.fork.backgroundProcess;

import java.util.List;
import java.util.TimerTask;

import com.fork.core.CoreRuntime;
import com.fork.domain.Rule;
import com.fork.gui.ZonePanel;
import com.fork.persistance.sqlite.DatabaseLogic;

public class ForkRuntime extends TimerTask {

	public ForkRuntime() {
	}

	public void run() {

		// create an abject of ForkLifecycle
		// call functions stack
		
		IForkLifecycle appLifecycle  = new ForkLifecycle();
		appLifecycle.updateDevicesData();
		
		ZonePanel.updateList();
		
		List<Rule> rules = DatabaseLogic.getRules();
		CoreRuntime.testRules(rules);
		
	}
}
