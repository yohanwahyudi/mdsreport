package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceAgent;

@Repository
public interface MonthlyPerfAgentRepository extends CrudRepository<PerformanceAgent, Long>{

	@Query(value="SELECT one.division, " + 
			"       one.agent_fullname," + 
			"       IFNULL(five.assigned_ticket,0) AS assigned_ticket, " + 
			"       IFNULL(four.pending_ticket,0) AS pending_ticket, " + 
			"       IFNULL(two.achieved_ticket,0) AS achieved_ticket, " + 
			"       IFNULL(three.missed_ticket,0) AS missed_ticket, " + 
			"       one.total_ticket          " + 
			"FROM   (SELECT agent.division, " + 
			"               incident.agent_fullname, " + 
			"               Count(incident.ref) AS total_ticket " + 
			"        FROM   incident incident " + 
			"               JOIN agent " + 
			"                 ON incident.agent_fullname = agent.NAME " +
			"        WHERE " +
			"		 		agent.is_active=1 "+
			"				AND incident.team_name NOT LIKE '%SUPPLIER PORTAL%' "+	
			"				AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"				AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"        GROUP  BY incident.agent_fullname " + 
			"        ORDER  BY division, " + 
			"                  agent_fullname ASC) one " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         incident.agent_fullname, " + 
			"                         Count(incident.ref) AS achieved_ticket " + 
			"                  FROM   incident incident " + 
			"                         JOIN agent " + 
			"                           ON incident.agent_fullname = agent.NAME " +
			"                  WHERE " +
			"		 		   agent.is_active=1 "+
			"                         AND ttr_passed = 'no' " + 
			"						  AND incident.team_name NOT LIKE '%SUPPLIER PORTAL%' "+	
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"					      AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"                  GROUP  BY incident.agent_fullname " + 
			"                  ORDER  BY division, " + 
			"                            agent_fullname ASC) two " + 
			"              ON one.agent_fullname = two.agent_fullname " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         incident.agent_fullname, " + 
			"                         Count(incident.ref) AS missed_ticket " + 
			"                  FROM   incident incident " + 
			"                         JOIN agent " + 
			"                           ON incident.agent_fullname = agent.NAME " +
			"                  WHERE " +
			"		 		   agent.is_active=1 "+
			"                         AND ttr_passed = 'yes' " + 
			"						  AND incident.team_name NOT LIKE '%SUPPLIER PORTAL%' "+	
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"                  GROUP  BY incident.agent_fullname " + 
			"                  ORDER  BY division, " + 
			"                            agent_fullname ASC) three " + 
			"              ON one.agent_fullname = three.agent_fullname " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         incident.agent_fullname, " + 
			"                         Count(incident.ref) AS pending_ticket " + 
			"                  FROM   incident incident " + 
			"                         JOIN agent " + 
			"                           ON incident.agent_fullname = agent.NAME " + 
			"                  WHERE  status = 'pending' " + 
			"		 		   		  AND agent.is_active=1 "+
			"						  AND incident.team_name NOT LIKE '%SUPPLIER PORTAL%' "+	
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"                  GROUP  BY incident.agent_fullname " + 
			"                  ORDER  BY division, " + 
			"                            agent_fullname ASC) four " + 
			"              ON one.agent_fullname = four.agent_fullname " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         incident.agent_fullname, " + 
			"                         Count(incident.ref) AS assigned_ticket " + 
			"                  FROM   incident incident " + 
			"                         JOIN agent " + 
			"                           ON incident.agent_fullname = agent.NAME " + 
			"                  WHERE  status = 'assigned' " + 
			"		 		   		  AND agent.is_active=1 "+
			"						  AND incident.team_name NOT LIKE '%SUPPLIER PORTAL%' "+	
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  "+
			"						  AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"                  GROUP  BY incident.agent_fullname" + 
			"                  ORDER  BY division, " + 
			"                            agent_fullname ASC) five " + 
			"              ON one.agent_fullname = five.agent_fullname; ", nativeQuery=true)
	public List<Object[]> getAgentTicket();
	
	@Query(value="SELECT * FROM perf_agent WHERE month(created_dt)=month(curdate()) AND year(created_dt)=year(curdate()) AND period='monthly' AND category='sa';", nativeQuery=true)
	public List<PerformanceAgent> getPerformanceThisWeek();
	

	
}
