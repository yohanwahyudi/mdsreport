package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceTeam;

@Repository
public interface MonthlyPerfTeamRepository extends CrudRepository<PerformanceTeam,Long>{

	@Query(value="SELECT one.division, " + 
			"       Ifnull(one.total_ticket, 0) as total_ticket, " + 
			"       Ifnull(two.achieved_ticket, 0) as achieved_ticket, " + 
			"       Ifnull(three.missed_ticket, 0) as missed_ticket " + 
			"FROM   (SELECT agent.division, " + 
			"               Count(ref) AS total_ticket " + 
			"        FROM   incident incident " + 
			"               INNER JOIN agent agent " + 
			"                       ON incident.agent_fullname = agent.NAME " +
			"		 WHERE "+	
			//"        WHERE  status IN ( 'closed', 'resolved' ) AND " + 
			"				start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  "+
			"				AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"        GROUP  BY division) one " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         Count(ref) AS achieved_ticket " + 
			"                  FROM   incident incident " + 
			"                         INNER JOIN agent agent " + 
			"                                 ON incident.agent_fullname = agent.NAME " +
			"					WHERE "+
			//"                  WHERE  status IN ( 'closed', 'resolved' ) AND " + 
			"                         ttr_passed = 'no' " + 
			"						  AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  "+
			"						  AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"                  GROUP  BY division) two " + 
			"              ON one.division = two.division " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         Count(ref) AS missed_ticket " + 
			"                  FROM   incident incident " + 
			"                         INNER JOIN agent agent " + 
			"                                 ON incident.agent_fullname = agent.NAME " +
			" 				   WHERE "+		
			//"                  WHERE  status IN ( 'closed', 'resolved' ) AND " + 
			"                         ttr_passed = 'yes' " + 
			"						  AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  "+
			"						  AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"                  GROUP  BY division) three " + 
			"              ON one.division = three.division; ",nativeQuery=true)
	public List<Object[]> getTeamTicketByDivision();
	
	@Query(value="select * from perf_team WHERE  month(created_dt)=month(curdate()) and year(created_dt)=year(curdate()) AND period='monthly' AND category='sa';", nativeQuery=true)
	public List<PerformanceTeam> getPerformanceThisWeek();
	
}
