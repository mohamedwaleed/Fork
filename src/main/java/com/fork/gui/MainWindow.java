package com.fork.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.GUILogic;

public class MainWindow {
	public static String username = "";
	public static String password = "";
	public JFrame frame;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
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

		getMySQlAuth();

		ImagePanel panel = new ImagePanel(new ImageIcon("fork.jpg").getImage());

		panel.setBounds(10, 11, 641, 76);
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

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Mysql Authentication");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMySqlAuth();

			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmQuit = new JMenuItem("Exit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmQuit);
	}

	private void getMySQlAuth() {
		String[] auth = DatabaseLogic.getMysqlAuth();
		MainWindow.username = auth[0];
		MainWindow.password = auth[1];
	}

	public int showMySqlAuth() {
		JTextField username = new JTextField();
		username.requestFocusInWindow();
		JPasswordField password = new JPasswordField();
		JPanel myPanel = new JPanel();
		myPanel.setBounds(100, 100, 100, 100);
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		myPanel.add(new JLabel("Username: "));
		myPanel.add(username);
		myPanel.add(new JLabel("Password: "));
		myPanel.add(password);

		int result = JOptionPane.showConfirmDialog(frame, myPanel,
				"MySql Authentication", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			MainWindow.username = username.getText().toString();
			MainWindow.password = new String(password.getPassword());
			DatabaseLogic.updateMysqlAuth(MainWindow.username,
					MainWindow.password);
			if (GUILogic.tryMysqlConnection(MainWindow.username,
					MainWindow.password))
				return 0;
			else
				return 1;
		}
		return 2;
	}

	public static void showWrongAuth() {
		JOptionPane.showMessageDialog(null,
				"Fueski or Apache not running \n Please run Fueski server and Apache.",
				"Warning", JOptionPane.WARNING_MESSAGE);
	}
}

@SuppressWarnings("serial")
class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;

		setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
