package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.OpenedIncidentRepository;
import com.vdi.batch.mds.repository.dao.OpenIncidentDAOService;
import com.vdi.model.Incident;

@Service("openIncidentDAO")
public class OpenedIncidentDaoImpl implements OpenIncidentDAOService{
	
	@Autowired
	private OpenedIncidentRepository openIncidentRepository;

	@Override
	public List<Incident> getOpenedIncident() {
		return openIncidentRepository.getOpenedIncident();
	}
	
	@Override
	public void saveAll(Iterable<Incident> list) {
		openIncidentRepository.saveAll(list);
	}
	
	@Override
	public Iterable<Incident> findByRefList(Iterable<String> refList){
		return openIncidentRepository.findByRefList(refList);
	}

}
