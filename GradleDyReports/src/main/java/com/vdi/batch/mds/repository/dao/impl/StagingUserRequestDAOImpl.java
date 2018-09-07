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

import com.vdi.batch.mds.repository.dao.StagingUserRequestDAOService;
import com.vdi.batch.mds.repository.StagingUserRequestRepository;
import com.vdi.model.staging.StagingUserRequest;

@Transactional
@Repository("stagingUserRequestDAO")
public class StagingUserRequestDAOImpl implements StagingUserRequestDAOService{

	@Autowired
	private StagingUserRequestRepository repo;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(Object obj) {
		repo.save((StagingUserRequest) obj);
		
	}

	@Override
	public <T> void addAll(Collection<T> col) {
		repo.saveAll((List<StagingUserRequest>) col);
		
	}

	@Override
	public void deleteEntity() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<StagingUserRequest> delete = cb.createCriteriaDelete(StagingUserRequest.class);
		
		delete.from(StagingUserRequest.class);
		
		em.createQuery(delete).executeUpdate();
		
	}

	@Override
	public void merge(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<StagingUserRequest> getAllIncidentByWeek(int month, int week) {
		
		return repo.getAllIncidentByWeek(month, week);
	}

	@Override
	public List<StagingUserRequest> getAllIncidentByMonth(int month) {
		
		return repo.getAllIncidentByMonth(month);
	}

}
