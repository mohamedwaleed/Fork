package com.fork.backgroundProcess;

import java.util.List;
import java.util.TimerTask;

import com.fork.core.CoreRuntime;
import com.fork.domain.Rule;
import com.fork.gui.MainWindow;
import com.fork.gui.ZonePanel;
import com.fork.outputController.DatabaseLogic;

public class ForkRuntime extends TimerTask {

	public ForkRuntime() {
	}

	public void run() {

		// create an abject of ForkLifecycle
		// call functions stack
		
		IForkLifecycle appLifecycle = new ForkLifecycle();
		if (appLifecycle.updateDevicesData() != null) {
			ZonePanel.updateList();

			List<Rule> rules = DatabaseLogic.getActiveRules();
			CoreRuntime.testRules(rules);
		} else {
			MainWindow.showWrongMysqlAuth();
		}

	}
}
