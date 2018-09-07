package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.batch.mds.repository.dao.BaseDAOService;
import com.vdi.model.staging.StagingUserRequest;

public interface StagingUserRequestDAOService extends BaseDAOService{
	
	public List<StagingUserRequest> getAllIncidentByWeek(int month, int week);
	public List<StagingUserRequest> getAllIncidentByMonth(int month);

}
