package com.vdi.batch.mds.repository.dao.impl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.SupportAgentRepository;
import com.vdi.batch.mds.repository.dao.BaseDAOService;
import com.vdi.batch.mds.repository.dao.SupportAgentDAOService;
import com.vdi.model.Agent;

@Transactional
@Repository("supportAgentDAO")
public class SupportAgentDAOImpl implements SupportAgentDAOService{
	
	@Autowired
	private SupportAgentRepository repository;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Object obj) {
		
		repository.save((Agent)obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void addAll(Collection<T> col) {
		Collection<Agent> cols = (Collection<Agent>) col;
		
		for(Agent agent:cols) {
			repository.save(agent);
		}
	}

	@Override
	public void deleteEntity() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaDelete<Agent> truncate = (CriteriaDelete<Agent>) cb.createCriteriaDelete(Agent.class);
		
		truncate.from(Agent.class);
		em.createQuery(truncate).executeUpdate();
		
	}
	
	@Override
	public void merge(Object obj) {
//		get session for hibernate		
//		Session session = em.unwrap(Session.class);
//		session.saveOrUpdate((SupportAgent)obj);
		
		em.merge((Agent)obj);
		
	}
}
