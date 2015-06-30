package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.fork.domain.Device;
import com.fork.domain.Interface;

public class InterfacePanel extends JPanel {
	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private List<Interface> interfaces;
	private DefaultListModel model;
	private JList list;
	private List<String> conditions;
	private JTextArea inMin, inMax, outMin, outMax;
	private Device device;

	/**
	 * Create the panel.
	 */
	public InterfacePanel() {
		setLayout(null);
		setBounds(10, 319, 763, 229);
		conditions = new ArrayList<String>();
		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBounds(10, 32, 223, 186);
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

		JLabel lblInbound = new JLabel("InBound");
		lblInbound.setBounds(10, 55, 68, 23);
		panel_1.add(lblInbound);

		JLabel lblOutbound = new JLabel("OutBound");
		lblOutbound.setBounds(10, 100, 68, 23);
		panel_1.add(lblOutbound);

		JLabel lblMinimum = new JLabel("Minimum");
		lblMinimum.setBounds(136, 11, 62, 23);
		panel_1.add(lblMinimum);

		JLabel lblMaximum = new JLabel("Maximum");
		lblMaximum.setBounds(316, 11, 62, 23);
		panel_1.add(lblMaximum);

		inMin = new JTextArea();
		inMin.setBounds(123, 60, 75, 15);
		panel_1.add(inMin);

		inMax = new JTextArea();
		inMax.setBounds(303, 60, 75, 15);
		panel_1.add(inMax);

		outMin = new JTextArea();
		outMin.setBounds(123, 105, 75, 15);
		panel_1.add(outMin);

		outMax = new JTextArea();
		outMax.setBounds(303, 105, 75, 15);
		panel_1.add(outMax);

		JButton btnNewButton = new JButton("Add condition");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!inMin.getText().toString().isEmpty()
						&& !inMax.getText().toString().isEmpty()
						&& !outMin.getText().toString().isEmpty()
						&& !outMax.getText().toString().isEmpty()
						&& list.getSelectedIndex() != -1)
					convertInputToCondition();
			}
		});
		btnNewButton.setBounds(385, 161, 115, 35);
		panel_1.add(btnNewButton);

		JLabel lblListOfInterfaces = new JLabel("List of interfaces");
		lblListOfInterfaces.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfInterfaces.setBounds(10, 7, 223, 14);
		add(lblListOfInterfaces);
	}

	protected void convertInputToCondition() {
		System.out.println("  " + device.getHostName() + "  " + device.getIP());
		String interfaceS = ((Interface) model.getElementAt(list
				.getSelectedIndex())).getName();
		String inMn = inMin.getText().toString();
		String inMx = inMax.getText().toString();
		String outMn = outMin.getText().toString();
		String outMx = outMax.getText().toString();
		String condition = device.getHostName() + "#" + interfaceS + "#" + inMn
				+ "#" + inMx + "#" + outMn + "#" + outMx;
		conditions.add(condition);

		JOptionPane.showMessageDialog(InterfacePanel.this,
				"Condition has been added", "Success",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void addInteefaces(List<Interface> interfaces, Device device) {
		this.device = device;
		model = new DefaultListModel();
		if (interfaces != null) {
			for (int i = 0; i < interfaces.size(); i++)
				model.addElement(((Interface) interfaces.get(i)));
			list.setModel(model);
		}
	}

	public List<String> getConditions() {
		return conditions;
	}
}
