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

import com.vdi.batch.mds.repository.StagingUReqYTDRepository;
import com.vdi.batch.mds.repository.dao.StagingUReqYTDDAOService;
import com.vdi.batch.mds.repository.dao.StagingUserRequestDAOService;
import com.vdi.model.staging.StagingUserRequest;
import com.vdi.model.staging.StagingUserRequestYTD;

@Transactional
@Repository("stagingUReqYTDDAO")
public class StagingUReqYTDDaoImpl implements StagingUReqYTDDAOService{
	
	@Autowired
	private StagingUReqYTDRepository repo;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Object obj) {
		
		repo.save((StagingUserRequestYTD) obj);
		
	}

	@Override
	public <T> void addAll(Collection<T> col) {
		
		repo.saveAll((List<StagingUserRequestYTD>) col);
	}

	@Override
	public void deleteEntity() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<StagingUserRequestYTD> delete = cb.createCriteriaDelete(StagingUserRequestYTD.class);	
		
		delete.from(StagingUserRequestYTD.class);
		
		em.createQuery(delete).executeUpdate();
		
	}

	
	

	
	
}
