package com.fork.ClientAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.stamfest.rrd.CommandResult;
import net.stamfest.rrd.RRDp;

public class CactiDataPoller implements IDataPoller {
	private String linuxBaseDir = "/var/lib/cacti/rra/";
	private String windowsBaseDir = "C:\\Cacti\\Apache2\\htdocs\\cacti\\rra\\";

	/**
	 * Method for get data from rrd files in a given device
	 * 
	 * @param device
	 *            Device that you will poll data for it
	 * @return List<String> output of each rrd file in device
	 */
	@Override
	public List<String> pollData(Device device) throws Exception {
		// TODO Auto-generated method stub

		String osName = System.getProperty("os.name").toLowerCase();
		RRDp command = null;
		CommandResult commandResult = null;

		command = new RRDp("/tmp", "/rrdcached.sock");
		List<String> deviceData = new ArrayList<String>();
		if (osName.charAt(0) == 'l') {
			List<String> deviceRRDS = getLinuxRRDSInfo(device);
			for (int i = 0; i < deviceRRDS.size(); ++i) {
				String[] cmd = { "fetch", linuxBaseDir + deviceRRDS.get(i),
						"AVERAGE" };
				commandResult = command.command(cmd);
				String data = commandResult.getOutput();
				deviceData.add(data);
			}
		} else if (osName.charAt(0) == 'W') {
		}
		return deviceData;
	}

	private List<String> getLinuxRRDSInfo(Device device) {
		File file = new File(linuxBaseDir);
		String[] rrdFileNames = file.list();
		List<String> deviceRRDS = new ArrayList<String>();
		for (int i = 0; i < rrdFileNames.length; i++) {
			if (rrdFileNames[i].split("_")[0].equals(device.getHostName())) {
				deviceRRDS.add(rrdFileNames[i]);
			}
		}
		return deviceRRDS;
	}
}