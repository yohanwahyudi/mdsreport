package com.vdi.batch.mds.repository.dao.impl.mtd;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdPerfAllRepository;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.model.performance.PerformanceOverall;

@Transactional
@Service("mtdPerfAllDao")
public class MtdPerfAllDAOImpl implements PerfAllDAOService{
	
	@Autowired
	private MtdPerfAllRepository mtdPerfRepo;

	@Override
	public int getTicketCount() {
		
		return mtdPerfRepo.getTicketCount();
	}

	@Override
	public int getTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAchievedTicketCount() {
		
		return mtdPerfRepo.getAchievedTicketCount();
	}

	@Override
	public int getAchievedTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMissedTicketCount() {
		
		return mtdPerfRepo.getMissedTicketCount();
	}

	@Override
	public int getMissedTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertPerformance(PerformanceOverall overall) {
		
		mtdPerfRepo.save(overall);
		
	}

	@Override
	public PerformanceOverall getPerformance() {
		
		return mtdPerfRepo.getExistingPerformance();
	}

	@Override
	public PerformanceOverall getPerformance(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePerformance(PerformanceOverall perf) {
		
		mtdPerfRepo.save(perf);
		
	}

}
