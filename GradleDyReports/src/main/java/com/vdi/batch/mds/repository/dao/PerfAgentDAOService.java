package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.performance.PerformanceAgent;

public interface PerfAgentDAOService {
	
	public List<Object[]> getAgentTicket();
	public List<Object[]> getAgentTicket(int week, int month);
	
	public void insertPerformance(List<PerformanceAgent> listAgent);
	
	public List<PerformanceAgent> getPerformance();
	public List<PerformanceAgent> getPerformance(int week, int month);
	
	public void updatePerformance(List<PerformanceAgent> agents);

}
