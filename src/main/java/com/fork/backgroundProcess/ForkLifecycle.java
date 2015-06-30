package com.fork.backgroundProcess;

import java.util.List;

import com.fork.ClientAdapter.CactiDataPoller;
import com.fork.ClientAdapter.CactiDevicePoller;
import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.outputController.GUILogic;
import com.fork.outputController.RDFLogic;

public class ForkLifecycle implements IForkLifecycle {

	public List<Device> updateDevicesData() {
		String username = GUILogic.getUsername();
		String pass = GUILogic.getPassowrd();
		CactiDevicePoller cactiDevicePoller = new CactiDevicePoller(username,
				pass);
		CactiDataPoller cactiDataPoller = new CactiDataPoller(username, pass);

		List<Device> newDevices = cactiDevicePoller.pollDevice();
		if (newDevices != null) {
			RDFLogic rDFLogic = new RDFLogic();
			List<Device> oldDevices = rDFLogic.getAllDevices();
			for (Device oldDevice : oldDevices) {
				rDFLogic.deleteDevice(oldDevice);
				for (Interface intrface : oldDevice.getInterfaces()) {
					rDFLogic.deleteInterface(intrface);
					rDFLogic.deleteDeviceInterfaceRelation(oldDevice, intrface);
				}
			}
			for (Device newDevice : newDevices) {
				try {
					newDevice
							.setInterfaces(cactiDataPoller.pollData(newDevice));
					rDFLogic.addDevice(newDevice);
					for (Interface intrface : newDevice.getInterfaces()) {
						rDFLogic.addInterface(intrface);
						rDFLogic.addDeviceInterfaceRelation(newDevice, intrface);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for (Device oldDevice : oldDevices) {
				boolean deviceFound = false;
				for (Device newDevice : newDevices)
					if (oldDevice.getID().equals(newDevice.getID()))
						deviceFound = true;
				if (!deviceFound)
					rDFLogic.deleteZoneDeviceRelation(oldDevice);
			}
		}
		return newDevices;
	}
}
