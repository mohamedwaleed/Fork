package com.fork.ClientAdapter;

import java.util.List;


public interface IDataPoller {

	public List<String> pollData(Device device) throws Exception;

}