package com.fork.ClientAdapter;

import java.util.List;

import com.fork.domain.InterfaceData;

public interface IDataParser {

	public InterfaceData parse(String rrdFilePath, List<String> dataSources);

}