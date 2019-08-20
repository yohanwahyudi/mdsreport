package com.vdi.batch.mds.service;

import java.util.List;

import com.vdi.model.staging.Staging;

public interface ItopMDSDataLoaderService {
	
	public List<List<String>> loadTrToListVisionetByUrl();
	public List<List<String>> loadTrToListVisionetByFile();
	
	public List getStagingAllByURL(); 
	public List getStagingAllByFile();

}
