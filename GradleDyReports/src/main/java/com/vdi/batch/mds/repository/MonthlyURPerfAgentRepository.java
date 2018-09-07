package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceAgent;

public interface MonthlyURPerfAgentRepository extends CrudRepository<PerformanceAgent, Long>{

	@Query(value="SELECT " + 
			"	one.division, "+
			"	one.agent,   " + 
			"	IFNULL(two.achieved_ticket,0) AS achieved_ticket,  " + 
			"   IFNULL(three.missed_ticket,0) AS missed_ticket,  " + 
			"   IFNULL(one.total_ticket,0) AS total_ticket  " + 
			"     " + 
			"FROM ( " + 
			"	SELECT   " + 
			"		IFNULL(agent.division,'') AS division, "+
			"		staging.scalar_user AS agent,    " + 
			"		Count(staging.scalar_urequestref) AS total_ticket    " + 
			"	FROM  staging_userrequest staging    " + 
			"	LEFT JOIN agent    " + 
			"	 ON staging.scalar_user = agent.NAME    " + 
			"	WHERE "+
			"    scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  " + 
			"	and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			"	or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"	AND year(urequest_startdate)=year(curdate()) "+   
			"	AND month(urequest_startdate)= :month "+
			"	AND staging.scalar_user like 'EXT%' "+
			"	GROUP  BY staging.scalar_user    " + 
			"	ORDER BY agent ASC  " + 
			") one " + 
			"LEFT JOIN (  " + 
			"	SELECT  " + 
			"		staging.scalar_user AS agent,  " + 
			"        count(staging.scalar_urequestref) AS achieved_ticket  " + 
			"    FROM  " + 
			"		staging_userrequest staging  " + 
			"	LEFT JOIN agent   " + 
			"		ON agent.name = staging.scalar_user  " + 
			"    WHERE " + 
			"    scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  " + 
			"	and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			"	or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"	 AND staging.urequest_slattopassed = 'no'   " + 
			"	 AND year(urequest_startdate)=year(curdate()) "+   
			"	 AND month(urequest_startdate)= :month "+
			"	 AND staging.scalar_user like 'EXT%' "+
			"    GROUP BY staging.scalar_user  " + 
			")	two  " + 
			"	ON one.agent = two.agent  " + 
			"LEFT JOIN (  " + 
			"	SELECT  " + 
			"		staging.scalar_user AS agent,  " + 
			"        count(staging.scalar_urequestref) AS missed_ticket  " + 
			"    FROM  " + 
			"		staging_userrequest staging  " + 
			"	LEFT JOIN agent   " + 
			"		ON agent.name = staging.scalar_user  " + 
			"    WHERE   " + 
			"    scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  " + 
			"	and ((urequest_starttime>='08:30:00' and urequest_starttime<='12:00:00') "+
			"	or (urequest_starttime>='13:00:00' and urequest_starttime<='17:30:00')) "+
			"	 AND staging.urequest_slattopassed = 'yes'   " + 
			"	 AND year(urequest_startdate)=year(curdate()) "+   
			"	 AND month(urequest_startdate)= :month "+
			"	 AND staging.scalar_user like 'EXT%' "+
			"    GROUP BY staging.scalar_user  " + 
			")	three                               " + 
			"	ON one.agent = three.agent; ", nativeQuery=true)
	public List<Object[]> getAgentTicket(@Param("month") int month);
	
	@Query(value="select " + 
			"	* " + 
			"from perf_agent " + 
			"where "+
			"year(created_dt)=year(curdate()) "+   
			"AND month(created_dt)= :month "+
			"AND period='monthly'  "+
			"AND category='ur' "+
			";", nativeQuery=true)
	public List<PerformanceAgent> getPerformanceThisMonth(@Param("month") int month);
	
}
