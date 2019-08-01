package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceOverall;

public interface MonthlyURPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_userrequest " + 
			"where yearweek(urequest_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value="select   " + 
			"				count(1)  " + 
			"			from staging_userrequest   ur" + 
			"				 left join ticket_exception e" + 
			"                  on ur.scalar_urequestref = e.ref and e.type='ur'" + 
			"			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')    " + 
			"			and DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') " + 
			"			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' " + 
			"			and scalar_user like 'EXT%' " + 
			"           and e.ref is null;",
			 nativeQuery=true)
	public int getTicketCount(@Param("month") int month);
	
	@Query(value="select " + 
			"	count(1) " + 
			"			from staging_userrequest   ur " + 
			"				 left join ticket_exception e " + 
			"                  on ur.scalar_urequestref = e.ref and e.type='ur' " + 
			"where DATE_FORMAT(urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  "+
			//"and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			//"or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"and urequest_slattopassed='no'  "+
			"and scalar_user like 'EXT%' and e.ref is null"+
			";", nativeQuery=true)
	public int getAchievedTicketCount(@Param("month") int month);
	
	@Query(value="select " + 
			"	count(1) " + 
			"			from staging_userrequest   ur " + 
			"				 left join ticket_exception e " + 
			"                  on ur.scalar_urequestref = e.ref and e.type='ur' " +
			"where DATE_FORMAT(urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			//"and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			//"or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"and urequest_slattopassed='yes'  "+
			"and scalar_user like 'EXT%' and e.ref is null "+
			";", nativeQuery=true)
	public int getMissedTicketCount(@Param("month") int month);
	
	@Query(value="select * from perf_overall "+
			"WHERE  " +
				"year(created_dt)=year(curdate()) "+   
				"AND month(created_dt)= :month "+
				"AND period='monthly' "+
				"AND category='ur';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisMonth(@Param("month") int month);
	
	@Query(value="select * from perf_overall WHERE  Year(created_dt) = Year(Curdate()) AND month(created_dt) = month(curdate()) "+
			"AND period='monthly' AND category='ur';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisMonth();

}
