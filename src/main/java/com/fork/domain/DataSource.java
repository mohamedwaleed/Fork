package com.fork.domain;

public class DataSource extends Object{
	private String ID;
	private String dataSourceName;
	private double dataSourceValue;

	public String getDataSourceName() {
		return dataSourceName;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public double getDataSourceValue() {
		return dataSourceValue;
	}

	public void setDataSourceValue(double dataSourceValue) {
		this.dataSourceValue = dataSourceValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (dataSourceName.equals(((DataSource) obj).getDataSourceName()))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return dataSourceName;
	}
	
}
