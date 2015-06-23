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
import com.fork.persistance.rdf.JenaRetrieval;

public class ZoneRemoval extends JPanel {
	private JList list;
	private DefaultListModel model;
	private List<Zone> zones;
	private JenaRetrieval jenaRetrieval;

	/**
	 * Create the panel.
	 */
	public ZoneRemoval() {
		jenaRetrieval = new JenaRetrieval();
		setLayout(null);

		JLabel lblScripts = new JLabel("All System Zones");
		lblScripts.setBounds(10, 11, 257, 14);
		add(lblScripts);

		zones = jenaRetrieval.getZones();
		model = new DefaultListModel();
		for (int i = 0; i < zones.size(); i++)
			model.addElement(((Zone) zones.get(i)));

		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 258, 180);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		list = new JList(model);
		JScrollPane jScrollPane1 = new JScrollPane(list);
		panel.add(jScrollPane1);
		jScrollPane1.setMaximumSize(new Dimension(100, 200));
	}

	public JList getChoosenZones() {
		return list;
	}

	public DefaultListModel getModel() {
		return model;
	}

}
