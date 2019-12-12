package com.vdi.batch.mds.repository.dao;

import java.util.Collection;
import java.util.List;

public interface MonthlyAppTeamDAOService {
	
	public List<Object[]> getAgentPerformance();
	public List<Object[]> getTeamPerformance();
	public <T> void addAll(Collection<T> col);
	public <T> void deleteEntity(Class<T> obj);

}
