package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.fork.domain.Script;
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.ScriptRunner;

@SuppressWarnings("serial")
public class ScriptPanel extends JPanel {
	private JList<Script> list;
	private List<Script> scriptsNames;
	private DefaultListModel<Script> model;
	private JScrollPane jScrollPane1;

	/**
	 * Create the panel.
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ScriptPanel() {
		setLayout(null);

		JPanel liftList1 = new JPanel();
		liftList1.setBounds(10, 35, 620, 207);
		liftList1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList1.setLayout(new BorderLayout(0, 0));
		add(liftList1);

		scriptsNames = DatabaseLogic.getScripts();
		model = new DefaultListModel();
		for (int i = 0; i < scriptsNames.size(); i++)
			model.addElement(((Script) scriptsNames.get(i)));
		list = new JList(model);
		jScrollPane1 = new JScrollPane(list);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));
		liftList1.add(jScrollPane1, BorderLayout.CENTER);

		JPanel panel_s = new JPanel();
		panel_s.setBounds(10, 253, 618, 58);
		add(panel_s);

		//JButton remove = new JButton("Remove");
		JButton remove = new RoundButton(new ImageIcon("remove.png"),
				"removec.png", "remove.png");
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

		// JButton btnNewButton_2 = new JButton("Add Script");
		JButton btnNewButton_2 = new RoundButton(new ImageIcon("add.png"),
				"addc.png", "add.png");
		panel_s.add(btnNewButton_2);
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
		panel_s.add(remove);

		JLabel lblListOfScripts = new JLabel("List of scripts");
		lblListOfScripts.setHorizontalAlignment(SwingConstants.LEFT);
		lblListOfScripts.setBounds(10, 11, 145, 14);
		add(lblListOfScripts);
	}

	static class ScriptAddition {
		static String name;

		public static void start() {
			final JPanel myPanel = new JPanel();
			myPanel.setBounds(100, 100, 100, 100);
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
			final JTextField path = new JTextField();
			path.setEditable(false);

			JButton browse = new JButton("Browse ...");
			browse.setBounds(0, 0, 627, 33);
			browse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser();
					fc.setAcceptAllFileFilterUsed(false);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int returnVal = fc.showOpenDialog(myPanel);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						String filePath = file.getAbsolutePath();
						name = file.getName();
						path.setText(filePath);
					}
				}
			});
			myPanel.add(browse);
			myPanel.add(path);

			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Add Script", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				try {
					ScriptRunner.copy(path.getText().toString(), name);
					DatabaseLogic.addScript(name);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
