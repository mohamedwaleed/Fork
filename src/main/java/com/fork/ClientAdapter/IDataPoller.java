package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;

public interface IDataPoller {

	public List<Interface> pollData(Device device) throws Exception;

}