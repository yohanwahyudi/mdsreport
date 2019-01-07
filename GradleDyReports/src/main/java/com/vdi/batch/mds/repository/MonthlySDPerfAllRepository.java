package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.performance.PerformanceOverall;

public interface MonthlySDPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where month(incident_startdate)=month(curdate())-1 and year(incident_startdate)=year(curdate());", nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where scalar_previousvalue in ('escalated_tto','new') AND scalar_newvalue = 'assigned' "+
			"	   AND incident_startdate < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  "+
			"	   AND incident_startdate >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"	   AND scalar_user like 'EXT%';", nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"	   AND incident_slattopassed='no' "+
			"	   AND incident_startdate < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  "+
			"	   AND incident_startdate >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"	   AND scalar_user like 'EXT%';", nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"	   AND incident_slattopassed='yes' "+
			"	   AND incident_startdate < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  "+
			"	   AND incident_startdate >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"	   AND scalar_user like 'EXT%';", nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall WHERE  month(created_dt)=month(curdate()) and "+
			"year(created_dt)=year(curdate()) AND period='monthly' AND category='sd';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
