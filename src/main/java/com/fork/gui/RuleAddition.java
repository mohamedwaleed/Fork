package com.fork.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
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
import com.fork.persistance.rdf.JenaRetrieval;
import com.fork.persistance.sqlite.DatabaseLogic;

@SuppressWarnings("serial")
public class RuleAddition extends JFrame implements ListSelectionListener,
		MouseListener {
	private ZoneArea zoneArea;
	private InterfacePanel interfacePanel;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	@SuppressWarnings("rawtypes")
	private JList list;
	private List<Zone> zones;
	private List<JLabel> labels;
	private JTextArea ruleName;
	private RulePanel par;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings("unchecked")
	public RuleAddition(RulePanel d) {
		this.par = d;
		labels = new ArrayList<JLabel>();
		setResizable(false);
		setBounds(100, 100, 789, 588);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Rule Addition");
		setVisible(true);

		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(10, 39, 763, 257);
		getContentPane().add(panel);

		zoneArea = new ZoneArea();
		zoneArea.initialize();
		zoneArea.setBounds(186, 0, 576, 257);
		panel.setLayout(null);
		panel.add(zoneArea);

		JPanel liftList = new JPanel();
		liftList.setBounds(10, 26, 167, 231);
		panel.add(liftList);
		liftList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		liftList.setLayout(new BorderLayout(0, 0));

		model = new DefaultListModel();
		zones = JenaRetrieval.getAllZones();

		zones = new ArrayList<Zone>();
		Zone s = new Zone();
		s.setName("ilam");
		zones.add(s);
		s = new Zone();
		s.setName("moha");
		zones.add(s);

		for (int i = 0; i < zones.size(); i++)
			model.addElement(((Zone) zones.get(i)));
		list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.setMaximumSize(new Dimension(200, 100));
		liftList.add(jScrollPane, BorderLayout.CENTER);

		JLabel lblListOfZones = new JLabel("List of zones");
		lblListOfZones.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOfZones.setBounds(10, 1, 166, 14);
		panel.add(lblListOfZones);

		interfacePanel = new InterfacePanel();
		getContentPane().add(interfacePanel);

		JButton btnNewButton = new JButton("Add Rule");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = ruleName.getText().toString();
				List<String> conditions = interfacePanel.getConditions();
				if (!name.isEmpty() && !conditions.isEmpty()) {
					String query = "islam";

					// /////////////////////////////////////////////////////////////////////////////

					showChooseScriptDialog(name, query);
				}
			}
		});
		btnNewButton.setBounds(522, 11, 251, 23);
		getContentPane().add(btnNewButton);

		JLabel lblRuleName = new JLabel("Rule Name");
		lblRuleName.setBounds(112, 14, 76, 14);
		getContentPane().add(lblRuleName);

		ruleName = new JTextArea();
		ruleName.setBounds(198, 13, 190, 15);
		getContentPane().add(ruleName);

	}

	protected void showChooseScriptDialog(String name, String query) {

		DialogJPanel djp = new DialogJPanel(name, query);
		UIManager.put("OptionPane.minimumSize", new Dimension(500, 350));
		int result = JOptionPane.showConfirmDialog(RuleAddition.this, djp,
				"Choose scripts", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			JList choosenScripts = djp.getChoosenScripts();
			DefaultListModel scriptsModel = djp.getModel();
			;
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
		if (!e.getValueIsAdjusting()) {
			zoneArea.removeAllComponents();
			if (list.getSelectedIndex() != -1) {
				List<Device> zoneDevices = new ArrayList<Device>();
				zoneDevices = JenaRetrieval.getZoneDevices((Zone) model
						.getElementAt(list.getSelectedIndex()));

				zoneDevices = new ArrayList<Device>();
				Device d = new Device();
				d.setID("1");
				d.setHostName("islam");
				d.setIP("192");
				zoneDevices.add(d);
				d = new Device();
				d.setID("2");
				d.setHostName("Ahmed");
				d.setIP("168");
				zoneDevices.add(d);

				for (int i = 0; i < zoneDevices.size(); i++) {
					JLabel tmp = zoneArea.addImage(zoneDevices.get(i)
							.getHostName(), zoneDevices.get(i).getIP());
					labels.add(tmp);
					tmp.addMouseListener(this);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		JLabel label = (JLabel) e.getSource();
		for (int i = 0; i < labels.size(); i++) {
			if (label == labels.get(i)) {
				System.out.println(label.getText().toString());
				Device device = new Device();
				device.setIP(label.getText().toString().split("-")[1]);
				device.setHostName(label.getText().toString().split("-")[0]);
				List<Interface> interfaces = new ArrayList<Interface>();
				interfaces = JenaRetrieval.getDeviceInterfaces(device
						.getHostName());

				if (label.getText().toString().equals("islam-192")) {
					interfaces = new ArrayList<Interface>();
					Interface in = new Interface();
					in.setName("Ali");
					interfaces.add(in);
					in = new Interface();
					in.setName("Mohamed");
					interfaces.add(in);
				} else {
					interfaces = new ArrayList<Interface>();
					Interface in = new Interface();
					in.setName("hahahahhahahha");
					interfaces.add(in);
					in = new Interface();
					in.setName("done it");
					interfaces.add(in);
				}

				interfacePanel.addInteefaces(interfaces, device);
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
