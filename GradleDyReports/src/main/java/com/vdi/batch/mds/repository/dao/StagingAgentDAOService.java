package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.staging.StagingAgent;

public interface StagingAgentDAOService extends BaseDAOService{

	public List<StagingAgent> getDataForInsert();
	public void resetSequenceTo1();
	public void disableSafeUpdates();
	public void enableSafeUpdates();
}
