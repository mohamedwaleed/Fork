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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.fork.domain.Rule;
import com.fork.domain.Script;
import com.fork.persistance.sqlite.DatabaseLogic;

public class RulePanel extends JPanel implements ListSelectionListener {
	private JTextField textField_1;
	@SuppressWarnings("rawtypes")
	private JList list;
	private JScrollPane jScrollPane1;
	private List<Rule> RulesNames;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	private JCheckBox chckbxActivated;
	private JTextArea RuleTextArea;
	private RuleAddition ruleAddition;

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
		lblNewLabel_2.setBounds(10, 60, 68, 14);
		RuleArea.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Rule");
		lblNewLabel_3.setBounds(10, 99, 68, 14);
		RuleArea.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBackground(SystemColor.window);
		textField_1.setBounds(88, 57, 355, 20);
		RuleArea.add(textField_1);
		textField_1.setColumns(10);
		JScrollPane scS = new JScrollPane();
		scS.setSize(365, 130);
		scS.setLocation(88, 99);
		scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		RuleArea.add(scS);

		RuleTextArea = new JTextArea(20, 20);
		RuleTextArea.setEditable(false);
		scS.setViewportView(RuleTextArea);

		chckbxActivated = new JCheckBox("Activated");
		chckbxActivated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(chckbxActivated.isSelected());
				if (list.getSelectedIndex() != -1) {
					int ret;
					if (chckbxActivated.isSelected())
						ret = 1;
					else
						ret = 0;
					DatabaseLogic.changeRuleState(((Rule) model
							.getElementAt(list.getSelectedIndex())).getId(),
							ret);
					((Rule) model.elementAt(list.getSelectedIndex()))
							.setState(ret);
				}
			}
		});
		chckbxActivated.setBounds(10, 18, 97, 23);
		RuleArea.add(chckbxActivated);

		JPanel liftList1 = new JPanel();
		liftList1.setBounds(10, 70, 145, 215);
		liftList1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList1.setLayout(new BorderLayout(0, 0));
		add(liftList1);

		RulesNames = DatabaseLogic.getRules();

		Rule s = new Rule();
		s.setId(1);
		s.setName("islam");
		s.setRule("rule1");
		s.setState(1);
		RulesNames.add(s);
		s = new Rule();
		s.setId(2);
		s.setName("ahm");
		s.setRule("rule2");
		s.setState(0);
		RulesNames.add(s);

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
				if (list.getSelectedIndex() != -1) {
					int rows[] = list.getSelectedIndices();
					Arrays.sort(rows);
					List<Integer> ids = new ArrayList<Integer>();
					for (int i = rows.length - 1; i >= 0; i--) {
						Rule Rule = (Rule) model.getElementAt(rows[i]);
						model.removeElement(Rule);
						ids.add(Rule.getId());
					}
					DatabaseLogic.deleteRules(ids);
				}
			}
		});
		panel_s.add(remove);

		JButton btnNewButton = new JButton("Edit Scripts");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (list.getSelectedIndex() != -1) {
					Rule ruleToUpdate = (Rule) model.getElementAt(list
							.getSelectedIndex());
					EditRuleJPanel djp = new EditRuleJPanel(ruleToUpdate);
					UIManager.put("OptionPane.minimumSize", new Dimension(500,
							350));
					int result = JOptionPane.showConfirmDialog(RulePanel.this,
							djp, "Edit existed scripts",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {

						DatabaseLogic.deleteRuleScripts(ruleToUpdate.getId());
						DefaultListModel scriptsModel = djp.getNewModel();

						List<Script> pickedScripts = new ArrayList<Script>();
						for (int i = 0; i < scriptsModel.size(); i++)
							pickedScripts.add((Script) (scriptsModel
									.getElementAt(i)));

						DatabaseLogic.addRuleScripts(pickedScripts,
								ruleToUpdate.getId());
					}
				}

			}
		});
		panel_s.add(btnNewButton);

		JButton btnNewButton_2 = new JButton("Add Rule");
		btnNewButton_2.setBounds(0, 0, 627, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ruleAddition = new RuleAddition(RulePanel.this);
				ruleAddition.initialize();

			}
		});
		add(btnNewButton_2);

		JLabel lblListOfRules = new JLabel("List of rules");
		lblListOfRules.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfRules.setBounds(10, 45, 145, 14);
		add(lblListOfRules);
	}

	public void updateList() {
		System.out.println("Here update List");
		RulesNames = DatabaseLogic.getRules();
		model = new DefaultListModel();
		for (int i = 0; i < RulesNames.size(); i++)
			model.addElement(((Rule) RulesNames.get(i)));
		list.setModel(model);
	}

	public void valueChanged(ListSelectionEvent arg0) {
		if (list.getSelectedIndex() != -1) {
			textField_1.setText(((Rule) model.getElementAt(list
					.getSelectedIndex())).getName());
			RuleTextArea.setText(((Rule) model.getElementAt(list
					.getSelectedIndex())).getRule());
			int ret = ((Rule) model.getElementAt(list.getSelectedIndex()))
					.getState();
			boolean ac = false;
			if (ret == 1)
				ac = true;
			chckbxActivated.setSelected(ac);
		} else {
			textField_1.setText("");
			RuleTextArea.setText("");
		}
	}
}