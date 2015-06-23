package com.fork.backgroundProcess;

import java.util.List;

import com.fork.ClientAdapter.CactiDataPoller;
import com.fork.ClientAdapter.CactiDevicePoller;
import com.fork.domain.Device;
import com.fork.domain.Interface;
import com.fork.persistance.rdf.JenaRetrieval;

public class ForkLifecycle implements IForkLifecycle {

	public List<Device> updateDevicesData() {
		CactiDevicePoller cactiDevicePoller = new CactiDevicePoller();
		CactiDataPoller cactiDataPoller = new CactiDataPoller();
		JenaRetrieval jenaRetrieval = new JenaRetrieval();

		List<Device> oldDevices = jenaRetrieval.getAllDevices();
		List<Device> newDevices = cactiDevicePoller.pollDevice();
		for (Device oldDevice : oldDevices) {
			jenaRetrieval.deleteDevice(oldDevice);
			for (Interface intrface : oldDevice.getInterfaces()) {
				jenaRetrieval.deleteInterface(intrface);
				jenaRetrieval.deleteDeviceInterfaceRelation(oldDevice, intrface);
			}
		}
		for (Device newDevice : newDevices) {
			try {
				newDevice.setInterfaces(cactiDataPoller.pollData(newDevice));
				jenaRetrieval.addDevice(newDevice);
				for (Interface intrface : newDevice.getInterfaces()) {
					jenaRetrieval.addInterface(intrface);
					jenaRetrieval.addDeviceInterfaceRelation(newDevice,
							intrface);
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
				jenaRetrieval.deleteZoneDeviceRelation(oldDevice);
		}
		return newDevices;
	}

}
