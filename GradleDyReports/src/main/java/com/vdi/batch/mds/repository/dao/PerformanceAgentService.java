package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.performance.PerformanceAgent;

public interface PerformanceAgentService {
	
	public void save(PerformanceAgent agent);
	public void saveList(List<PerformanceAgent> agents);
	
	public void delete(PerformanceAgent agent);
	public void deleteList(List<PerformanceAgent> agentList);
	
	public void deleteUnassignedAgent();

}
