package com.fork.backgroundProcess;

import java.awt.BorderLayout;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.fork.core.CoreRuntime;
import com.fork.domain.Device;
import com.fork.domain.Rule;
import com.fork.gui.MainWindow;
import com.fork.gui.ZonePanel;
import com.fork.outputController.DatabaseLogic;

public class ForkRuntime extends TimerTask {
	private JFrame frame;

	public ForkRuntime(JFrame frame) {
		this.frame = frame;
	}

	public void run() {

		// create an abject of ForkLifecycle
		// call functions stack

		final IForkLifecycle appLifecycle = new ForkLifecycle();
		final JDialog dlgProgress = new JDialog(frame, "Please wait...", true);
		dlgProgress.setSize(200, 200);
		dlgProgress.setLocationRelativeTo(null);
		JProgressBar pbProgress = new JProgressBar(0, 100);
		pbProgress.setIndeterminate(true);
		JLabel lblStatus = new JLabel("Loading...");
		dlgProgress.getContentPane().add(BorderLayout.NORTH, lblStatus);
		dlgProgress.getContentPane().add(BorderLayout.CENTER, pbProgress);
		dlgProgress.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dlgProgress.setSize(300, 90);
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
			List<Device> tmp;

			@Override
			protected Void doInBackground() throws Exception {
				tmp = appLifecycle.updateDevicesData();
				return null;
			}

			@Override
			protected void done() {
				dlgProgress.dispose();// close the modal dialog
				if (tmp != null) {
					ZonePanel.updateList();

					List<Rule> rules = DatabaseLogic.getActiveRules();
					CoreRuntime.testRules(rules);
				} else {
					MainWindow.showWrongMysqlAuth();
				}
			}
		};
		sw.execute(); // this will start the processing on a separate
		dlgProgress.setVisible(true); // this will block user input as

	}
}
