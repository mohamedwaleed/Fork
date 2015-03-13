package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.Device;


public interface IDataPoller {

	public List<String> pollData(Device device) throws Exception;

}