package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.WeeklyURPefAgentRepository;
import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.model.performance.PerformanceAgent;

@Transactional
@Repository("weeklyURPerfAgentDAO")
public class WeeklyURPerfAgentDAOImpl implements PerfAgentDAOService{
	
	@Autowired
	private WeeklyURPefAgentRepository repo;

	@Override
	public List<Object[]> getAgentTicket() {
		
		return null;
	}

	@Override
	public List<Object[]> getAgentTicket(int week, int month) {
		
		return repo.getAgentTicket(week, month);
	}

	@Override
	public void insertPerformance(List<PerformanceAgent> listAgent) {
		
		repo.saveAll(listAgent);
		
	}

	@Override
	public List<PerformanceAgent> getPerformance() {
		
		return null;
	}

	@Override
	public List<PerformanceAgent> getPerformance(int week, int month) {
		
		return repo.getPerformanceThisWeek(week, month);
	}

	@Override
	public void updatePerformance(List<PerformanceAgent> agents) {
		
		repo.saveAll(agents);
	}
	
	

}
