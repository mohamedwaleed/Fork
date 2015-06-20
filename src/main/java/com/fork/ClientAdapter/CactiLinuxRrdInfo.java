package com.fork.ClientAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.stamfest.rrd.CommandResult;
import net.stamfest.rrd.RRDp;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.domain.InterfaceData;

public class CactiLinuxRrdInfo implements ICactiRrd {
	private String linuxBaseDir = "/var/lib/cacti/rra/";

	public List<Interface> getRrdsInfo(Device device) throws Exception {
		// TODO Auto-generated method stub
		RRDp command = null;
		CommandResult commandResult = null;

		command = new RRDp("/tmp", "/rrdcached.sock");
		List<Interface> interfaces = new ArrayList<Interface>();
		List<String> deviceRRDS = getLinuxRRDSInfo(device);
		String[] words = device.getHostName().split(" ");
		for (int i = 0; i < deviceRRDS.size(); ++i) {
			String[] cmd = { "fetch", linuxBaseDir + deviceRRDS.get(i),
					"AVERAGE" };
			commandResult = command.command(cmd);
			String data = commandResult.getOutput();
			// Get Interface Name.
			String[] filesWords = deviceRRDS.get(i).split("_");
			String interfaceName = filesWords[words.length];
			for (int j = words.length + 1; j < filesWords.length - 1; ++j) {
				interfaceName += " ";
				interfaceName += filesWords[j];
			}
			boolean flag = true;
			for (int j = 0; j < interfaces.size(); ++j)
				if (interfaces.get(j).getInterfaceName().equals(interfaceName))
					flag = false;
			List<InterfaceData> interfaceData = null;
			if (flag) {
				Interface intrface = new Interface();
				intrface.setInterfaceName(device.getID() + "_" + interfaceName);
				interfaceData = new CactiParser().parse(data);
				int last = interfaceData.size() - 1;
				intrface.setInterfaceData(interfaceData.get(last));
				interfaces.add(intrface);
			}
		}

		return interfaces;
	}

	private List<String> getLinuxRRDSInfo(Device device) {
		File file = new File(linuxBaseDir);
		String[] rrdFileNames = file.list();
		String[] words = device.getHostName().split(" ");
		List<String> deviceRRDS = new ArrayList<String>();
		for (int i = 0; i < rrdFileNames.length; i++) {
			String[] filesWords = rrdFileNames[i].split("_");
			int flag = 1;
			for (int j = 0; j < words.length; j++) {
				if (j == filesWords.length || !words[j].equals(filesWords[j])) {
					flag = 0;
					break;
				}
			}
			if (flag == 1)
				deviceRRDS.add(rrdFileNames[i]);
		}
		return deviceRRDS;
	}
}
