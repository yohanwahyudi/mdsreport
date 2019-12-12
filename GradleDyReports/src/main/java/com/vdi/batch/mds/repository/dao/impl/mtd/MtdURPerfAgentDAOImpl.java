package com.vdi.batch.mds.repository.dao.impl.mtd;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdURPerfAgentRepository;
import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.model.performance.PerformanceAgent;

@Transactional
@Service("mtdUrPerfAgentDao")
public class MtdURPerfAgentDAOImpl implements PerfAgentDAOService{
	
	@Autowired
	private MtdURPerfAgentRepository mtdUrAgentRepository;

	@Override
	public List<Object[]> getAgentTicket() {
		
		return mtdUrAgentRepository.getAgentTicket();
	}

	@Override
	public List<Object[]> getAgentTicket(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPerformance(List<PerformanceAgent> listAgent) {
		
		mtdUrAgentRepository.saveAll(listAgent);
	}

	@Override
	public List<PerformanceAgent> getPerformance() {
		
		return mtdUrAgentRepository.getExistingPerformance();
	}

	@Override
	public List<PerformanceAgent> getPerformance(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePerformance(List<PerformanceAgent> agents) {
		
		mtdUrAgentRepository.saveAll(agents);
	}

}
