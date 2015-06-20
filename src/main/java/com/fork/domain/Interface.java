package com.fork.domain;

public class Interface extends Object {
	private String interfaceName;
	private InterfaceData interfaceData;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public InterfaceData getInterfaceData() {
		return interfaceData;
	}

	public void setInterfaceData(InterfaceData interfaceData) {
		this.interfaceData = interfaceData;
	}

	public Interface() {
	}

	@Override
	public String toString() {
		return interfaceName;
	}

	@Override
	public boolean equals(Object s) {
		if (interfaceName.equals(((Interface) s).getInterfaceName()))
			return true;
		else
			return false;
	}

}
