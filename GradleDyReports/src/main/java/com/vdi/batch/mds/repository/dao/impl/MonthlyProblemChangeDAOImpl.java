package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MonthlyChangeRepository;
import com.vdi.batch.mds.repository.MonthlyProblemRepository;
import com.vdi.batch.mds.repository.dao.MonthlyProblemChangeDAOService;

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
	public List<Object[]> getProblemList() {
		
		return problemRepo.getProblemList();
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
	public List<Object[]> getChangeList() {
		
		return changeRepo.getChangeList();
	}

}
