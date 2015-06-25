package com.fork.ClientAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.stamfest.rrd.CommandResult;
import net.stamfest.rrd.RRDp;

import org.rrd4j.core.RrdDb;

import com.fork.domain.DataSource;
import com.fork.domain.InterfaceData;

public class CactiParser implements IDataParser {
	/**
	 * Method for parse an rrd file data to list of InterfaceData
	 * 
	 * @param data
	 *            Device rrd file data
	 * @return List<InterfaceData> output InterfaceData of the rrd file data
	 */
	public InterfaceData parse(String rrdFilePath, List<String> dataSources) {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows"))
			return parseWindows(rrdFilePath, dataSources);
		else
			return parseLinux(rrdFilePath);
	}

	public InterfaceData parseWindows(String rrdFilePath,
			List<String> dataSources) {
		InterfaceData interfaceData = new InterfaceData();
		try {
			File rrdFile = new File(rrdFilePath);
			String path = RrdDb.PREFIX_RRDTool + rrdFile.getCanonicalPath();
			RrdDb convert = new RrdDb("RRD4J.jrb", path);
			convert.close();

			RrdDb parseData = new RrdDb("RRD4J.jrb");
			int ID = 0;
			for (String dataSource : dataSources) {
				DataSource newDataSource = new DataSource();
				newDataSource.setID(String.valueOf(ID));
				newDataSource.setDataSourceName(dataSource);
				double value = parseData.getLastDatasourceValue(dataSource);
				newDataSource.setDataSourceValue(value);
				interfaceData.addDataSource(newDataSource);
				++ID;
			}
			parseData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return interfaceData;
	}

	public InterfaceData parseLinux(String rrdFilePath) {
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
		String dataLine = in.nextLine() + " ", temp = "";
		List<String> dataSources = new ArrayList<String>();
		for (int i = 0; i < dataLine.length(); ++i) {
			if (dataLine.charAt(i) == ' ') {
				if (!temp.isEmpty())
					dataSources.add(temp);
				temp = "";
			} else
				temp += dataLine.charAt(i);
		}
		in.nextLine();
		String line = "";
		while (in.hasNextLine()) {
			line = in.nextLine();
		}
		String[] twoParts = line.split(": ");
		String[] dataRow = twoParts[1].split(" ");
		int ID = 0;
		for (int i = 0; i < dataRow.length; ++i) {
			double value = -1.0;
			if (!dataRow[i].equals("-nan"))
				value = Double.valueOf(dataRow[i]);
			DataSource newDataSource = new DataSource();
			newDataSource.setID(String.valueOf(ID));
			newDataSource.setDataSourceName(dataSources.get(i));
			newDataSource.setDataSourceValue(value);
			interfaceData.addDataSource(newDataSource);
			++ID;
		}
		in.close();
		return interfaceData;
	}
}
