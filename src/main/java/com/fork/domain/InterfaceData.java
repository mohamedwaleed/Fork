package com.fork.domain;

import java.util.ArrayList;
import java.util.List;

public class InterfaceData {
	private String ID;
	private List<DataSource> dataSources;

	public InterfaceData() {
		setDataSources(new ArrayList<DataSource>());
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public List<DataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public void addDataSource(DataSource dataSource) {
		this.dataSources.add(dataSource);
	}
}
