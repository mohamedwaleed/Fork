package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.fork.domain.Rule;
import com.fork.domain.Script;
import com.fork.persistance.sqlite.DatabaseLogic;

public class EditRuleJPanel extends JPanel {

	private JList list;
	private JList list2;
	private List<Script> scriptsNames;
	private List<Script> ruleScriptsNames;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	private DefaultListModel model2;

	/**
	 * Create the panel.
	 */
	public EditRuleJPanel(Rule rule) {
		setLayout(null);

		JLabel lblScripts = new JLabel("All System Scripts");
		lblScripts.setBounds(10, 11, 146, 20);
		add(lblScripts);

		scriptsNames = DatabaseLogic.getScripts();
		model = new DefaultListModel();
		for (int i = 0; i < scriptsNames.size(); i++)
			model.addElement(((Script) scriptsNames.get(i)));

		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 146, 247);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		list = new JList(model);
		JScrollPane jScrollPane1 = new JScrollPane(list);
		panel.add(jScrollPane1);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));

		JLabel lblRuleScripts = new JLabel("Rule Scripts");
		lblRuleScripts.setBounds(245, 11, 69, 20);
		add(lblRuleScripts);

		ruleScriptsNames = DatabaseLogic.getRuleScripts(rule.getId());
		model2 = new DefaultListModel();
		for (int i = 0; i < ruleScriptsNames.size(); i++)
			model2.addElement(((Script) ruleScriptsNames.get(i)));

		JPanel panel1 = new JPanel();
		panel1.setBounds(245, 40, 161, 249);
		add(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		list2 = new JList(model2);
		JScrollPane jScrollPane12 = new JScrollPane(list2);
		panel1.add(jScrollPane12);
		jScrollPane12.setMaximumSize(new Dimension(100, 200));

		JButton button = new JButton(">>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() != -1) {
					int rows[] = list.getSelectedIndices();
					Arrays.sort(rows);
					for (int i = rows.length - 1; i >= 0; i--) {
						Script script = (Script) model.getElementAt(rows[i]);
						if (!model2.contains(script))
							model2.addElement((Script) script);
					}
				}
			}
		});
		button.setBounds(166, 138, 69, 23);
		add(button);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list2.getSelectedIndex() != -1) {
					int rows[] = list2.getSelectedIndices();
					Arrays.sort(rows);
					for (int i = rows.length - 1; i >= 0; i--) {
						Script script = (Script) model2.getElementAt(rows[i]);
						model2.removeElement(script);
					}
				}
			}
		});
		btnRemove.setBounds(324, 10, 82, 23);
		add(btnRemove);

	}

	public JList getChoosenScripts() {
		return list2;
	}

	public DefaultListModel getNewModel() {
		return model2;
	}
}