package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.fork.domain.DataSource;
import com.fork.domain.Device;
import com.fork.domain.Interface;

@SuppressWarnings("serial")
public class DataSourcePanel extends JPanel {
	private JPanel panel = new JPanel();
	private JPanel panel_1 = new JPanel();
	private DefaultListModel<DataSource> model;
	private JList<DataSource> list;
	private String condition;
	private JTextArea min, max;
	private Device device;
	private Interface interface1;

	/**
	 * Create the panel.
	 */
	public DataSourcePanel(Interface inte, Device device) {
		this.device = device;
		this.interface1 = inte;
		condition = "";
		setLayout(null);
		setBounds(10, 319, 538, 275);

		setVisible(true);
		panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBounds(10, 32, 223, 186);
		add(panel);

		model = new DefaultListModel<DataSource>();
		List<DataSource> ds = interface1.getData().getDataSources();
		for (DataSource d : ds) {
			model.addElement(d);
		}
		list = new JList<DataSource>(model);
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.setMaximumSize(new Dimension(200, 100));
		panel.add(jScrollPane);

		panel_1 = new JPanel();
		panel_1.setBounds(243, 11, 281, 207);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel lblMinimum = new JLabel("Minimum");
		lblMinimum.setBounds(23, 11, 75, 32);
		panel_1.add(lblMinimum);

		JLabel lblMaximum = new JLabel("Maximum");
		lblMaximum.setBounds(23, 54, 75, 32);
		panel_1.add(lblMaximum);

		min = new JTextArea();
		min.setBounds(108, 11, 98, 32);
		panel_1.add(min);

		max = new JTextArea();
		max.setBounds(107, 54, 99, 32);
		panel_1.add(max);

		JLabel lblNoteAnyTraffic = new JLabel(
				"Note: Any traffic data is entered in Bits");
		lblNoteAnyTraffic.setForeground(Color.RED);
		lblNoteAnyTraffic.setBounds(10, 182, 261, 14);
		panel_1.add(lblNoteAnyTraffic);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_2.setBounds(43, 97, 163, 68);
		panel_1.add(panel_2);

		// JButton btnNewButton = new JButton("Add condition");
		JButton btnNewButton = new RoundButton(new ImageIcon("add.png"),
				"addc.png", "add.png");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("asds");
				if (!min.getText().toString().isEmpty()
						&& !max.getText().toString().isEmpty()
						&& list.getSelectedIndex() != -1) {
					convertInputToCondition();
					min.setText("");
					max.setText("");
				}
			}
		});

		JLabel lblListOfInterfaces = new JLabel("List of data sources");
		lblListOfInterfaces.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfInterfaces.setBounds(10, 7, 223, 14);
		add(lblListOfInterfaces);
	}

	protected void convertInputToCondition() {
		System.out.println("  " + device.getHostName() + "  " + device.getIP());
		String interfaceS = interface1.getName();
		String inMn = min.getText().toString();
		String inMx = max.getText().toString();
		DataSource ds = model.getElementAt(list.getSelectedIndex());
		if (condition.isEmpty())
			condition = device.getHostName() + "#" + interfaceS;
		condition += ("#" + ds.getDataSourceName() + "#" + inMn + "#" + inMx);
		UIManager.getLookAndFeel().uninitialize();
		
		UIManager.getLookAndFeelDefaults().put("OptionPane.minimumSize",
				new Dimension(262, 90));
		JOptionPane.showMessageDialog(DataSourcePanel.this,
				"Condition has been added", "Success",
				JOptionPane.INFORMATION_MESSAGE);

	}

	public String getConditions() {
		return condition;
	}
}
