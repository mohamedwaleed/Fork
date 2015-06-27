package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.fork.domain.Rule;
import com.fork.domain.Script;
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.ScriptRunner;

public class RuleFire {
	public JFrame frame;
	private JList<Script> list2;
	private DefaultListModel<Script> model2;
	private List<Script> ruleScripts;
	private JTextArea scriptTextArea;
	private Rule rule;

	public RuleFire(Rule rul) {
		this.rule = rul;
		frame = new JFrame();
		frame.setTitle("Rule Fired");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 453, 302);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 11, 430, 33);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel(rule.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 5, 410, 22);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		final JPanel scriptArea = new JPanel();
		scriptArea.setBounds(10, 55, 255, 207);
		scriptArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scriptArea.setBackground(SystemColor.menu);
		scriptArea.setForeground(Color.BLACK);
		frame.getContentPane().add(scriptArea);
		scriptArea.setLayout(new BorderLayout(0, 0));
		JScrollPane scS = new JScrollPane();
		scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scriptArea.add(scS);
		scriptTextArea = new JTextArea(20, 20);
		scS.setViewportView(scriptTextArea);
		scriptTextArea.setText(writeRuleToTextArea(rule));

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(275, 55, 165, 135);
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

		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(275, 201, 165, 61);
		frame.getContentPane().add(panel_1);
		//JButton btnRunScripts = new JButton("Run script(s)");
		JButton btnRunScripts = new RoundButton(new ImageIcon("run.png"),
				"runc.png", "run.png");
		btnRunScripts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list2.getSelectedIndex() != -1) {
					int[] scripts = list2.getSelectedIndices();
					for (int j : scripts)
						ScriptRunner.runScript((Script) model2.getElementAt(j));
				}
			}
		});
		panel_1.add(btnRunScripts);
		
		
	}

	public static String writeRuleToTextArea(Rule rule) {
		String text = "";
		for (int i = 0; i < rule.getDevicesName().size(); i++) {
			text += rule.getDevicesName().get(i);
			text += "\n";
			text += (rule.getInterfacesName().get(i));
			text += "\n";
			for (int j = 0; j < rule.getDataSourceName().get(i).size(); j++) {
				text += (rule.getDataSourceName().get(i).get(j));
				text += "\t";
				text += ("Min: " + rule.getMinValue().get(i).get(j) + ", Max: " + rule
						.getMaxValue().get(i).get(j));
				text += "\n";
			}
			if (i != rule.getDevicesName().size() - 1)
				text += "\n&\n";
		}
		return text;
	}
}
