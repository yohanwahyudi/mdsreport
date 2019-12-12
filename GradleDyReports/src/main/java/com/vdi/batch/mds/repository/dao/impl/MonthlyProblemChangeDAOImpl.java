package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MonthlyChangeRepository;
import com.vdi.batch.mds.repository.MonthlyProblemRepository;
import com.vdi.batch.mds.repository.dao.MonthlyProblemChangeDAOService;
import com.vdi.model.Change;
import com.vdi.model.Problem;

@Transactional
@Service("monthlyProblemChangeDAO")
public class MonthlyProblemChangeDAOImpl implements MonthlyProblemChangeDAOService{
	
	@Autowired
	private MonthlyProblemRepository problemRepo;
	
	@Autowired
	private MonthlyChangeRepository changeRepo;

	@Override
	public List<Object[]> getProblemSummary() {
		
		return problemRepo.getProblemSummary();
	}

	@Override
	public List<Object[]> getProblemAchievementByTeam() {
		
		return problemRepo.getProblemAchievementByTeam();
	}

	@Override
	public List<Object[]> getProblemAchievementByAgent() {
		
		return problemRepo.getProblemAchievementByAgent();
	}

	@Override
	public List<Object[]> getLastMonthProblemList() {
		
		return problemRepo.getLastMonthProblemList();
	}

	@Override
	public List<Object[]> getChangeSummary() {
		
		return changeRepo.getChangeSummary();
	}

	@Override
	public List<Object[]> getChangeAchievementByTeam() {
		
		return changeRepo.getChangeAchievementByTeam();
	}

	@Override
	public List<Object[]> getChangeAchievementByAgent() {
		
		return changeRepo.getChangeAchievementByAgent();
	}

	@Override
	public List<Object[]> getLastMonthChangeList() {
		
		return changeRepo.getLastMonthChangeList();
	}

	@Override
	public List<Object[]> getAllProblemSummary() {
		return problemRepo.getAllProblemSummary();
	}

	@Override
	public List<Object[]> getAllOpenedProblemByTeamAgent() {
		return problemRepo.getAllOpenedProblemByTeamAgent();
	}

	@Override
	public List<Problem> getAllOpenedProblemList() {
		return problemRepo.getAllOpenedProblemList();
	}

	@Override
	public List<Object[]> getAllChangeSummary() {
		return changeRepo.getAllChangeSummary();
	}

	@Override
	public List<Object[]> getAllOpenedChangeByTeamAgent() {
		return changeRepo.getAllOpenedChangeByTeamAgent();
	}

	@Override
	public List<Change> getAllOpenedChangeList() {
		return changeRepo.getAllOpenedChangeList();
	}

}
