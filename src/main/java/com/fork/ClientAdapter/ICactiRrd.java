package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.Device;
import com.fork.domain.Interface;

public interface ICactiRrd {
	public List<Interface> getRrdsInfo(Device device) throws Exception;
}
