package com.vdi.batch.mds.repository.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.WeeklyURPerfAllRepository;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.model.performance.PerformanceOverall;

@Transactional
@Repository("weeklyURPerfAllDAO")
public class WeeklyURPerfAllDAOImpl implements PerfAllDAOService{

	@Autowired
	private WeeklyURPerfAllRepository repo;
	
	@Override
	public int getTicketCount() {
		
		return repo.getAllTicketCount();
	}

	@Override
	public int getTicketCount(int week, int month) {
		
		return repo.getTicketCount(week, month);
	}

	@Override
	public int getAchievedTicketCount() {
		
		return 0;
	}

	@Override
	public int getAchievedTicketCount(int week, int month) {
		
		return repo.getAchievedTicketCount(week, month);
	}

	@Override
	public int getMissedTicketCount() {
		
		return 0;
	}

	@Override
	public int getMissedTicketCount(int week, int month) {
		
		return repo.getMissedTicketCount(week, month);
	}

	@Override
	public void insertPerformance(PerformanceOverall overall) {
		
		repo.save(overall);
		
	}

	@Override
	public PerformanceOverall getPerformance() {
		
		return null;
	}

	@Override
	public PerformanceOverall getPerformance(int week, int month) {
		
		return repo.getPerformanceThisWeek(week, month);
	}

	@Override
	public void updatePerformance(PerformanceOverall list) {
		
		
	}

	
	
}
