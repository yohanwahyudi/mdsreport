package com.vdi.batch.mds.repository.dao.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.IncidentRepository;
import com.vdi.batch.mds.repository.dao.IncidentDAOService;
import com.vdi.model.Incident;

@Transactional
@Repository("incidentDAO")
public class IncidentDAOImpl implements IncidentDAOService{
	
	@Autowired
	private IncidentRepository incidentRepository;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Object obj) {
		
		incidentRepository.save((Incident)obj);
		
	}

	@Override
	public <T> void addAll(Collection<T> col) {
		
		Collection<Incident> incidentList = (Collection<Incident>) col;
		for(Incident incident : incidentList) {
			incidentRepository.save(incident);
		}
	}

	@Override
	public void deleteEntity() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Incident> queryDelete = cb.createCriteriaDelete(Incident.class);
		
		queryDelete.from(Incident.class);
		em.createQuery(queryDelete).executeUpdate();
	}

	@Override
	public void merge(Object obj) {
		// TODO Auto-generated method stub
		
	}

}
