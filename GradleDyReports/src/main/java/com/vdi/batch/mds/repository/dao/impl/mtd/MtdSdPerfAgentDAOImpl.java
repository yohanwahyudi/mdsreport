package com.vdi.batch.mds.repository.dao.impl.mtd;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdSdPerfAgentRepository;
import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.model.performance.PerformanceAgent;

@Transactional
@Service("mtdSdPerfAgentDao")
public class MtdSdPerfAgentDAOImpl implements PerfAgentDAOService{

	@Autowired
	private MtdSdPerfAgentRepository mtdSdPerfAgent;

	@Override
	public List<Object[]> getAgentTicket() {
		
		return mtdSdPerfAgent.getAgentTicket();
	}

	@Override
	public List<Object[]> getAgentTicket(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPerformance(List<PerformanceAgent> listAgent) {
		
		mtdSdPerfAgent.saveAll(listAgent);
	}

	@Override
	public List<PerformanceAgent> getPerformance() {
		
		return mtdSdPerfAgent.getExistingPerformance();
	}

	@Override
	public List<PerformanceAgent> getPerformance(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePerformance(List<PerformanceAgent> agents) {
		
		mtdSdPerfAgent.saveAll(agents);
	}
	
	
	
}
