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

import com.vdi.batch.mds.repository.ServiceDeskRepository;
import com.vdi.batch.mds.repository.dao.ServiceDeskDAOService;
import com.vdi.model.ServiceDesk;

@Transactional
@Repository("serviceDeskDAO")
public class ServiceDeskDAOImpl implements ServiceDeskDAOService{

	@Autowired
	ServiceDeskRepository repo;
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public void add(Object obj) {
		repo.save((ServiceDesk) obj);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void addAll(Collection<T> col) {		
		repo.saveAll((List<ServiceDesk>) col);
		
	}

	@Override
	public void deleteEntity() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<ServiceDesk> queryDelete = cb.createCriteriaDelete(ServiceDesk.class);
		
		queryDelete.from(ServiceDesk.class);
		
		em.createQuery(queryDelete).executeUpdate();
		
	}

	@Override
	public void merge(Object obj) {
		// TODO Auto-generated method stub
		
	}

}
