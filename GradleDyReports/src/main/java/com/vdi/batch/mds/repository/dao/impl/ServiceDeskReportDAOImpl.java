package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.StagingServiceDeskRepository;
import com.vdi.batch.mds.repository.dao.ServiceDeskReportDAO;
import com.vdi.model.staging.StagingServiceDesk;

@Transactional
@Repository("serviceDeskReportDAO")
public class ServiceDeskReportDAOImpl implements ServiceDeskReportDAO{

	@Autowired
	private StagingServiceDeskRepository repo;
	
	@Override
	public List<StagingServiceDesk> getAllIncidentByWeek(int month, int week) {
		
		return repo.getAllIncidentByWeek(month, week);
	}

	@Override
	public List<StagingServiceDesk> getAllIncidentByMonth(int month) {
		
		return repo.getAllIncidentByMonth(month);
	}

	
	
}
