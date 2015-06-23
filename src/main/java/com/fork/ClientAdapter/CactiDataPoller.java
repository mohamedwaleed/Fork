package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.utills.Factory;

public class CactiDataPoller implements IDataPoller {
	private String baseClassName = "com.fork.ClientAdapter.";

	/**
	 * Method for get data from rrd files in a given device
	 * 
	 * @param device
	 *            Device that you will poll data for it
	 * @return List<String> output of each rrd file in device
	 */
	public List<Interface> pollData(Device device) throws Exception {
		// TODO Auto-generated method stub
		String osName = System.getProperty("os.name");
		String actualOsName = osName.split(" ")[0];
		String requiredClassPath = baseClassName + "Cacti" + actualOsName
				+ "RrdInfo";
		ICactiRrd cactiRrd = (ICactiRrd) Factory.newInstance(requiredClassPath);
		List<Interface> deviceData = cactiRrd.getRrdsInfo(device);
		return deviceData;
	}

}