package com.fork.backgroundProcess;

import java.util.List;

import com.fork.ClientAdapter.CactiDataPoller;
import com.fork.ClientAdapter.CactiDevicePoller;
import com.fork.domain.Device;
import com.fork.persistance.rdf.JenaRetrieval;

public class ForkLifecycle implements IForkLifecycle {

	@Override
	public List<Device> updateDevicesData() {
		CactiDevicePoller cactiDevicePoller = new CactiDevicePoller();
		CactiDataPoller cactiDataPoller = new CactiDataPoller();
		JenaRetrieval jenaRetrieval = new JenaRetrieval();
		String URL = "http://www.semanticweb.org/Fork#";
		jenaRetrieval.setOntURL(URL);
		List<Device> devices = cactiDevicePoller.pollDevice();
		List<Device> oldDevices = jenaRetrieval.getDevices();
		for (Device oldDevice : oldDevices)
			jenaRetrieval.deleteDevice(oldDevice);

		for (Device device : devices) {
			try {
				device.setInterfaces(cactiDataPoller.pollData(device));
				jenaRetrieval.addDevice(device);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return devices;
	}

}
