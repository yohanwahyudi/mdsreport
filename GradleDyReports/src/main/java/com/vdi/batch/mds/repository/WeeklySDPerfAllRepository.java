package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceOverall;

public interface WeeklySDPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where yearweek(incident_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where year(incident_startdate)=year(curdate()) "+   
			"and month(incident_startdate)= :month "+
			"and week(incident_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and scalar_user like 'EXT%' "+
			";", nativeQuery=true)
	public int getTicketCount(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and yearweek(incident_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where year(incident_startdate)=year(curdate()) "+   
			"and month(incident_startdate)= :month "+
			"and week(incident_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and incident_slattopassed='no'  "+
			"and scalar_user like 'EXT%' "+
			";", nativeQuery=true)
	public int getAchievedTicketCount( @Param("week") int week, @Param("month") int month);
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and incident_slattopassed='no' "+
			"and yearweek(incident_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where year(incident_startdate)=year(curdate()) "+   
			"and month(incident_startdate)= :month "+
			"and week(incident_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and incident_slattopassed='yes'  "+
			"and scalar_user like 'EXT%' "+
			";", nativeQuery=true)
	public int getMissedTicketCount( @Param("week") int week, @Param("month") int month);
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and incident_slattopassed='yes' "+
			"and yearweek(incident_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall "+
		"WHERE  " +
			"year(created_dt)=year(curdate()) "+   
			"AND month= :month "+
			"AND week(created_dt,3)= :week "+
			"AND period='weekly' "+
			"AND category='sd';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select * from perf_overall WHERE  Yearweek(created_dt, 3) = Yearweek(Curdate(), 3) AND period='weekly' AND category='sd';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
