package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.fork.domain.Rule;
import com.fork.domain.Script;
import com.fork.outputController.DatabaseLogic;

@SuppressWarnings("serial")
public class DialogJPanel extends JPanel {

	private JTextField textField;
	private JList<Script> list;
	private List<Script> scriptsNames;
	private DefaultListModel<Script> model;

	/**
	 * Create the panel.
	 */
	public DialogJPanel(String name, String query) {
		Rule rule = new Rule();
		rule.setRule(query);
		setLayout(null);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 24, 61, 27);
		add(lblName);

		JLabel lblRule = new JLabel("Rule");
		lblRule.setBounds(10, 68, 61, 20);
		add(lblRule);

		JLabel lblScripts = new JLabel("Scripts");
		lblScripts.setBounds(10, 164, 61, 27);
		add(lblScripts);

		textField = new JTextField();
		textField.setBounds(83, 24, 280, 27);
		textField.setText(name);
		add(textField);
		textField.setColumns(10);

		JScrollPane scS = new JScrollPane();
		scS.setSize(282, 87);
		scS.setLocation(81, 73);
		scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scS);
		JTextArea scriptTextArea = new JTextArea(20, 20);
		scriptTextArea.setEditable(false);
		scriptTextArea.setText(RuleFire.writeRuleToTextArea(rule));
		scS.setViewportView(scriptTextArea);

		scriptsNames = DatabaseLogic.getScripts();
		model = new DefaultListModel<Script>();
		for (int i = 0; i < scriptsNames.size(); i++)
			model.addElement(((Script) scriptsNames.get(i)));

		JPanel panel = new JPanel();
		panel.setBounds(81, 171, 147, 141);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		list = new JList<Script>(model);
		JScrollPane jScrollPane1 = new JScrollPane(list);
		panel.add(jScrollPane1);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));

	}

	public JList<Script> getChoosenScripts() {
		return list;
	}

	public String getNameEdited() {
		return textField.getText().toString();
	}

	public DefaultListModel<Script> getModel() {
		return model;
	}
}
