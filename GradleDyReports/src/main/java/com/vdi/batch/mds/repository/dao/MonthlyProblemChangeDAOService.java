package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.Change;
import com.vdi.model.Problem;

public interface MonthlyProblemChangeDAOService {
	
	public List<Object[]> getProblemSummary();
	public List<Object[]> getProblemAchievementByTeam();
	public List<Object[]> getProblemAchievementByAgent();
	public List<Object[]> getLastMonthProblemList();
	public List<Object[]> getAllProblemSummary();
	public List<Object[]> getAllOpenedProblemByTeamAgent();
	public List<Problem> getAllOpenedProblemList();
	
	public List<Object[]> getChangeSummary();
	public List<Object[]> getChangeAchievementByTeam();
	public List<Object[]> getChangeAchievementByAgent();
	public List<Object[]> getLastMonthChangeList();
	public List<Object[]> getAllChangeSummary();
	public List<Object[]> getAllOpenedChangeByTeamAgent();
	public List<Change> getAllOpenedChangeList();

}
