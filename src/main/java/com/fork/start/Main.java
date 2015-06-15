package com.fork.start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.fork.core.Core;
import com.fork.persistance.sqlite.DatabaseConnector;

public class Main {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		DatabaseConnector.buildDatabase();

		initializeCoreThread();

		initialize();
	}

	private void initializeCoreThread() {
		Core core = new Core("Thread-1");
		core.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 626, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 600, 76);
		frame.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 98, 600, 287);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 600, 287);
		panel_1.add(tabbedPane);

		JPanel zoneTab = new JPanel();
		tabbedPane.addTab("Zones", null, zoneTab, null);
		zoneTab.setLayout(new BorderLayout(0, 0));
		// ////////////////////////////////////////////
		JPanel zoneArea = new JPanel();
		zoneArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		zoneArea.setBackground(Color.WHITE);
		zoneArea.setForeground(Color.BLACK);
		zoneTab.add(zoneArea, BorderLayout.CENTER);

		JLabel lblNewLabel_1 = new JLabel("New label");
		zoneArea.add(lblNewLabel_1);

		JPanel zoneName = new JPanel();
		zoneTab.add(zoneName, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Zone Name");
		zoneName.add(lblNewLabel);

		textField = new JTextField();
		textField.setText("");
		zoneName.add(textField);
		textField.setColumns(20);

		JPanel liftList = new JPanel();
		liftList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		zoneTab.add(liftList, BorderLayout.WEST);
		String[] columns = { "islam", "kkkkk" };
		liftList.setLayout(new BorderLayout(0, 0));
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JList list = new JList(columns);
		JScrollPane jScrollPane = new JScrollPane(list);
		jScrollPane.setMaximumSize(new Dimension(200, 100));
		liftList.add(jScrollPane, BorderLayout.CENTER);

		JButton btnNewButton = new JButton(">>");
		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
		liftList.add(btnNewButton, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		zoneTab.add(panel_2, BorderLayout.SOUTH);

		JButton btnNewButton_1 = new JButton("Save Zone");
		panel_2.add(btnNewButton_1);

		// ////////////////////////////////////////////
		JPanel scriptsTab = new JPanel();
		tabbedPane.addTab("Scripts", null, scriptsTab, null);
		scriptsTab.setLayout(new BorderLayout(0, 0));

		final JPanel scriptArea = new JPanel();
		scriptArea.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scriptArea.setBackground(SystemColor.menu);
		scriptArea.setForeground(Color.BLACK);
		scriptsTab.add(scriptArea);
		scriptArea.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Script Name");
		lblNewLabel_2.setBounds(35, 11, 84, 14);
		scriptArea.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Script");
		lblNewLabel_3.setBounds(35, 40, 84, 14);
		scriptArea.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setBackground(SystemColor.window);
		textField_1.setBounds(151, 8, 365, 20);
		scriptArea.add(textField_1);
		textField_1.setColumns(10);
		JScrollPane scS = new JScrollPane();
		scS.setSize(365, 144);
		scS.setLocation(151, 40);
		scS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scriptArea.add(scS);

		JTextArea script = new JTextArea(20, 20);
		scS.setViewportView(script);

		JPanel liftList1 = new JPanel();
		liftList1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scriptsTab.add(liftList1, BorderLayout.WEST);
		String[] columns1 = { "jjjjjj", "kkkkk" };
		liftList1.setLayout(new BorderLayout(0, 0));
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final JList list1 = new JList(columns1);
		JScrollPane jScrollPane1 = new JScrollPane(list1);
		jScrollPane1.setMaximumSize(new Dimension(200, 100));
		liftList1.add(jScrollPane1, BorderLayout.WEST);

		JPanel panel_s = new JPanel();
		scriptsTab.add(panel_s, BorderLayout.SOUTH);

		JButton update = new JButton("Update");
		panel_s.add(update);
		JButton remove = new JButton("Remove");
		panel_s.add(remove);

		JButton btnNewButton_2 = new JButton("Add Script");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame addSFrame = new JFrame();
				addSFrame.setResizable(false);
				addSFrame.setBounds(100, 100, 600, 300);
				addSFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addSFrame.add(scriptArea);
				addSFrame.setVisible(true);
				
				
			}
		});
		scriptsTab.add(btnNewButton_2, BorderLayout.NORTH);

		// ////////////////////////////////////////////
		JPanel rulesTab = new JPanel();
		tabbedPane.addTab("Rules", null, rulesTab, null);
	}
}
