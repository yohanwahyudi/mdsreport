package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.performance.PerformanceTeam;

public interface PerformanceTeamService {

	public void save(PerformanceTeam team);
	public void saveList(List<PerformanceTeam> teams);
	
	public void delete(PerformanceTeam team);
	public void deleteList(List<PerformanceTeam> teams);
	
	public void deleteUnassignedTeam();
	
}
