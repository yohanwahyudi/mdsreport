package com.vdi.batch.mds.helper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.AgentsService;
import com.vdi.batch.mds.repository.dao.IncidentDAOService;
import com.vdi.batch.mds.repository.dao.OpenIncidentDAOService;
import com.vdi.batch.mds.repository.dao.StagingIncidentDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.PropertyNames;
import com.vdi.model.Agent;
import com.vdi.model.Incident;
import com.vdi.model.IncidentOpen;
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
	@Qualifier("incidentOpenDLService")
	private ItopMDSDataLoaderService loadOpenIncident;
	
	@Autowired
	private AgentsService agentService;

	@Autowired
	@Qualifier("mailService")
	private MailService mailService;

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private OpenIncidentDAOService openIncidentService;

	private List<Staging> stagingList;

	private List<Object[]> unregisteredAgentList;

	@SuppressWarnings("unchecked")
	private void addToStaging() {
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

	private void addToMaster() {
		stagingDAO.updateIncidentTable();
		stagingDAO.insertToIncidentTable();
		
		insertAndCheckUnregisteredAgent();

		stagingDAO.syncDeleteMaster();
		stagingDAO.syncDeletePrevMonth();
		
		updateOpenedIncident();
		
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

	private void sendMail(List<Agent> agents) {
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
	
	private void insertAndCheckUnregisteredAgent() {
		
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
	
	//should be private
	private void updateOpenedIncident() {
		
		List<Incident> localList = openIncidentService.getOpenedIncident();
		List<IncidentOpen> newList = loadOpenIncident.getStagingAllByURL();
		
		if((newList!=null && newList.size()>0 ) && (localList!=null && localList.size()>0 )) {
			Map<String, Iterable<Incident>> map = getOpenedIncidentForUpdate(newList, localList);
			
			if(map!=null && !map.isEmpty()) {
				if(map.get("diff")!=null) {
					List<Incident> forUpdateDiff = updateDiffIncident(map.get("diff"));
					openIncidentService.saveAll(forUpdateDiff);
				}
				
				if(map.get("similar")!=null) {
					Iterable<Incident> similar = map.get("similar");			
					updateSimilarIncident(similar, newList);
				}
			}
		}
				
	}
	
	private Map<String, Iterable<Incident>> getOpenedIncidentForUpdate(List<IncidentOpen> newList, List<Incident> localList){
		
		Collection<String> newRefNo = new HashSet<String>();
		Collection<String> localRefNo = new HashSet<String>();
		
		for(IncidentOpen newIncident : newList) {
			newRefNo.add(newIncident.getRef());
		}
		
		for(Incident localIncident : localList) {
			localRefNo.add(localIncident.getRef());
		}
		
		Collection<String> similar = new HashSet<String>(newRefNo);
		Collection<String> diff = new HashSet<String>();
		diff.addAll(newRefNo);
		diff.addAll(localRefNo);
		
		similar.retainAll(localRefNo);
		diff.removeAll(newRefNo); 
		
//		System.out.println("localRefNo: " +localRefNo);
//		System.out.println("diff: "+ diff);
//		System.out.println("diff: "+ diff.size());
//		System.out.println("similar: "+similar);
		
		Map<String, Iterable<Incident>> map = new HashMap<>();
		if(diff!=null && diff.size()>0) {
			map.put("diff", openIncidentService.findByRefList(diff));
		}
		
		if(similar!=null && similar.size()>0) {
			map.put("similar", openIncidentService.findByRefList(similar));
		}
		
		return map;
	}
	
	private List<Incident> updateDiffIncident(Iterable<Incident> diff) {
		
		List<Incident> temp = new ArrayList<>();
		
		Iterator<Incident> iter = diff.iterator();
		while(iter.hasNext()) {
			Incident incident = new Incident();
			incident = iter.next();
			incident.setAgent_fullname("");
			incident.setAgent("");
			incident.setAgent_lastname("");
			incident.setStatus(PropertyNames.CLOSED);
			
			temp.add(incident);
		}
		
		return temp;
	}
	
	private void updateSimilarIncident(Iterable<Incident> similar, List<IncidentOpen> newList) {
		
		Map<String, IncidentOpen> mapNewList = new HashMap<>();
		for(IncidentOpen open : newList) {
			mapNewList.put(open.getRef(), open);
		}
		
		List<Incident> temp = new ArrayList<Incident>();
		
		List<String> refList = new ArrayList<>();
		for(IncidentOpen a : newList) {
			refList.add(a.getRef());
		}
		
		Iterator<Incident> iter = similar.iterator();
		while(iter.hasNext()) {
			Incident localIncident = new Incident();
			localIncident = iter.next();
			
			String ref = localIncident.getRef();
			if(refList.contains(ref)) {
				
				
				IncidentOpen open = new IncidentOpen();
				open = mapNewList.get(ref);
				
				localIncident.setAgent(open.getAgent());
				localIncident.setAgent_fullname(open.getAgentFullname());
				localIncident.setAgent_lastname(open.getAgentLastName());
				localIncident.setStatus(open.getStatus());
				localIncident.setIncident_organization_name(open.getOrganizationFullname());
				localIncident.setPriority(open.getPriority());
				localIncident.setPending_reason(open.getPendingReason());
				
				temp.add(localIncident);
			}
		}
		
		openIncidentService.saveAll(temp);
		
	}

}
