package com.vdi.batch.mds.repository.dao;

import java.util.List;

public interface MonthlyProblemChangeDAOService {
	
	public List<Object[]> getProblemSummary();
	public List<Object[]> getProblemAchievementByTeam();
	public List<Object[]> getProblemAchievementByAgent();
	public List<Object[]> getProblemList();
	
	public List<Object[]> getChangeSummary();
	public List<Object[]> getChangeAchievementByTeam();
	public List<Object[]> getChangeAchievementByAgent();
	public List<Object[]> getChangeList();

}
