package com.fork.ClientAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fork.domain.InterfaceData;

public class CactiParser implements IDataParser {

	/**
	 * Method for parse an rrd file data to list of InterfaceData
	 * 
	 * @param data
	 *            Device rrd file data
	 * @return List<InterfaceData> output InterfaceData of the rrd file data
	 */
	public List<InterfaceData> parse(String data) {
		List<InterfaceData> list = new ArrayList<InterfaceData>();
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
				list.add(new InterfaceData(time, inBound, outBound));
			} else
				list.add(new InterfaceData(time, -1, -1));
		}
		in.close();
		return list;
	}

}
