package com.fork.gui;

import javax.swing.JOptionPane;

import com.fork.domain.Rule;

public class RuleFire {

	public RuleFire(Rule rule) {
		JOptionPane.showMessageDialog(null, rule.getRule());
	}
}
