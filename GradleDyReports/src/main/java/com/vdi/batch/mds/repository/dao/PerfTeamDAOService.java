package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.performance.PerformanceTeam;

public interface PerfTeamDAOService {
	
	public List<Object[]> getTeamTicketByDivision();
	public List<Object[]> getTeamTicketByDivision(int week, int month);
	
	public void insertPerformance(List<PerformanceTeam> team);
	
	public List<PerformanceTeam> getPerformance();
	public List<PerformanceTeam> getPerformance(int week, int month);
	
	public void updatePerformance(List<PerformanceTeam> teams);

}
