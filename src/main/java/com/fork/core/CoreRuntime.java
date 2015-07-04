package com.fork.core;

import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Rule;
import com.fork.outputController.RDFLogic;
import com.fork.outputController.RuleFire;

public class CoreRuntime {

	public static List<Rule> testRules(List<Rule> rules) {
		List<Rule>firedRules = new ArrayList<Rule>();
		RDFLogic rDFLogic = new RDFLogic();
		for (Rule rule : rules) {
			if (rDFLogic.fireRule(rule)) {

				firedRules.add(rule);
			}
		}
		return firedRules;
	}
}
