package com.fork.backgroundProcess;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import com.fork.core.CoreRuntime;
import com.fork.core.Reasoner;
import com.fork.core.RuleSequence;
import com.fork.domain.Device;
import com.fork.domain.Rule;
import com.fork.outputController.DatabaseLogic;
import com.fork.outputController.GUILogic;
import com.fork.outputController.RuleFire;

public class ForkRuntime extends TimerTask {
	private JFrame frame;
	public static int reasonningInterval = 3;
    public static RuleSequence ruleSequence = new RuleSequence();
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
					GUILogic.updateZonePanelList();
					List<Rule> rules = DatabaseLogic.getActiveRules();
					List<Rule> firedRules = CoreRuntime.testRules(rules);


                    Reasoner reasoner = new Reasoner();
                    reasonningInterval--;

                    List<Rule> recommededRules = null;
                    boolean fired = false;
                    boolean reInitialize = false;
                    for (int i = 0 ; i < firedRules.size(); i ++){
                        recommededRules = null;
                        if(!ruleSequence.find(firedRules.get(i))) {
                            ruleSequence.add(firedRules.get(i));
                            fired = true;
						}
                        if(reasonningInterval == 0) {
                            // each 15 minutes as we poll every 5 minutes so 15 / 5 = 3 reasonningInterval
                            recommededRules = reasoner.performReasoning(firedRules.get(i));
                            reInitialize = true;
                        }
                        if(recommededRules!=null && recommededRules.size() == 0)
                            recommededRules = null;

                        RuleFire window = new RuleFire(firedRules.get(i),recommededRules);
                        window.frame.setVisible(true);
                    }

                    if(reInitialize){
                        reasoner.addToHistory(ruleSequence);
                        reasonningInterval = 3;
                        ruleSequence.clear();
                    }



				} else {
					GUILogic.showWrongAuth();
				}
			}
		};
		sw.execute(); // this will start the processing on a separate
		dlgProgress.setVisible(true); // this will block user input as

	}
}
