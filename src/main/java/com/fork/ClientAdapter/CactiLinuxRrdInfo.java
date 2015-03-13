package com.fork.ClientAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Device;

import net.stamfest.rrd.CommandResult;
import net.stamfest.rrd.RRDp;

public class CactiLinuxRrdInfo implements ICactiRrd {
	private String linuxBaseDir = "/var/lib/cacti/rra/";

	@Override
	public List<String> getRrdsInfo(Device device) throws Exception {
		// TODO Auto-generated method stub
		RRDp command = null;
		CommandResult commandResult = null;

		command = new RRDp("/tmp", "/rrdcached.sock");
		List<String> deviceData = new ArrayList<String>();

		List<String> deviceRRDS = getLinuxRRDSInfo(device);
		for (int i = 0; i < deviceRRDS.size(); ++i) {
			String[] cmd = { "fetch", linuxBaseDir + deviceRRDS.get(i),
					"AVERAGE" };
			commandResult = command.command(cmd);
			String data = commandResult.getOutput();
			deviceData.add(data);
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
