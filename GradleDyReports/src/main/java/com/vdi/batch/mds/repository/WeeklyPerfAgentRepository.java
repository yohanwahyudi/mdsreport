package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceAgent;

public interface WeeklyPerfAgentRepository extends CrudRepository<PerformanceAgent, Long>{
	
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
			"        WHERE   " + 
			"				year(start_date)=year(curdate()) "+   
			"				AND month(start_date)= :month "+
			"				AND week(start_date,3)= :week "+
			//"				AND status in ('closed','resolved') "+
			"        GROUP  BY incident.agent_fullname " + 
			"        ORDER  BY division, " + 
			"                  agent_fullname ASC) one " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         incident.agent_fullname, " + 
			"                         Count(incident.ref) AS achieved_ticket " + 
			"                  FROM   incident incident " + 
			"                         JOIN agent " + 
			"                           ON incident.agent_fullname = agent.NAME " + 
			"        			WHERE   " + 
			"							year(start_date)=year(curdate()) "+   
			"							AND month(start_date)= :month "+
			"							AND week(start_date,3)= :week "+
			//"							AND status in ('closed','resolved') "+
			"                         	AND ttr_passed = 'no' " + 
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
			"        		   WHERE   " + 
			"							year(start_date)=year(curdate()) "+   
			"							AND month(start_date)= :month "+
			"							AND week(start_date,3)= :week "+
			//"							AND status in ('closed','resolved') "+
			"                         	AND ttr_passed = 'yes' " + 
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
			"        		   WHERE   " + 
			"							year(start_date)=year(curdate()) "+   
			"							AND month(start_date)= :month "+
			"							AND week(start_date,3)= :week "+
			"							AND status = 'pending' "+ 
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
			"        		   WHERE   " + 
			"							year(start_date)=year(curdate()) "+   
			"							AND month(start_date)= :month "+
			"							AND week(start_date,3)= :week "+
			"							AND status = 'assigned' "+  
			"                  GROUP  BY incident.agent_fullname" + 
			"                  ORDER  BY division, " + 
			"                            agent_fullname ASC) five " + 
			"              ON one.agent_fullname = five.agent_fullname; ", nativeQuery=true)
	public List<Object[]> getAgentTicket(@Param("week") int week, @Param("month") int month);
	
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
			"        WHERE  Yearweek(start_date, 3) = Yearweek(Curdate(), 3) AND "+
			//"				status IN ( 'closed', 'resolved' ) " + 
			"        GROUP  BY incident.agent_fullname " + 
			"        ORDER  BY division, " + 
			"                  agent_fullname ASC) one " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         incident.agent_fullname, " + 
			"                         Count(incident.ref) AS achieved_ticket " + 
			"                  FROM   incident incident " + 
			"                         JOIN agent " + 
			"                           ON incident.agent_fullname = agent.NAME " + 
			"                  WHERE  "+
			//"						  status IN ( 'closed', 'resolved' ) " + 
			"                         ttr_passed = 'no' " + 
			"                         AND Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
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
			"                  WHERE  "+
			//"						  status IN ( 'closed', 'resolved' ) " + 
			"                         ttr_passed = 'yes' " + 
			"                         AND Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
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
			"                         AND Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
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
			"                         AND Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
			"                  GROUP  BY incident.agent_fullname" + 
			"                  ORDER  BY division, " + 
			"                            agent_fullname ASC) five " + 
			"              ON one.agent_fullname = five.agent_fullname; ", nativeQuery=true)
	public List<Object[]> getAgentTicket();
	
	@Query(value="select " + 
			"	* " + 
			"from perf_agent " + 
			"where "+
			"year(created_dt)=year(curdate()) "+   
			"AND month= :month "+
			"AND week(created_dt,3)= :week "+
			"AND period='weekly'  "+
			"AND category='sa' "+
			";", nativeQuery=true)
	public List<PerformanceAgent> getPerformanceThisWeek(@Param("week") int week, @Param("month") int month);
	
	@Query(value="SELECT * FROM perf_agent WHERE Yearweek(created_dt, 3) = Yearweek(Curdate(), 3) AND period='weekly' AND category='sa';", nativeQuery=true)
	public List<PerformanceAgent> getPerformanceThisWeek();

}
