package com.fork.core;

import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Rule;
import com.fork.gui.RuleFire;
import com.fork.outputController.RDFLogic;

public class CoreRuntime {

	public static List<Rule> testRules(List<Rule> rules) {
		List<Rule>firedRules = new ArrayList<Rule>();
		RDFLogic rDFLogic = new RDFLogic();
		for (Rule rule : rules) {
			if (rDFLogic.fireRule(rule)) {
				RuleFire window = new RuleFire(rule);
				window.frame.setVisible(true);
				firedRules.add(rule);
			}
		}
		return firedRules;
	}
}
