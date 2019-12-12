package com.vdi.batch.mds.repository.dao.impl.mtd;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.MtdSdPerfAllRepository;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.model.performance.PerformanceOverall;

@Transactional
@Service("mtdSdPerfAllDao")
public class MtdSdPerfAllDAOImpl implements PerfAllDAOService{
	
	@Autowired
	MtdSdPerfAllRepository mtdSdPerfAll;

	@Override
	public int getTicketCount() {
		
		return mtdSdPerfAll.getTicketCount();
	}

	@Override
	public int getTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAchievedTicketCount() {
		
		return mtdSdPerfAll.getAchievedTicketCount();
	}

	@Override
	public int getAchievedTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMissedTicketCount() {
		
		return mtdSdPerfAll.getMissedTicketCount();
	}

	@Override
	public int getMissedTicketCount(int week, int month) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertPerformance(PerformanceOverall overall) {
		
		mtdSdPerfAll.save(overall);
		
	}

	@Override
	public PerformanceOverall getPerformance() {
		
		return mtdSdPerfAll.getExistingPerformance();
	}

	@Override
	public PerformanceOverall getPerformance(int week, int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePerformance(PerformanceOverall list) {
		
		mtdSdPerfAll.save(list);
		
	}

}
