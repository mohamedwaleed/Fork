package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.fork.domain.Script;
import com.fork.persistance.sqlite.DatabaseLogic;

@SuppressWarnings("serial")
public class ScriptPanel extends JPanel implements ListSelectionListener {
	private JTextField textField_1;
	@SuppressWarnings("rawtypes")
	private JList list;
	private JScrollPane jScrollPane1;
	private List<Script> scriptsNames;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	private JTextArea scriptTextArea;

	/**
	 * Create the panel.
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ScriptPanel() {
		setLayout(null);

		final JPanel scriptArea = new JPanel();
		scriptArea.setBounds(165, 45, 462, 240);
		scriptArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scriptArea.setBackground(SystemColor.menu);
		scriptArea.setForeground(Color.BLACK);
		add(scriptArea);
		scriptArea.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBounds(10, 11, 68, 14);
		scriptArea.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Script");
		lblNewLabel_3.setBounds(10, 40, 68, 14);
		scriptArea.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setBackground(SystemColor.window);
		textField_1.setBounds(88, 8, 355, 20);
		scriptArea.add(textField_1);
		textField_1.setColumns(10);
		JScrollPane scS = new JScrollPane();
		scS.setSize(365, 189);
		scS.setLocation(88, 40);
		scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scriptArea.add(scS);

		scriptTextArea = new JTextArea(20, 20);
		scS.setViewportView(scriptTextArea);

		JPanel liftList1 = new JPanel();
		liftList1.setBounds(10, 45, 145, 240);
		liftList1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList1.setLayout(new BorderLayout(0, 0));
		add(liftList1);

		scriptsNames = DatabaseLogic.getScripts();
		model = new DefaultListModel();
		for (int i = 0; i < scriptsNames.size(); i++)
			model.addElement(((Script) scriptsNames.get(i)));
		list = new JList(model);
		list.addListSelectionListener(this);
		jScrollPane1 = new JScrollPane(list);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));
		liftList1.add(jScrollPane1, BorderLayout.CENTER);

		JPanel panel_s = new JPanel();
		panel_s.setBounds(10, 285, 618, 33);
		add(panel_s);

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = list.getSelectedIndex();
				DatabaseLogic.updateScript(((Script) model.getElementAt(row))
						.getId(), textField_1.getText().toString(),
						scriptTextArea.getText().toString());
				Script newS = new Script();
				newS.setId(((Script) model.getElementAt(row)).getId());
				newS.setName(textField_1.getText().toString());
				newS.setScript(scriptTextArea.getText().toString());
				model.setElementAt(newS, row);
			}
		});
		panel_s.add(update);
		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int rows[] = list.getSelectedIndices();
				Arrays.sort(rows);
				List<Integer> ids = new ArrayList<Integer>();
				for (int i = rows.length - 1; i >= 0; i--) {
					Script script = (Script) model.getElementAt(rows[i]);
					model.removeElement(script);
					ids.add(script.getId());
				}
				DatabaseLogic.deleteScripts(ids);
			}
		});
		panel_s.add(remove);

		JButton btnNewButton_2 = new JButton("Add Script");
		btnNewButton_2.setBounds(0, 0, 627, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScriptAddition.start();
				scriptsNames = DatabaseLogic.getScripts();
				model = new DefaultListModel();
				for (int i = 0; i < scriptsNames.size(); i++)
					model.addElement(((Script) scriptsNames.get(i)));
				list.setModel(model);
			}
		});
		add(btnNewButton_2);
	}

	static class ScriptAddition extends JPanel {
		public static void start() {
			JPanel myPanel = new JPanel();
			myPanel.setBounds(100, 100, 100, 100);
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			JTextField scriptName = new JTextField();
			myPanel.add(new JLabel("Script Name"));
			myPanel.add(scriptName);
			myPanel.add(new JLabel("Script"));

			JScrollPane scS = new JScrollPane();
			scS.setSize(365, 144);
			scS.setLocation(151, 40);
			scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			JTextArea script = new JTextArea(20, 20);
			scS.setViewportView(script);
			myPanel.add(scS);
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Add Script", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				DatabaseLogic.addScript(scriptName.getText().toString(), script
						.getText().toString());
			}
		}
	}

	public void valueChanged(ListSelectionEvent evt) {
		if (!evt.getValueIsAdjusting()) {
			if (list.getSelectedIndex() != -1) {
				System.out.println("here");
				textField_1.setText(((Script) model.getElementAt(list
						.getSelectedIndex())).getName());
				scriptTextArea.setText(((Script) model.getElementAt(list
						.getSelectedIndex())).getScript());
			} else {
				textField_1.setText("");
				scriptTextArea.setText("");
			}
		}
	}

}
