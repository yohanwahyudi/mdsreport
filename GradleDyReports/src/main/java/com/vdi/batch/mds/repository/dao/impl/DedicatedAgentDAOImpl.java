package com.vdi.batch.mds.repository.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.DedicatedAgentSummaryRepository;
import com.vdi.batch.mds.repository.DedicatedAgentTeamRepository;
import com.vdi.batch.mds.repository.dao.DedicatedAgentDAOService;
import com.vdi.model.performance.DedicatedAgentSummary;
import com.vdi.model.performance.DedicatedAgentTeam;

@Transactional
@Repository("dedicatedDAO")
public class DedicatedAgentDAOImpl implements DedicatedAgentDAOService{
	
	@Autowired
	private DedicatedAgentSummaryRepository repoSummary;
	
	@Autowired
	private DedicatedAgentTeamRepository repoTeam;
	
	@Autowired
	private EntityManager em;

	@Override
	public DedicatedAgentSummary getPreviousMonthSummary(String category) {
		
		return repoSummary.getPreviousMonthSummary(category);
	}

	@Override
	public List<DedicatedAgentTeam> getPreviousMonthAgentTeam(String category) {
		
		return repoTeam.getPreviousMonthAgentTeam(category);
	}
	
	@Override
	public List<Object[]> getPreviousMonthTeam(String category) {
		
		return repoTeam.getPreviousMonthTeam(category);
	}

	@Override
	public <T> void addAllCollections(Collection<T> col) {
		
		Collection<DedicatedAgentSummary> dedicatedAgentSummary = (Collection<DedicatedAgentSummary>) col;
		
		repoSummary.saveAll(dedicatedAgentSummary);
		
	}

	@Override
	public <T> void deleteEntity(Class<T> obj) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<T> query = (CriteriaDelete<T>) cb.createCriteriaDelete(obj);
		
		query.from(obj);
		em.createQuery(query).executeUpdate();
	}

}
