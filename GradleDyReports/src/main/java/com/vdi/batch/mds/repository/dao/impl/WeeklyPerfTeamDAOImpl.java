package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.WeeklyPerfTeamRepository;
import com.vdi.batch.mds.repository.dao.PerfTeamDAOService;
import com.vdi.model.performance.PerformanceTeam;

@Transactional
@Repository("weeklyPerfTeamDAO")
public class WeeklyPerfTeamDAOImpl implements PerfTeamDAOService {
	
	@Autowired
	private WeeklyPerfTeamRepository repo;

	@Override
	public List<Object[]> getTeamTicketByDivision() {
		return repo.getTeamTicketByDivision();
	}

	@Override
	public void insertPerformance(List<PerformanceTeam> team) {
		repo.saveAll(team);
	}

	@Override
	public List<PerformanceTeam> getPerformance() {
		return repo.getPerformanceThisWeek();
	}

	@Override
	public void updatePerformance(List<PerformanceTeam> teams) {
		repo.saveAll(teams);		
	}

	@Override
	public List<Object[]> getTeamTicketByDivision(int week, int month) {
		return repo.getTeamTicketByDivision(week, month);
	}

	@Override
	public List<PerformanceTeam> getPerformance(int week, int month) {
		return repo.getPerformanceThisWeek(week, month);
	}

}
