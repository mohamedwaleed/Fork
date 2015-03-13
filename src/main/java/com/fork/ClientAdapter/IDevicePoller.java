package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.Device;

public interface IDevicePoller {

	public List<Device> pollDevice();

}