package com.fork.ClientAdapter;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.fork.domain.Device;


public class CactiDataPollerTest {

	@Test
	public void  pollDataTest(){
		IDataPoller poller = new CactiDataPoller();
		Device device = new Device();
		device.setHostName("eslam");
		try {
			List<String> l = poller.pollData(device);
			for (int i = 0; i < l.size(); i++) {
				System.out.println(l.get(i).split("\n")[0]);
			}
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
}
