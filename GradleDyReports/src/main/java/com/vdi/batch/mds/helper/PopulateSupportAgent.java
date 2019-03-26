package com.vdi.batch.mds.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.StagingAgentDAOService;
import com.vdi.batch.mds.repository.dao.SupportAgentDAOService;
import com.vdi.model.Agent;
import com.vdi.model.staging.StagingAgent;
import com.vdi.tools.ParseCSVService;

@Component
public class PopulateSupportAgent {
	
	Logger logger = LogManager.getLogger(PopulateSupportAgent.class);

	@Autowired
	@Qualifier("parseCSVService")
	private ParseCSVService parseCSV;

	@Autowired
	@Qualifier("stagingAgentDAO")
	private StagingAgentDAOService stagingDAO;
	
	@Autowired
	@Qualifier("supportAgentDAO")
	private SupportAgentDAOService agentDAO;

	private List<Agent> agentForInsert;

	public void addToStaging(List<List<String>> rowList) {
		
		for (List<String> row : rowList) {			
			StagingAgent agent = new StagingAgent();
			if(row!=null && !row.isEmpty()) {
//				logger.info(row);				
				agent.setName(row.get(0));
				agent.setTeam_name(row.get(1));
				agent.setDivision(row.get(2));
				agent.setOrganization_name(row.get(3));
				agent.setEmail(row.get(4));
				agent.setResource(row.get(5));
				agent.setIsActive(Integer.parseInt(row.get(6)));
				
				stagingDAO.add(agent);
			}
		}
		
		stagingDAO.deleteEntity();
	}

	public void addToMaster() {		
		stagingDAO.updateAgentTableMaster();
		stagingDAO.insertAgentTableMaster();
		
	}

	public void populate() throws IllegalArgumentException, IllegalAccessException {

		// truncate table
		stagingDAO.deleteEntity();
		stagingDAO.resetSequenceTo1();
		
		List<List<String>> rows = parseCSV.readRows();

		if (rows != null && rows.size() > 0) {
			addToStaging(rows);
			addToMaster();

		}

	}

	public void debugDataForInsert(List<StagingAgent> agentList)
			throws IllegalArgumentException, IllegalAccessException {
		for (StagingAgent staging : agentList) {
			for (Field field : staging.getClass().getDeclaredFields()) {
				field.setAccessible(true);

				String name = field.getName();
				Object value = field.get(staging);

				System.out.println("name: " + name + " value: " + value);

			}
		}
	}

	public List<Agent> getAgentForInsert() {
		return agentForInsert;
	}

	public void setAgentForInsert(List<Agent> agentForInsert) {
		this.agentForInsert = agentForInsert;
	}

}
