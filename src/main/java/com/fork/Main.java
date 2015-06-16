package com.fork;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.fork.core.Core;
import com.fork.gui.RulePanel;
import com.fork.gui.ScriptPanel;
import com.fork.gui.ZonePanel;
import com.fork.persistance.sqlite.DatabaseConnector;

public class Main {

	private JFrame frame;

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
		frame.setBounds(100, 100, 669, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Fork");

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 641, 76);
		panel.add(new JLabel(new ImageIcon("running.png")));
		frame.getContentPane().add(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 98, 641, 347);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 641, 347);
		panel_1.add(tabbedPane);

		ZonePanel zoneTab = new ZonePanel();
		tabbedPane.addTab("Zones", null, zoneTab, null);

		ScriptPanel scriptsTab = new ScriptPanel();
		tabbedPane.addTab("Scripts", null, scriptsTab, null);

		RulePanel rulesTab = new RulePanel();
		tabbedPane.addTab("Rules", null, rulesTab, null);
	}
}
