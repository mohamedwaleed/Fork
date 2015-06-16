package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.fork.domain.Device;
import com.fork.persistance.rdf.JenaRetrieval;

@SuppressWarnings("serial")
public class ZonePanel extends JPanel {
	private JTextField textField;
	
	private List<Device> devices;
	private List<Device> choosenDevices;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	private JList list;
	private ZoneArea zoneArea;
	

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZonePanel() {
		setLayout(null);

		zoneArea = new ZoneArea();
		zoneArea.initialize();
		add(zoneArea);

		JPanel zoneName = new JPanel();
		zoneName.setBounds(10, 0, 605, 30);
		add(zoneName);

		JLabel lblNewLabel = new JLabel("Zone Name");
		zoneName.add(lblNewLabel);

		textField = new JTextField();
		textField.setText("");
		zoneName.add(textField);
		textField.setColumns(20);

		JPanel liftList = new JPanel();
		liftList.setBounds(10, 30, 157, 240);
		liftList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(liftList);
		liftList.setLayout(new BorderLayout(0, 0));

		// devices = JenaRetrieval.getAvailableDevices();
		devices = new ArrayList<Device>();
		Device d = new Device();
		d.setID("1");
		d.setHostName("islam");
		d.setIP("192");
		devices.add(d);
		d = new Device();
		d.setID("2");
		d.setHostName("Ahmed");
		d.setIP("168");
		devices.add(d);

		choosenDevices = new ArrayList<Device>();

		model = new DefaultListModel();
		for (int i = 0; i < devices.size(); i++)
			model.addElement(((Device) devices.get(i)));
		list = new JList(model);
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.setMaximumSize(new Dimension(200, 100));
		liftList.add(jScrollPane, BorderLayout.CENTER);

		JButton btnNewButton = new JButton(">>");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					int rows[] = list.getSelectedIndices();
					Arrays.sort(rows);
					for (int i = rows.length-1; i >= 0; i--) {
						Device newD = (Device) model.getElementAt(rows[i]);
						choosenDevices.add(newD);
						model.removeElementAt(rows[i]);
						zoneArea.addImage(newD.getHostName(), newD.getIP());
					}
				}
			}
		});
		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
		liftList.add(btnNewButton, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 281, 605, 33);
		add(panel_2);

		JButton btnNewButton_1 = new JButton("Save Zone");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!textField.getText().toString().isEmpty())
					JenaRetrieval.createZone(devices, textField.getText()
							.toString());
			}
		});
		panel_2.add(btnNewButton_1);

	}

}
