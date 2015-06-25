package com.fork.core;

import java.util.List;

import com.fork.domain.Rule;
import com.fork.gui.RuleFire;
import com.fork.outputController.RDFLogic;

public class CoreRuntime {

	public static void testRules(List<Rule> rules) {
		RDFLogic rDFLogic = new RDFLogic();
		for (Rule rule : rules) {
			if (rDFLogic.fireRule(rule)) {
				new RuleFire(rule);
				RuleFire window = new RuleFire(rule);
				window.frame.setVisible(true);
			}
		}
	}
}
