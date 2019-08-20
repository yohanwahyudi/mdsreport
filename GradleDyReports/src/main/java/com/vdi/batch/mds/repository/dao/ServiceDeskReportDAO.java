package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.staging.StagingServiceDesk;

public interface ServiceDeskReportDAO {
	
	public List<StagingServiceDesk> getAllIncidentByWeek(int month, int week);
	public List<StagingServiceDesk> getAllIncidentByMonth(int month);

}
