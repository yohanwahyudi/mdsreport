package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.IncidentDAOService;
import com.vdi.batch.mds.repository.dao.StagingIncidentDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.staging.Staging;

@Component
public class PopulateIncident {
	
	private static Logger logger = LogManager.getLogger(PopulateIncident.class);

	@Autowired
	@Qualifier("stagingDAORepo")
	private StagingIncidentDAOService stagingDAO;

	@Autowired
	@Qualifier("incidentDAO")
	private IncidentDAOService incidentDAO;

	@Autowired
	@Qualifier("itopMDSDataLoaderService")
	private ItopMDSDataLoaderService loadIncidentFromURL;

	@Autowired
	@Qualifier("mailService")
	private MailService mailService;

	@Autowired
	private AppConfig appConfig;

	private List<Staging> stagingList;

	private List<Object[]> unregisteredAgentList;

	@SuppressWarnings("unchecked")
	public void addToStaging() {
		stagingList = new ArrayList<Staging>();
		stagingList = loadIncidentFromURL.getStagingAllByURL();

		if (stagingList != null && stagingList.size() > 0) {
			stagingDAO.addAll(stagingList);
		}
	}

	public void addToMaster() throws Throwable {
		stagingDAO.updateIncidentTable();

		stagingDAO.insertToIncidentTable();
		
		unregisteredAgentList = new ArrayList<Object[]>();
		unregisteredAgentList = stagingDAO.getUnregisteredAgent();
		
		if(unregisteredAgentList.size() > 0) {
			StringBuffer sb = new StringBuffer();

			for (Object[] obj : unregisteredAgentList) {
				sb.append(obj[0]).append(" - ").append(obj[1]).append("\n");
			}
			String content = sb.toString();

			sendMail("Agent not Registered " + content);
			
			logger.debug("Agent not registered "+content);

			throw new Exception("Agent not Registered " + content);
		}

		
		
//		if (unregisteredAgentList.size() < 1) {
//			stagingDAO.insertToIncidentTable();
//		} else {
//
//			StringBuffer sb = new StringBuffer();
//
//			for (Object[] obj : unregisteredAgentList) {
//				sb.append(obj[0]).append(" - ").append(obj[1]).append("\n");
//			}
//			String content = sb.toString();
//
//			sendMail("Agent not Registered " + content);
//			
//			logger.debug("Agent not registered "+content);
//
//			throw new Exception("Agent not Registered " + content);
//		}

	}

	public void sendMail(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorMsg", content);

		String[] toStrArr = appConfig.getMailSlaMgr();

		mailService.sendEmail(map, "fm_unregisteredAgent.txt", toStrArr, "Weekly/ Monthly Report not Running");
	}

	public void populate() throws Throwable {
		
		// delete and reset sequence for staging table
		stagingDAO.deleteEntity();

		// add new incident list to staging
		addToStaging();

		// add to table incident
		addToMaster();

	}

}
