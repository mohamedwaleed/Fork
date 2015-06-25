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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.fork.domain.Device;
import com.fork.domain.Zone;
import com.fork.outputController.RDFLogic;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class ZonePanel extends JPanel {
	private JTextField textField;

	private static List<Device> devices;
	private List<Device> choosenDevices;
	private static DefaultListModel<Device> model;
	private static JList<Device> list;
	private ZoneArea zoneArea;
	private static RDFLogic rDFLogic;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZonePanel() {
		rDFLogic = new RDFLogic();

		setLayout(null);

		zoneArea = new ZoneArea();
		zoneArea.initialize();
		add(zoneArea);

		JPanel zoneName = new JPanel();
		FlowLayout flowLayout = (FlowLayout) zoneName.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		zoneName.setBounds(10, 0, 605, 30);
		add(zoneName);

		JLabel lblNewLabel = new JLabel("Zone Name");
		zoneName.add(lblNewLabel);

		textField = new JTextField();
		textField.setText("");
		zoneName.add(textField);
		textField.setColumns(20);

		JPanel liftList = new JPanel();
		liftList.setBounds(10, 56, 157, 214);
		liftList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		add(liftList);
		liftList.setLayout(new BorderLayout(0, 0));
		choosenDevices = new ArrayList<Device>();
		devices = new ArrayList<Device>();

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
					for (int i = rows.length - 1; i >= 0; i--) {
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
				if (!textField.getText().toString().isEmpty()) {
					List<Zone> zones = rDFLogic.getZones();
					int mx = -1;
					for (Zone curZone : zones)
						mx = Math.max(mx, Integer.valueOf(curZone.getZoneID()));
					Zone zone = new Zone();
					zone.setName(textField.getText().toString());
					zone.setZoneID(String.valueOf(mx + 1));
					rDFLogic.addZone(zone);
					for (Device device : choosenDevices)
						rDFLogic.addZoneDeviceRelation(zone, device);
					zoneArea.removeAllComponents();
					JOptionPane.showMessageDialog(ZonePanel.this,
							"Zone "+textField.getText().toString()+" has been created", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		panel_2.add(btnNewButton_1);

		JButton btnRemoveAZone = new JButton("Remove a Zone");
		btnRemoveAZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ZoneRemoval djp = new ZoneRemoval();
				UIManager
						.put("OptionPane.minimumSize", new Dimension(400, 300));
				int result = JOptionPane.showConfirmDialog(ZonePanel.this, djp,
						"Pick zones to delete", JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					JList choosenZones = djp.getChoosenZones();
					DefaultListModel zonesModel = djp.getModel();
					int picked[] = choosenZones.getSelectedIndices();
					for (int i : picked) {
						rDFLogic.deleteZone((Zone) (zonesModel
								.getElementAt(i)));
						rDFLogic.deleteAllZoneRelations((Zone) (zonesModel
								.getElementAt(i)));
						updateList();
					}

				}

			}
		});
		panel_2.add(btnRemoveAZone);

		JLabel lblListOfNonzoned = new JLabel("List of non-zoned devices");
		lblListOfNonzoned.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfNonzoned.setBounds(10, 35, 157, 20);
		add(lblListOfNonzoned);

	}

	public static void updateList() {
		devices = rDFLogic.getFreeDevices();
		model = new DefaultListModel<Device>();
		for (int i = 0; i < devices.size(); i++)
			model.addElement(((Device) devices.get(i)));
		list.setModel(model);
	}
}
