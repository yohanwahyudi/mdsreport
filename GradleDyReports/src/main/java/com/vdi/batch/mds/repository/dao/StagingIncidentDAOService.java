package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.staging.Staging;

public interface StagingIncidentDAOService extends BaseDAOService{
	
	public List<Staging> getDataForInsert();
	public void resetSequenceTo1();
	public void disableSafeUpdates();
	public void enableSafeUpdates();
	public void updateIncidentTable();
	public void insertToIncidentTable();
	public void syncDeleteMaster();
	public void syncDeletePrevMonth();
	public List<Object[]> getUnregisteredAgent();

}
