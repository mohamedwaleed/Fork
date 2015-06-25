package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.fork.domain.Zone;
import com.fork.outputController.RDFLogic;

@SuppressWarnings("serial")
public class ZoneRemoval extends JPanel {
	private JList<Zone> list;
	private DefaultListModel<Zone> model;
	private List<Zone> zones;
	private RDFLogic rDFLogic;

	/**
	 * Create the panel.
	 */
	public ZoneRemoval() {
		rDFLogic = new RDFLogic();
		setLayout(null);

		JLabel lblScripts = new JLabel("All System Zones");
		lblScripts.setBounds(10, 11, 257, 14);
		add(lblScripts);

		zones = rDFLogic.getZones();
		model = new DefaultListModel<Zone>();
		for (int i = 0; i < zones.size(); i++)
			model.addElement(((Zone) zones.get(i)));

		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 258, 180);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		list = new JList<Zone>(model);
		JScrollPane jScrollPane1 = new JScrollPane(list);
		panel.add(jScrollPane1);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));
	}

	public JList<Zone> getChoosenZones() {
		return list;
	}

	public DefaultListModel<Zone> getModel() {
		return model;
	}

}
