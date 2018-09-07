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

import com.vdi.batch.mds.repository.StagingServiceDeskRepository;
import com.vdi.batch.mds.repository.dao.StagingServiceDeskDAOService;
import com.vdi.model.staging.StagingServiceDesk;

@Transactional
@Repository("stagingServiceDeskDAO")
public class StagingServiceDeskDAOImpl implements StagingServiceDeskDAOService{

	@Autowired
	StagingServiceDeskRepository repo;
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void add(Object obj) {
		repo.save((StagingServiceDesk)obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void addAll(Collection<T> col) {		
		repo.saveAll((List<StagingServiceDesk>) col);
	}

	@Override
	public void deleteEntity() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<StagingServiceDesk> queryDelete = cb.createCriteriaDelete(StagingServiceDesk.class);
		
		queryDelete.from(StagingServiceDesk.class);
		
		em.createQuery(queryDelete).executeUpdate();
		
	}

	@Override
	public void merge(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Object[]> getUnregisteredAgent() {
		return repo.getUnregisteredAgent();
	}

	
}
