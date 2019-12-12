package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.AgentRepository;
import com.vdi.batch.mds.repository.dao.AgentsService;
import com.vdi.model.Agent;

@Service
public class AgentServiceImpl implements AgentsService{
	
	@Autowired
	private AgentRepository agentRepository;

	@Override
	public void save(Agent agent) {
		agentRepository.save(agent);
	}

	@Override
	public void saveList(List<Agent> agentList) {
		agentRepository.saveAll(agentList);
	}

}
