package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.Incident;

public interface OpenIncidentDAOService {
	
	public List<Incident> getOpenedIncident();
	public void saveAll(Iterable<Incident> list);
	
	Iterable<Incident> findByRefList(Iterable<String> refList);

}
