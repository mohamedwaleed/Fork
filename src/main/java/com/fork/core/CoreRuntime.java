package com.fork.core;

import java.util.List;

import com.fork.domain.Rule;
import com.fork.gui.RuleFire;
import com.fork.persistance.rdf.JenaRetrieval;

public class CoreRuntime {

	public static void testRules(List<Rule> rules) {
		JenaRetrieval jenaRetrieval = new JenaRetrieval();
		for (Rule rule : rules) {
			if (jenaRetrieval.fireRule(rule))
				new RuleFire(rule);
		}
	}

}
