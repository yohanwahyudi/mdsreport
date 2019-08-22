package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceOverall;

@Repository
public interface MtdURPerfAllRepository extends CrudRepository<PerformanceOverall, Long> {
	
	@Query(value="select   " + 
			"				count(1)  " + 
			"			from staging_userrequest   ur" + 
			"	    		 LEFT JOIN agent    " + 
			"	 			   ON ur.scalar_user = agent.NAME    " + 
			"				 left join ticket_exception e" + 
			"                  on ur.scalar_urequestref = e.ref and e.type='ur'" + 
			"			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')    " +  
			"			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' " + 
			"			and scalar_user like 'EXT%' " + 
			"			and agent.is_active=1 "+
			"           and e.ref is null;",
			 nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select " + 
			"	count(1) " + 
			"			from staging_userrequest   ur " + 
			"	    		 LEFT JOIN agent    " + 
			"	 			   ON ur.scalar_user = agent.NAME    " + 
			"				 left join ticket_exception e " + 
			"                  on ur.scalar_urequestref = e.ref and e.type='ur' " + 
			"where DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  "+
			"and urequest_slattopassed='no'  "+
			"and scalar_user like 'EXT%' and e.ref is null "+
			"and agent.is_active=1 "+
			";", nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select " + 
			"	count(1) " + 
			"			from staging_userrequest   ur " + 
			"	    		 LEFT JOIN agent    " + 
			"	 			   ON ur.scalar_user = agent.NAME    " + 
			"				 left join ticket_exception e " + 
			"                  on ur.scalar_urequestref = e.ref and e.type='ur' " +
			"where DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and urequest_slattopassed='yes'  "+
			"and scalar_user like 'EXT%' and e.ref is null "+
			"and agent.is_active=1 "+
			";", nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall "+
			"WHERE  " +
				"year(created_dt)=year(curdate()) "+   
				"AND month= month(curdate()) "+
				"AND period='monthly' "+
				"AND category='ur';", nativeQuery=true)
	public PerformanceOverall getExistingPerformance();

}
