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
import javax.swing.ImageIcon;
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
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.RuleFire;

@SuppressWarnings("serial")
public class RulePanel extends JPanel implements ListSelectionListener {
	private JTextField textField_1;
	private JList<Rule> list;
	private JScrollPane jScrollPane1;
	private List<Rule> RulesNames;
	private DefaultListModel<Rule> model;
	private JCheckBox chckbxActivated;
	private JTextArea RuleTextArea;
	private RuleAddition ruleAddition;

	/**
	 * Create the panel.
	 */
	public RulePanel() {
		setLayout(null);

		final JPanel RuleArea = new JPanel();
		RuleArea.setBounds(165, 11, 462, 227);
		RuleArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		RuleArea.setBackground(SystemColor.menu);
		RuleArea.setForeground(Color.BLACK);
		add(RuleArea);
		RuleArea.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBounds(10, 40, 68, 14);
		RuleArea.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Rule");
		lblNewLabel_3.setBounds(10, 79, 68, 14);
		RuleArea.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBackground(SystemColor.window);
		textField_1.setBounds(88, 37, 355, 31);
		RuleArea.add(textField_1);
		textField_1.setColumns(10);
		JScrollPane scS = new JScrollPane();
		scS.setSize(365, 130);
		scS.setLocation(88, 79);
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
							.getElementAt(list.getSelectedIndex())).getID(),
							ret);
					((Rule) model.elementAt(list.getSelectedIndex()))
							.setState(ret);
				}
			}
		});
		chckbxActivated.setBounds(10, 7, 97, 23);
		RuleArea.add(chckbxActivated);

		JPanel liftList1 = new JPanel();
		liftList1.setBounds(10, 36, 145, 203);
		liftList1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList1.setLayout(new BorderLayout(0, 0));
		add(liftList1);

		RulesNames = DatabaseLogic.getRules();
		model = new DefaultListModel<Rule>();
		for (int i = 0; i < RulesNames.size(); i++)
			model.addElement(((Rule) RulesNames.get(i)));
		list = new JList<Rule>(model);
		list.addListSelectionListener(this);
		jScrollPane1 = new JScrollPane(list);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));
		liftList1.add(jScrollPane1, BorderLayout.CENTER);

		JPanel panel_s = new JPanel();
		panel_s.setBounds(10, 249, 618, 65);
		add(panel_s);

		//JButton remove = new JButton("Remove");
		JButton remove = new RoundButton(new ImageIcon("remove.png"),
				"removec.png", "remove.png");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					int rows[] = list.getSelectedIndices();
					Arrays.sort(rows);
					List<Integer> ids = new ArrayList<Integer>();
					for (int i = rows.length - 1; i >= 0; i--) {
						Rule Rule = (Rule) model.getElementAt(rows[i]);
						model.removeElement(Rule);
						ids.add(Rule.getID());
					}
					DatabaseLogic.deleteRules(ids);
				}
			}
		});

		// JButton btnNewButton_2 = new JButton("Add Rule");
		JButton btnNewButton_2 = new RoundButton(new ImageIcon("add.png"),
				"addc.png", "add.png");
		panel_s.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ruleAddition = new RuleAddition(RulePanel.this);
				ruleAddition.initialize();

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

						DatabaseLogic.deleteRuleScripts(ruleToUpdate.getID());
						DefaultListModel<Script> scriptsModel = djp
								.getNewModel();

						List<Script> pickedScripts = new ArrayList<Script>();
						for (int i = 0; i < scriptsModel.size(); i++)
							pickedScripts.add((Script) (scriptsModel
									.getElementAt(i)));

						DatabaseLogic.addRuleScripts(pickedScripts,
								ruleToUpdate.getID());
					}
				}

			}
		});
		panel_s.add(btnNewButton);

		JLabel lblListOfRules = new JLabel("List of rules");
		lblListOfRules.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfRules.setBounds(10, 11, 145, 14);
		add(lblListOfRules);
	}

	public void updateList() {
		System.out.println("Here update List");
		RulesNames = DatabaseLogic.getRules();
		model = new DefaultListModel<Rule>();
		for (int i = 0; i < RulesNames.size(); i++)
			model.addElement(((Rule) RulesNames.get(i)));
		list.setModel(model);
	}

	public void valueChanged(ListSelectionEvent arg0) {

		if (list.getSelectedIndex() != -1) {
			textField_1.setText(((Rule) model.getElementAt(list
					.getSelectedIndex())).getName());
			RuleTextArea.setText(RuleFire.writeRuleToTextArea((Rule) model
					.getElementAt(list.getSelectedIndex())));
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
