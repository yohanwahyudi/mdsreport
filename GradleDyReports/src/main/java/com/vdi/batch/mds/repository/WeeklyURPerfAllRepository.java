package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceOverall;

public interface WeeklyURPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{

	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_userrequest " + 
			"where yearweek(urequest_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value="select " + 
			"	count(scalar_urequestref) " + 
			"from staging_userrequest " + 
			"where year(urequest_startdate)=year(curdate()) "+   
			"and month(urequest_startdate)= :month "+
			"and week(urequest_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			//"and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			//		"or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"and scalar_user like 'EXT%' "+
			";", nativeQuery=true)
	public int getTicketCount(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select " + 
			"	count(scalar_urequestref) " + 
			"from staging_userrequest " + 
			"where year(urequest_startdate)=year(curdate()) "+   
			"and month(urequest_startdate)= :month "+
			"and week(urequest_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and urequest_slattopassed='no'  "+
			//"and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			//		"or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"and scalar_user like 'EXT%' "+
			";", nativeQuery=true)
	public int getAchievedTicketCount( @Param("week") int week, @Param("month") int month);
	
	@Query(value="select " + 
			"	count(scalar_urequestref) " + 
			"from staging_userrequest " + 
			"where year(urequest_startdate)=year(curdate()) "+   
			"and month(urequest_startdate)= :month "+
			"and week(urequest_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and urequest_slattopassed='yes'  "+
			//"and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			//		"or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"and scalar_user like 'EXT%' "+
			";", nativeQuery=true)
	public int getMissedTicketCount( @Param("week") int week, @Param("month") int month);
	
	@Query(value="select * from perf_overall "+
			"WHERE  " +
				"year(created_dt)=year(curdate()) "+   
				"AND month= :month "+
				"AND week(created_dt,3)= :week "+
				"AND period='weekly' "+
				"AND category='ur';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select * from perf_overall WHERE  Yearweek(created_dt, 3) = Yearweek(Curdate(), 3) AND period='weekly' AND category='ur';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();
}
