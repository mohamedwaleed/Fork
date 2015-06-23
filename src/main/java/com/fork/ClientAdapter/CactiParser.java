package com.fork.ClientAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.stamfest.rrd.CommandResult;
import net.stamfest.rrd.RRDp;

import com.fork.domain.InterfaceData;

public class CactiParser implements IDataParser {
	/**
	 * Method for parse an rrd file data to list of InterfaceData
	 * 
	 * @param data
	 *            Device rrd file data
	 * @return List<InterfaceData> output InterfaceData of the rrd file data
	 */
	public InterfaceData parse(String rrdFilePath) {
		RRDp command = null;
		CommandResult commandResult = null;
		try {
			command = new RRDp("/tmp", "/rrdcached.sock");
			String[] cmd = { "fetch", rrdFilePath, "AVERAGE" };
			commandResult = command.command(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = commandResult.getOutput();
		InterfaceData interfaceData = new InterfaceData();
		Scanner in = new Scanner(data);
		in.nextLine();
		in.nextLine();
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] twoParts = line.split(": ");
			long time = Long.parseLong(twoParts[0]);
			String inB = twoParts[1].split(" ")[0], outB = twoParts[1]
					.split(" ")[1];
			if (!inB.equals("-nan") && !outB.equals("-nan")) {
				double inBound = Double.valueOf(inB);
				double outBound = Double.valueOf(outB);
				interfaceData = new InterfaceData(time, inBound, outBound);
			} else
				interfaceData = new InterfaceData(time, -1, -1);
		}
		in.close();
		return interfaceData;
	}

}
