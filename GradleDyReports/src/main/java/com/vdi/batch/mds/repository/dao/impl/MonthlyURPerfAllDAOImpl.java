package com.vdi.batch.mds.repository.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.MonthlyURPerfAllRepository;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.model.performance.PerformanceOverall;

@Transactional
@Repository("monthlyURPerfAllDAO")
public class MonthlyURPerfAllDAOImpl implements PerfAllDAOService{

	@Autowired
	private MonthlyURPerfAllRepository repo;
	
	@Override
	public int getTicketCount() {
		
		return repo.getAllTicketCount();
	}

	@Override
	public int getTicketCount(int week, int month) {
		
		return repo.getTicketCount(month);
	}

	@Override
	public int getAchievedTicketCount() {
		
		return 0;
	}

	@Override
	public int getAchievedTicketCount(int week, int month) {
		
		return repo.getAchievedTicketCount(month);
	}

	@Override
	public int getMissedTicketCount() {
		
		return 0;
	}

	@Override
	public int getMissedTicketCount(int week, int month) {
		
		return repo.getMissedTicketCount(month);
	}

	@Override
	public void insertPerformance(PerformanceOverall overall) {
		
		repo.save(overall);
		
	}

	@Override
	public PerformanceOverall getPerformance() {
		
		return repo.getPerformanceThisMonth();
	}

	@Override
	public PerformanceOverall getPerformance(int week, int month) {
		
		return repo.getPerformanceThisMonth(month);
	}

	@Override
	public void updatePerformance(PerformanceOverall list) {
		
		repo.save(list);
		
	}

}
