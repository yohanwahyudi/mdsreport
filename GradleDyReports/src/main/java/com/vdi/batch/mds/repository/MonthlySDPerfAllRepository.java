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
	
	@Query(value="SELECT " + 
			"	count(1) " + 
			"FROM staging_servicedesk s " + 
			"		LEFT JOIN ticket_exception e " +
			"			 ON e.ref = s.incident_ref " +
			"				AND e.type = 'sd' " +
			"where scalar_previousvalue in ('escalated_tto','new') AND scalar_newvalue = 'assigned' "+
			"	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"	   AND scalar_user like 'EXT%' AND e.ref is null;", nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select " + 
			"	count(1) " + 
			"FROM staging_servicedesk s " + 
			"		LEFT JOIN ticket_exception e " +
			"			 ON e.ref = s.incident_ref " +
			"				AND e.type = 'sd' " +
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"	   AND incident_slattopassed='no' "+
			"	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"	   AND scalar_user like 'EXT%' AND ref is null;", nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select " + 
			"	count(1) " + 
			"FROM staging_servicedesk s " + 
			"		LEFT JOIN ticket_exception e " +
			"			 ON e.ref = s.incident_ref " +
			"				AND e.type = 'sd' " +
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"	   AND incident_slattopassed='yes' "+
			"	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"	   AND scalar_user like 'EXT%' AND ref is null;", nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall WHERE  month(created_dt)=month(curdate()) and "+
			"year(created_dt)=year(curdate()) AND period='monthly' AND category='sd';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
