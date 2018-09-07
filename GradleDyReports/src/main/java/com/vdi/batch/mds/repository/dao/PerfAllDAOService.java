package com.vdi.batch.mds.repository.dao;

import com.vdi.model.performance.PerformanceOverall;

public interface PerfAllDAOService {
	
	public int getTicketCount();
	public int getTicketCount(int week, int month);
	
	public int getAchievedTicketCount();
	public int getAchievedTicketCount(int week, int month);
	
	public int getMissedTicketCount();
	public int getMissedTicketCount(int week, int month);
	
	public void insertPerformance(PerformanceOverall overall);
	
	public PerformanceOverall getPerformance();
	public PerformanceOverall getPerformance(int week, int month);
	
	public void updatePerformance(PerformanceOverall list);

}
