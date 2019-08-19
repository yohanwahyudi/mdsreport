package com.vdi.batch.mds.repository.dao.impl.mtd;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdPerfAgentRepository;
import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.model.performance.PerformanceAgent;

@Transactional
@Service("mtdPerfAgentDao")
public class MtdPerfAgentDAOImpl implements PerfAgentDAOService{

	@Autowired
	private MtdPerfAgentRepository mtdAgentRepository;
	
	@Override
	public List<Object[]> getAgentTicket() {
		
		return mtdAgentRepository.getAgentTicket();
	}

	@Override
	public List<Object[]> getAgentTicket(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertPerformance(List<PerformanceAgent> listAgent) {

		mtdAgentRepository.saveAll(listAgent);
		
	}

	@Override
	public List<PerformanceAgent> getPerformance() {
		
		return mtdAgentRepository.getPerformance();
	}

	@Override
	public List<PerformanceAgent> getPerformance(int week, int month) {
		
		return null;
	}

	@Override
	public void updatePerformance(List<PerformanceAgent> agents) {
		
		mtdAgentRepository.saveAll(agents);
		
	}

}
