package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import com.vdi.batch.mds.repository.PerformanceTeamRepository;
import com.vdi.batch.mds.repository.dao.PerformanceTeamService;
import com.vdi.model.performance.PerformanceTeam;

public class PerformanceTeamServiceImpl implements PerformanceTeamService{
	
	private PerformanceTeamRepository performanceTeamRepository;

	@Override
	public void save(PerformanceTeam team) {
		
		performanceTeamRepository.save(team);
	}

	@Override
	public void saveList(List<PerformanceTeam> teams) {
		
		performanceTeamRepository.saveAll(teams);
	}

	@Override
	public void delete(PerformanceTeam team) {
		
		performanceTeamRepository.delete(team);
	}

	@Override
	public void deleteList(List<PerformanceTeam> teams) {
		
		performanceTeamRepository.deleteAll(teams);
	}

	@Override
	public void deleteUnassignedTeam() {
		performanceTeamRepository.deleteUnassignedTeam();
	}
	
	

}
