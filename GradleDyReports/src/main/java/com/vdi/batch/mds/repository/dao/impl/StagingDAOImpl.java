package com.vdi.batch.mds.repository.dao.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.StagingRepository;
import com.vdi.batch.mds.repository.dao.StagingIncidentDAOService;
import com.vdi.model.staging.Staging;

@Transactional
@Repository("stagingDAORepo")
public class StagingDAOImpl implements StagingIncidentDAOService{
	
	@Autowired
	private StagingRepository stagingRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(Object obj) {
		stagingRepository.save((Staging)obj);
	}
	
	@Override
	public void addAll(Collection stagingCol) {
		Collection<Staging> col = stagingCol;
		
		for(Staging staging : col) {
			stagingRepository.save(staging);
		}
	}
	
	public void deleteAll() {
		stagingRepository.deleteAll();
	}
	
	@Override
	public void deleteEntity() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaDelete<Staging> query = (CriteriaDelete<Staging>) cb.createCriteriaDelete(Staging.class);
		
		query.from(Staging.class);
		em.createQuery(query).executeUpdate();		
		
	}

	@Override
	public void merge(Object obj) {
		
	}

	@Override
	public List getDataForInsert() {
		
		List temp = stagingRepository.getDataForInsert();
		
		Staging incident = (Staging)temp.get(0);
		for(Field field : incident.getClass().getDeclaredFields()) {
			field.setAccessible(true);
						
			try {
				String name = field.getName();
				Object value = field.get(incident);
				
				System.out.println("name: "+name+" value: "+value+" type: "+field.getType());
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return stagingRepository.getDataForInsert();
	}

	@Override
	public void resetSequenceTo1() {
		stagingRepository.resetSequenceTo1();		
	}

	@Override
	public void disableSafeUpdates() {
		stagingRepository.disableSafeUpdates();
		
	}

	@Override
	public void enableSafeUpdates() {
		stagingRepository.enableSafeUpdates();
		
	}

	@Override
	public void updateIncidentTable() {
		stagingRepository.updateIncidentTable();
		
	}

	@Override
	public void insertToIncidentTable() {
		stagingRepository.insertToIncidentTable();
		
	}

	@Override
	public List<Object[]> getUnregisteredAgent() {
		return stagingRepository.getUnregisteredAgent();
	}

}
