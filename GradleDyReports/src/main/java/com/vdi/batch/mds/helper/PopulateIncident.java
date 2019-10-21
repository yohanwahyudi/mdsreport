package com.vdi.batch.mds.helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.AgentsService;
import com.vdi.batch.mds.repository.dao.IncidentDAOService;
import com.vdi.batch.mds.repository.dao.StagingIncidentDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.Agent;
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
	private AgentsService agentService;

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
		
		try {
			logger.info("incident staging list size: "+stagingList.size());
		} catch (Exception e) {
			// bypass
		}

		if (stagingList != null && stagingList.size() > 0) {
			stagingDAO.addAll(stagingList);
		}
	}

	public void addToMaster() {
		stagingDAO.updateIncidentTable();
		stagingDAO.insertToIncidentTable();
		
		insertAndCheckUnregisteredAgent();

		stagingDAO.syncDeleteMaster();
		stagingDAO.syncDeletePrevMonth();
		
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

	public void sendMail(List<Agent> agents) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agents", agents);

		String[] toStrArr = appConfig.getMailSlaMgr();

		mailService.sendEmail(map, "fm_agents_added.txt", toStrArr, "MDS Report - Agents Added");
	}

	public void populate() {
		
		logger.info("start populate incident....");
		
		// delete and reset sequence for staging table
		stagingDAO.deleteEntity();

		// add new incident list to staging
		addToStaging();

		// add to table incident
		addToMaster();
		
		logger.info("finish populate incident....");

	}
	
	public void insertAndCheckUnregisteredAgent() {
		
		unregisteredAgentList = new ArrayList<Object[]>();
		unregisteredAgentList = stagingDAO.getUnregisteredAgent();
		
		
		if(unregisteredAgentList.size() > 0) {
			List<Agent> agents = new ArrayList<Agent>();
			for (Object[] obj : unregisteredAgentList) {
				
				String name		= (String) obj[0];
				String team		= (String) obj[1];
				String email	= (String) obj[2];
				String org		= (String) obj[3];
				
				Agent agent = new Agent();
				
				if(org.equalsIgnoreCase("DCU")) {
					agent.setDivision("ITMS-DCU");
					agent.setEmail(email);
					agent.setIsActive(1);
					agent.setName(name);
					agent.setOrganization_name(org);
					agent.setResource("Non Dedicated");
					agent.setTeam_name("DCU");
					agent.setVersion(0);
					agent.setCreatedDate(LocalDateTime.now());
					agent.setUpdatedDate(LocalDateTime.now());
				} else {
					agent.setDivision("");
					agent.setEmail(email);
					agent.setIsActive(1);
					agent.setName(name);
					agent.setOrganization_name(org);
					agent.setResource("");
					agent.setTeam_name("");
					agent.setVersion(0);
					agent.setCreatedDate(LocalDateTime.now());
					agent.setUpdatedDate(LocalDateTime.now());
				}
				
				agents.add(agent);
			}
			
			agentService.saveList(agents);
			
			sendMail(agents);

		}
		
	}

}
