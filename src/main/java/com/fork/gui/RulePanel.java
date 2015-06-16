package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.fork.domain.Rule;
import com.fork.persistance.sqlite.DatabaseLogic;

public class RulePanel extends JPanel implements ListSelectionListener {
	private JTextField textField_1;
	@SuppressWarnings("rawtypes")
	private JList list;
	private JScrollPane jScrollPane1;
	private List<Rule> RulesNames;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	private JTextArea RuleTextArea;

	/**
	 * Create the panel.
	 */
	public RulePanel() {
		setLayout(null);

		final JPanel RuleArea = new JPanel();
		RuleArea.setBounds(165, 45, 462, 240);
		RuleArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		RuleArea.setBackground(SystemColor.menu);
		RuleArea.setForeground(Color.BLACK);
		add(RuleArea);
		RuleArea.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBounds(10, 11, 68, 14);
		RuleArea.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Rule");
		lblNewLabel_3.setBounds(10, 40, 68, 14);
		RuleArea.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setBackground(SystemColor.window);
		textField_1.setBounds(88, 8, 355, 20);
		RuleArea.add(textField_1);
		textField_1.setColumns(10);
		JScrollPane scS = new JScrollPane();
		scS.setSize(365, 189);
		scS.setLocation(88, 40);
		scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		RuleArea.add(scS);

		RuleTextArea = new JTextArea(20, 20);
		scS.setViewportView(RuleTextArea);

		JPanel liftList1 = new JPanel();
		liftList1.setBounds(10, 45, 145, 240);
		liftList1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList1.setLayout(new BorderLayout(0, 0));
		add(liftList1);

		RulesNames = DatabaseLogic.getRules();
		model = new DefaultListModel();
		for (int i = 0; i < RulesNames.size(); i++)
			model.addElement(((Rule) RulesNames.get(i)));
		list = new JList(model);
		list.addListSelectionListener(this);
		jScrollPane1 = new JScrollPane(list);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));
		liftList1.add(jScrollPane1, BorderLayout.CENTER);

		JPanel panel_s = new JPanel();
		panel_s.setBounds(10, 285, 618, 33);
		add(panel_s);

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rows[] = list.getSelectedIndices();
				Arrays.sort(rows);
				List<Integer> ids = new ArrayList<Integer>();
				for (int i = rows.length; i >= 0; i--) {
					Rule Rule = (Rule) model.getElementAt(rows[i]);
					model.removeElement(Rule);
					ids.add(Rule.getId());
				}
				DatabaseLogic.deleteRules(ids);
			}
		});
		panel_s.add(remove);

		JButton btnNewButton_2 = new JButton("Add Rule");
		btnNewButton_2.setBounds(0, 0, 627, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RuleAddition.start();
				RulesNames = DatabaseLogic.getRules();
				model = new DefaultListModel();
				for (int i = 0; i < RulesNames.size(); i++)
					model.addElement(((Rule) RulesNames.get(i)));
				list.setModel(model);
			}
		});
		add(btnNewButton_2);
	}

	static class RuleAddition extends JPanel {
		public static void start() {
			JPanel myPanel = new JPanel();
			myPanel.setBounds(100, 100, 100, 100);
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			JTextField RuleName = new JTextField();
			myPanel.add(new JLabel("Rule Name"));
			myPanel.add(RuleName);
			myPanel.add(new JLabel("Rule"));

			JScrollPane scS = new JScrollPane();
			scS.setSize(365, 144);
			scS.setLocation(151, 40);
			scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			JTextArea Rule = new JTextArea(20, 20);
			scS.setViewportView(Rule);
			myPanel.add(scS);
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Add Rule", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				DatabaseLogic.addRule(RuleName.getText().toString(), Rule
						.getText().toString());
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if (list.getSelectedIndex() != -1) {
			textField_1.setText(((Rule) model.getElementAt(list
					.getSelectedIndex())).getName());
			RuleTextArea.setText(((Rule) model.getElementAt(list
					.getSelectedIndex())).getRule());
		} else {
			textField_1.setText("");
			RuleTextArea.setText("");
		}
	}

}
