package com.vdi.batch.mds.repository.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.StagingAgentRepository;
import com.vdi.batch.mds.repository.dao.StagingAgentDAOService;
import com.vdi.model.staging.StagingAgent;

@Transactional
@Repository("stagingAgentDAO")
public class StagingAgentDAOImpl implements StagingAgentDAOService{

	@Autowired
	private StagingAgentRepository repo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Object obj) {
		repo.save((StagingAgent)obj);
	}

	@Override
	public <T> void addAll(Collection<T> col) {
		Collection<StagingAgent> agentCol = (Collection<StagingAgent>) col;
		for(StagingAgent stAgent : agentCol) {
			repo.save(stAgent);
		}
	}

	@Override
	public void deleteEntity() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<StagingAgent> delete = (CriteriaDelete<StagingAgent>) cb.createCriteriaDelete(StagingAgent.class);
		
		delete.from(StagingAgent.class);
		
		em.createQuery(delete).executeUpdate();
	}

	@Override
	public void merge(Object obj) {
		
	}

	@Override
	public List<StagingAgent> getDataForInsert() {
		return repo.getDataForInsert();
	}

	@Override
	public void resetSequenceTo1() {
		repo.resetSequenceTo1();
		
	}

	@Override
	public void disableSafeUpdates() {
		repo.disableSafeUpdates();
		
	}

	@Override
	public void enableSafeUpdates() {
		repo.enableSafeUpdates();
		
	}
	
	
	
}
