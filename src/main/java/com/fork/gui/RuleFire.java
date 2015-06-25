package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.fork.domain.Rule;
import com.fork.domain.Script;
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.ScriptRunner;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class RuleFire {
	public JFrame frame;
	private JList<Script> list2;
	private DefaultListModel<String> model;
	private DefaultListModel<Script> model2;
	private List<Script> ruleScripts;

	public RuleFire(Rule rule) {
		frame = new JFrame();
		frame.setTitle("Rule Fired");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 452, 263);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 11, 430, 46);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel(rule.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 5, 410, 22);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		model = new DefaultListModel<String>();
		for (int i = 0; i < rule.getDevicesName().size(); i++)
			model.addElement((rule.getDevicesName().get(i)));

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 69, 430, 126);
		panel_3.setLayout(new BorderLayout(0, 0));
		ruleScripts = DatabaseLogic.getRuleScripts(rule.getID());
		model2 = new DefaultListModel<Script>();
		for (int i = 0; i < ruleScripts.size(); i++)
			model2.addElement((ruleScripts.get(i)));
		list2 = new JList<Script>(model2);
		JScrollPane jScrollPane2 = new JScrollPane(list2);
		jScrollPane2.setMaximumSize(new Dimension(100, 200));
		panel_3.add(jScrollPane2);
		frame.getContentPane().add(panel_3);

		JButton btnRunScripts = new JButton("Run script(s)");
		btnRunScripts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list2.getSelectedIndex() != -1) {
					int[] scripts = list2.getSelectedIndices();
					for (int j : scripts) {
						ScriptRunner.runScript((Script) model2.getElementAt(j));
					}
				}
			}
		});
		btnRunScripts.setBounds(318, 200, 122, 23);
		frame.getContentPane().add(btnRunScripts);
	}
}
