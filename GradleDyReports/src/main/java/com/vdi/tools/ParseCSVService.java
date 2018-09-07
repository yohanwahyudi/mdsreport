package com.vdi.tools;

import java.util.List;

public interface ParseCSVService {

	public void createCSV();
	public List<String> readHeader();
	public List<List<String>> readRows();
	
}