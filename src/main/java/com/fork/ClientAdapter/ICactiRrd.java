package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.Device;

public interface ICactiRrd {
	public List<String> getRrdsInfo(Device device) throws Exception;
}
