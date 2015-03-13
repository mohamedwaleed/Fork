package com.fork.utills;

import java.util.List;

import org.junit.Test;

import com.fork.ClientAdapter.ICactiRrd;
import com.fork.domain.Device;

import static org.junit.Assert.*;

public class FactoryTest {

	@Test
	public void newInstanceTest() {

		ICactiRrd cactiRrd;
		try {
			Device device = new Device();
			device.setHostName("eslam");
			cactiRrd = (ICactiRrd) Factory
					.newInstance("com.fork.ClientAdapter.CactiLinuxRrdInfo");
			List<String> deviceRrds = cactiRrd.getRrdsInfo(device);
			assertTrue(deviceRrds.size() > 0);
			assertTrue(cactiRrd instanceof com.fork.ClientAdapter.CactiLinuxRrdInfo);
		} catch (ClassNotFoundException e) {
			assertTrue(false);
		} catch (InstantiationException e) {
			assertTrue(false);
		} catch (IllegalAccessException e) {
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
}
