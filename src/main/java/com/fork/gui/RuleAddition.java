package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.Script;
import com.fork.domain.Zone;
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.RDFLogic;

@SuppressWarnings("serial")
public class RuleAddition extends JFrame implements ListSelectionListener,
		MouseListener {
	private ZoneArea zoneArea;
	private DefaultListModel<Zone> model;
	private DefaultListModel<Interface> model2;
	private List<Interface> interfaces;
	private JList<Zone> list;
	private JList<Interface> list2;
	private List<Zone> zones;
	private List<JLabel> labels;
	private JTextArea ruleName;
	private RulePanel par;
	private RDFLogic rDFLogic;
	private List<Device> zoneDevices;
	private Device selDevice;
	private JLabel lblSelectedDeviceInterfaces;
	private List<String> conditions;

	/**
	 * Create the panel.
	 */
	public RuleAddition(RulePanel d) {
		rDFLogic = new RDFLogic();
		this.par = d;
		labels = new ArrayList<JLabel>();
		setResizable(false);
		setBounds(100, 100, 986, 399);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Rule Addition");
		setVisible(true);
		conditions = new ArrayList<String>();
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 77, 763, 282);
		getContentPane().add(panel);

		zoneArea = new ZoneArea();
		zoneArea.initialize();
		zoneArea.setBounds(195, 11, 558, 260);
		panel.setLayout(null);
		panel.add(zoneArea);

		JPanel liftList = new JPanel();
		liftList.setBounds(10, 40, 167, 231);
		panel.add(liftList);
		liftList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList.setLayout(new BorderLayout(0, 0));

		model = new DefaultListModel<Zone>();
		zones = rDFLogic.getZones();

		for (int i = 0; i < zones.size(); i++)
			model.addElement(((Zone) zones.get(i)));
		list = new JList<Zone>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.setMaximumSize(new Dimension(200, 100));
		liftList.add(jScrollPane, BorderLayout.CENTER);

		JLabel lblListOfZones = new JLabel("List of zones");
		lblListOfZones.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfZones.setBounds(10, 1, 166, 28);
		panel.add(lblListOfZones);

		JLabel lblRuleName = new JLabel("Rule Name");
		lblRuleName.setBounds(284, 15, 76, 25);
		getContentPane().add(lblRuleName);

		ruleName = new JTextArea();
		ruleName.setBounds(357, 15, 190, 25);
		getContentPane().add(ruleName);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(783, 107, 190, 240);
		getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		model2 = new DefaultListModel<Interface>();
		list2 = new JList<Interface>(model2);
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list2.addListSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(list2);
		scrollPane.setMaximumSize(new Dimension(200, 100));
		panel_1.add(scrollPane, BorderLayout.CENTER);

		lblSelectedDeviceInterfaces = new JLabel("Selected device interfaces");
		lblSelectedDeviceInterfaces
				.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectedDeviceInterfaces.setBounds(783, 71, 190, 25);
		getContentPane().add(lblSelectedDeviceInterfaces);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_2.setBounds(583, 11, 190, 63);
		getContentPane().add(panel_2);

		// JButton btnNewButton = new JButton("Add Rule");
		JButton btnNewButton = new RoundButton(new ImageIcon("add.png"),
				"addc.png", "add.png");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (conditions.size() > 0) {
					String name = ruleName.getText().toString();
					String rule = "";
					for (int i = 0; i < conditions.size(); i++) {
						if (i < conditions.size() - 1)
							rule += (conditions.get(i) + "&");
						else
							rule += conditions.get(i);
					}
					if (!name.isEmpty() && !conditions.isEmpty())
						showChooseScriptDialog(name, rule);
				}
			}
		});

	}

	protected void showChooseScriptDialog(String name, String query) {
		UIManager.getLookAndFeel().uninitialize();
		UIManager.getLookAndFeelDefaults().put("OptionPane.minimumSize",
				new Dimension(600, 380));
		DialogJPanel djp = new DialogJPanel(name, query);
		int result = JOptionPane.showConfirmDialog(RuleAddition.this, djp,
				"Choose scripts", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			JList<Script> choosenScripts = djp.getChoosenScripts();
			DefaultListModel<Script> scriptsModel = djp.getModel();
			String nameEdited = djp.getNameEdited();
			if (!nameEdited.isEmpty()) {
				int newId = DatabaseLogic.addRule(nameEdited, query);
				int picked[] = choosenScripts.getSelectedIndices();
				List<Script> pickedScripts = new ArrayList<Script>();
				for (int i : picked)
					pickedScripts.add((Script) (scriptsModel.getElementAt(i)));

				DatabaseLogic.addRuleScripts(pickedScripts, newId);
				par.updateList();
				dispatchEvent(new WindowEvent(RuleAddition.this,
						WindowEvent.WINDOW_CLOSING));
			}
		}
	}

	public void initialize() {

	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == list) {
			if (!e.getValueIsAdjusting()) {
				zoneArea.removeAllComponents();
				if (list.getSelectedIndex() != -1) {
					zoneDevices = rDFLogic.getZoneDevices((Zone) model
							.getElementAt(list.getSelectedIndex()));

					System.out.println((Zone) model.getElementAt(list
							.getSelectedIndex()));
					for (int i = 0; i < zoneDevices.size(); i++) {
						JLabel tmp = zoneArea.addImage(zoneDevices.get(i)
								.getHostName(), zoneDevices.get(i).getIP());
						labels.add(tmp);
						tmp.addMouseListener(this);
					}
				}
			}
		} else if (e.getSource() == list2) {
			if (!e.getValueIsAdjusting()) {
				if (list2.getSelectedIndex() != -1) {
					UIManager.getLookAndFeel().uninitialize();
					UIManager.getLookAndFeelDefaults().put(
							"OptionPane.minimumSize", new Dimension(550, 320));

					
					DataSourcePanel dataSourcePanel = new DataSourcePanel(
							model2.getElementAt(list2.getSelectedIndex()),
							selDevice);
					int result = JOptionPane
							.showConfirmDialog(RuleAddition.this,
									dataSourcePanel, "Data Sources values",
									JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						conditions.add(dataSourcePanel.getConditions());
					}

				}
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		for (int i = 0; i < labels.size(); i++) {
			if (label == labels.get(i)) {
				System.out.println(label.getText().toString());

				Device device = zoneDevices.get(i);
				interfaces = device.getInterfaces();
				System.out.println("Test:  " + interfaces.size());
				model2 = new DefaultListModel<Interface>();
				for (Interface interfac : interfaces)
					model2.addElement((Interface) interfac);
				list2.setModel(model2);
				selDevice = device;
				lblSelectedDeviceInterfaces.setText(selDevice.getHostName());
				break;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
