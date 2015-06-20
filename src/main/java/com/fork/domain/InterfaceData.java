package com.fork.domain;

public class InterfaceData {
	private long time;
	private double inBound, outBound;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getInBound() {
		return inBound;
	}

	public void setInBound(double inBound) {
		this.inBound = inBound;
	}

	public double getOutBound() {
		return outBound;
	}

	public void setOutBound(double outBound) {
		this.outBound = outBound;
	}

	public InterfaceData() {
	}

	public InterfaceData(long time, double inBound, double outBound) {
		super();
		this.time = time;
		this.inBound = inBound;
		this.outBound = outBound;
	}

}
