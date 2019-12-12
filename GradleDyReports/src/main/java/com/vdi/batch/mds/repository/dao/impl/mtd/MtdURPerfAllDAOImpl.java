package com.vdi.batch.mds.repository.dao.impl.mtd;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdURPerfAllRepository;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.model.performance.PerformanceOverall;

@Transactional
@Service("mtdUrPerfAllDao")
public class MtdURPerfAllDAOImpl implements PerfAllDAOService{

	@Autowired
	private MtdURPerfAllRepository mtdUrRepository;
	
	@Override
	public int getTicketCount() {
		
		return mtdUrRepository.getTicketCount();
	}

	@Override
	public int getTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAchievedTicketCount() {
		
		return mtdUrRepository.getAchievedTicketCount();
	}

	@Override
	public int getAchievedTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMissedTicketCount() {
		
		return mtdUrRepository.getMissedTicketCount();
	}

	@Override
	public int getMissedTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertPerformance(PerformanceOverall overall) {
		
		mtdUrRepository.save(overall);
	}

	@Override
	public PerformanceOverall getPerformance() {
		
		return mtdUrRepository.getExistingPerformance();
	}

	@Override
	public PerformanceOverall getPerformance(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePerformance(PerformanceOverall list) {
		
		mtdUrRepository.save(list);
	}

}
