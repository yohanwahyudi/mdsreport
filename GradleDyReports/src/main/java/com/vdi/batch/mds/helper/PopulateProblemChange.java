package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.MonthlyAppTeamDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.model.Change;
import com.vdi.model.Problem;

@Component
public class PopulateProblemChange {

	private final Logger logger = LogManager.getLogger(PopulateProblemChange.class);

	@Autowired
	@Qualifier("monthlyAppTeamDAO")
	private MonthlyAppTeamDAOService repo;

	@Autowired
	@Qualifier("problemDataLoaderService")
	private ItopMDSDataLoaderService loadProblem;

	@Autowired
	@Qualifier("changeDataLoaderService")
	private ItopMDSDataLoaderService loadChange;

	private List<Problem> problemList;
	private List<Change> changeList;

	private void addToStagingProblem() {
		
		logger.info("Clear problem...");
		repo.deleteEntity(Problem.class);
		
		logger.info("Add to problem...");
		problemList = new ArrayList<Problem>();
		problemList = castList(Problem.class, loadProblem.getStagingAllByURL());
		repo.addAll(problemList);
//		repo.addAll(loadProblem.getStagingAllByURL());

//		problemList = new ArrayList<Problem>();
//		problemList = castList(Problem.class, loadProblem.getStagingAllByURL());
//
//		if (problemList != null && problemList.size() > 0) {
//			repo.addAll(problemList);
//		}

	}

	private void addToStagingChange() {
		
		logger.info("Clear change...");
		repo.deleteEntity(Change.class);

		logger.info("Add to Change...");
		changeList = new ArrayList<Change>();
		changeList = castList(Change.class, loadChange.getStagingAllByURL());	
		repo.addAll(changeList);
		
//		repo.addAll(loadChange.getStagingAllByURL());
		
//		changeList = new ArrayList<Change>();
//		changeList = castList(Change.class, loadChange.getStagingAllByURL());		
//
//		if (changeList != null && changeList.size() > 0) {
//			repo.addAll(changeList);
//		}
	}

	private <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c) {
			r.add(clazz.cast(o));
		}

		return r;
	}
	
	public void populate() {
		addToStagingProblem();
		addToStagingChange();
	}
	
	public int getProblemDataSize() {
		
		int size=0;
		try {
			size = problemList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return size;
	}
	
	public int getChangeDataSize() {
		
		int size=0;
		try {
			size = changeList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return size;
	}

}
