package com.vdi.batch.mds.repository.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.MonthlyAppTeamRepository;
import com.vdi.batch.mds.repository.dao.MonthlyAppTeamDAOService;

@Transactional
@Repository("monthlyAppTeamDAO")
public class MonthlyAppTeamDAOImpl implements MonthlyAppTeamDAOService{
	
	@Autowired
	private MonthlyAppTeamRepository repo;
	
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getAgentPerformance() {
		
		return repo.getAgentPerformance();
	}

	@Override
	public List<Object[]> getTeamPerformance() {
		
		return repo.getTeamPerformance();
	}
	
	@Override
	public void addAll(Collection stagingCol) {
		repo.saveAll(stagingCol);
	}
	
	@Override
	public <T> void deleteEntity(Class<T> obj) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<T> query = (CriteriaDelete<T>) cb.createCriteriaDelete(obj);
		
		query.from((Class<T>) obj);
		em.createQuery(query).executeUpdate();
	}

}
