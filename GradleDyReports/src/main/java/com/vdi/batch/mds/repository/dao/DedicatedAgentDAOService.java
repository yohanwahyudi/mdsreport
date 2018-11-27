package com.vdi.batch.mds.repository.dao;

import java.util.Collection;
import java.util.List;

import com.vdi.model.performance.DedicatedAgentSummary;
import com.vdi.model.performance.DedicatedAgentTeam;

public interface DedicatedAgentDAOService {
	
	public DedicatedAgentSummary getPreviousMonthSummary(String category);	
	public List<DedicatedAgentTeam> getPreviousMonthAgentTeam(String category);
	public List<Object[]> getPreviousMonthTeam(String category);
	
//	public List<DedicatedAgentSummary> insertToDedicatedAgentSummary();
//	public List<DedicatedAgentTeam> insertToDedicatedAgentTeam();
	
	public <T> void addAllCollections(Collection<T> col);
	
	public <T> void deleteEntity(Class<T> obj);


}
