package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceAgent;

@Repository
public interface MonthlySDPerfAgentRepository extends CrudRepository<PerformanceAgent, Long>{

	@Query(value="SELECT " + 
			"	one.division, "+
			"	one.agent,   " + 
			"	IFNULL(two.achieved_ticket,0) AS achieved_ticket,  " + 
			"    IFNULL(three.missed_ticket,0) AS missed_ticket,  " + 
			"    IFNULL(one.total_ticket,0) AS total_ticket  " + 
			"     " + 
			"FROM ( " + 
			"	SELECT   " + 
			"		IFNULL(agent.division,'') AS division, "+
			"		staging.scalar_user AS agent,    " + 
			"		Count(staging.incident_ref) AS total_ticket    " + 
			"	FROM  staging_servicedesk staging    " + 
			"	LEFT JOIN agent    " + 
			"	 ON staging.scalar_user = agent.NAME    " + 
			"	WHERE scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  " + 
			"    AND month(incident_startdate)=month(curdate())-1 and year(incident_startdate)=year(curdate())  " +
			"	 AND staging.scalar_user like 'EXT%' "+
			"	GROUP  BY staging.scalar_user    " + 
			"	ORDER BY agent ASC  " + 
			") one " + 
			"LEFT JOIN (  " + 
			"	SELECT  " + 
			"		staging.scalar_user AS agent,  " + 
			"        count(staging.incident_ref) AS achieved_ticket  " + 
			"    FROM  " + 
			"		staging_servicedesk staging  " + 
			"	LEFT JOIN agent   " + 
			"		ON agent.name = staging.scalar_user  " + 
			"    WHERE scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  " + 
			"		AND staging.incident_slattopassed = 'no'   " + 
			"    AND month(incident_startdate)=month(curdate())-1 and year(incident_startdate)=year(curdate())  " +
			"	 AND staging.scalar_user like 'EXT%' "+
			"    GROUP BY staging.scalar_user  " + 
			")	two  " + 
			"	ON one.agent = two.agent  " + 
			"LEFT JOIN (  " + 
			"	SELECT  " + 
			"		staging.scalar_user AS agent,  " + 
			"        count(staging.incident_ref) AS missed_ticket  " + 
			"    FROM  " + 
			"		staging_servicedesk staging  " + 
			"	LEFT JOIN agent   " + 
			"		ON agent.name = staging.scalar_user  " + 
			"    WHERE scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'  " + 
			"		AND staging.incident_slattopassed = 'yes'   " + 
			"    AND month(incident_startdate)=month(curdate())-1 and year(incident_startdate)=year(curdate())  " +
			"	 AND staging.scalar_user like 'EXT%' "+
			"    GROUP BY staging.scalar_user  " + 
			")	three                               " + 
			"	ON one.agent = three.agent; ", nativeQuery=true)
	public List<Object[]> getAgentTicket();
	
	@Query(value="SELECT * FROM perf_agent WHERE month(created_dt)=month(curdate()) and "+
			"year(created_dt)=year(curdate()) AND period='monthly' AND category='sd';", nativeQuery=true)
	public List<PerformanceAgent> getPerformanceThisWeek();
	
}
