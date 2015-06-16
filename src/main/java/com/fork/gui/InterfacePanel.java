package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.fork.domain.Interface;

public class InterfacePanel extends JPanel {
	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private List<Interface> interfaces;
	private DefaultListModel model;
	private JList list;

	/**
	 * Create the panel.
	 */
	public InterfacePanel() {
		setLayout(null);
		setBounds(10, 319, 763, 229);
		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBounds(10, 6, 223, 212);
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(panel);

		

		model = new DefaultListModel();
		list = new JList(model);
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.setMaximumSize(new Dimension(200, 100));
		panel.add(jScrollPane);
		
	
		panel_1 = new JPanel();
		panel_1.setBounds(243, 11, 510, 207);
		add(panel_1);
		panel_1.setLayout(null);
	}

	public void addInteefaces(List<Interface> interfaces) {
		model = new DefaultListModel();
		System.out.println("                 "  +interfaces.size());
		if (interfaces != null) {
			for (int i = 0; i < interfaces.size(); i++)
				model.addElement(((Interface) interfaces.get(i)));
			list.setModel(model);
		}
		
	}
}
