package com.vdi.batch.mds.repository.dao;

import java.util.List;


public interface StagingServiceDeskDAOService extends BaseDAOService{

	public List<Object[]> getUnregisteredAgent();
}
