package com.vdi.batch.mds.repository.dao.impl.mtd;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdPerfTeamRepository;
import com.vdi.batch.mds.repository.dao.PerfTeamDAOService;
import com.vdi.model.performance.PerformanceTeam;

@Transactional
@Service("mtdPerfTeamDao")
public class MtdPerfTeamDAOImpl implements PerfTeamDAOService{
	
	@Autowired
	private MtdPerfTeamRepository mtdTeamRepository;

	@Override
	public List<Object[]> getTeamTicketByDivision() {

		return mtdTeamRepository.getTeamTicketByDivision();
	}

	@Override
	public List<Object[]> getTeamTicketByDivision(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPerformance(List<PerformanceTeam> team) {
		
		mtdTeamRepository.saveAll(team);
		
	}

	@Override
	public List<PerformanceTeam> getPerformance() {

		return mtdTeamRepository.getExistingPerformance();
	}

	@Override
	public List<PerformanceTeam> getPerformance(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePerformance(List<PerformanceTeam> teams) {

		mtdTeamRepository.saveAll(teams);
		
	}

}
