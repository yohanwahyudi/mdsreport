package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.Agent;

public interface AgentsService {

	public void save(Agent agent);
	public void saveList(List<Agent> agentList);
	
}
