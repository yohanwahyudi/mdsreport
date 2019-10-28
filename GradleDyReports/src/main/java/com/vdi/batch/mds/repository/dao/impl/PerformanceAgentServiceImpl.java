package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.PerformanceAgentRepository;
import com.vdi.batch.mds.repository.dao.PerformanceAgentService;
import com.vdi.model.performance.PerformanceAgent;

@Service
@Transactional
public class PerformanceAgentServiceImpl implements PerformanceAgentService{
	
	@Autowired
	private PerformanceAgentRepository performanceAgentRepository;

	@Override
	public void save(PerformanceAgent agent) {
		performanceAgentRepository.save(agent);
	}

	@Override
	public void saveList(List<PerformanceAgent> agents) {
		performanceAgentRepository.saveAll(agents);
	}

	@Override
	public void delete(PerformanceAgent agent) {
		performanceAgentRepository.delete(agent);
	}

	@Override
	public void deleteList(List<PerformanceAgent> agentList) {
		performanceAgentRepository.deleteAll(agentList);
	}

	@Override
	public void deleteUnassignedAgent() {
		performanceAgentRepository.deleteUnassignedAgent();
	}
	
	

}
