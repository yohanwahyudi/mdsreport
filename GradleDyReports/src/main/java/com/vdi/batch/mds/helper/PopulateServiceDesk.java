package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.StagingServiceDeskDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.model.staging.StagingServiceDesk;

@Component
public class PopulateServiceDesk {

	@Autowired
	@Qualifier("stagingServiceDeskDAO")
	private StagingServiceDeskDAOService stagingDAO;

	@Autowired
	@Qualifier("serviceDeskDataLoaderService")
	private ItopMDSDataLoaderService load;

	@Autowired
	@Qualifier("mailService")
	private MailService mailService;

	private List<StagingServiceDesk> stagingList;


	@SuppressWarnings("unchecked")
	public void addToStaging() throws Throwable {
		
		stagingList = new ArrayList<StagingServiceDesk>();
		stagingList = load.getStagingAllByURL();

		if (stagingList != null && stagingList.size() > 0) {
			stagingDAO.addAll(stagingList);
		}		
		
	}


	public void populate() throws Throwable {
		
		// delete and reset sequence for staging table
		stagingDAO.deleteEntity();

		// add new incident list to staging
		addToStaging();


	}

}
