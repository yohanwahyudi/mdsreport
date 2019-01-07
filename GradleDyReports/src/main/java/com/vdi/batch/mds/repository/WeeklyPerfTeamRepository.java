package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceTeam;

public interface WeeklyPerfTeamRepository extends CrudRepository<PerformanceTeam, Long>{
	@Query(value="SELECT one.division, " + 
			"       Ifnull(one.total_ticket, 0) as total_ticket, " + 
			"       Ifnull(two.achieved_ticket, 0) as achieved_ticket, " + 
			"       Ifnull(three.missed_ticket, 0) as missed_ticket " + 
			"FROM   (SELECT agent.division, " + 
			"               Count(ref) AS total_ticket " + 
			"        FROM   incident incident " + 
			"               INNER JOIN agent agent " + 
			"                       ON incident.agent_fullname = agent.NAME " + 
			"        WHERE   " + 
			"				year(start_date)=year(curdate()) "+   
			"				AND month(start_date)= :month "+
			"				AND week(start_date,3)= :week "+
			//"				AND status in ('closed','resolved') "+
			"        GROUP  BY division) one " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         Count(ref) AS achieved_ticket " + 
			"                  FROM   incident incident " + 
			"                         INNER JOIN agent agent " + 
			"                                 ON incident.agent_fullname = agent.NAME " + 
			"                  WHERE   " + 
			"						year(start_date)=year(curdate()) "+   
			"						AND month(start_date)= :month "+
			"						AND week(start_date,3)= :week "+
			//"						AND status in ('closed','resolved') "+
			"                       AND ttr_passed = 'no' " + 
			"                  GROUP  BY division) two " + 
			"              ON one.division = two.division " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         Count(ref) AS missed_ticket " + 
			"                  FROM   incident incident " + 
			"                         INNER JOIN agent agent " + 
			"                                 ON incident.agent_fullname = agent.NAME " + 
			"        			WHERE   " + 
			"						year(start_date)=year(curdate()) "+   
			"						AND month(start_date)= :month "+
			"						AND week(start_date,3)= :week "+
			//"						AND status in ('closed','resolved') "+
			"                       AND ttr_passed = 'yes' " + 
			"                  GROUP  BY division) three " + 
			"              ON one.division = three.division; ",nativeQuery=true)
	public List<Object[]> getTeamTicketByDivision(@Param("week") int week, @Param("month") int month);
	
	@Query(value="SELECT one.division, " + 
			"       Ifnull(one.total_ticket, 0) as total_ticket, " + 
			"       Ifnull(two.achieved_ticket, 0) as achieved_ticket, " + 
			"       Ifnull(three.missed_ticket, 0) as missed_ticket " + 
			"FROM   (SELECT agent.division, " + 
			"               Count(ref) AS total_ticket " + 
			"        FROM   incident incident " + 
			"               INNER JOIN agent agent " + 
			"                       ON incident.agent_fullname = agent.NAME " + 
			"        WHERE  "+
			//"				status IN ( 'closed', 'resolved' ) AND " + 
			"               Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
			"        GROUP  BY division) one " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         Count(ref) AS achieved_ticket " + 
			"                  FROM   incident incident " + 
			"                         INNER JOIN agent agent " + 
			"                                 ON incident.agent_fullname = agent.NAME " + 
			"                  WHERE  " +
			//"						  status IN ( 'closed', 'resolved' ) AND " + 
			"                         ttr_passed = 'no' " + 
			"                         AND Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
			"                  GROUP  BY division) two " + 
			"              ON one.division = two.division " + 
			"       LEFT JOIN (SELECT agent.division, " + 
			"                         Count(ref) AS missed_ticket " + 
			"                  FROM   incident incident " + 
			"                         INNER JOIN agent agent " + 
			"                                 ON incident.agent_fullname = agent.NAME " + 
			"                  WHERE  "+
			//"						  status IN ( 'closed', 'resolved' ) AND " + 
			"                         ttr_passed = 'yes' " + 
			"                         AND Yearweek(start_date, 3) = Yearweek(Curdate(), 3) " + 
			"                  GROUP  BY division) three " + 
			"              ON one.division = three.division; ",nativeQuery=true)
	public List<Object[]> getTeamTicketByDivision();
	
	@Query(value="select " + 
			"	* " + 
			"from perf_team " + 
			"where "+
			"year(created_dt)=year(curdate()) "+   
			"AND month= :month "+
			"AND week(created_dt,3)= :week "+
			"AND period='weekly'  "+
			"AND category='sa' "+
			";", nativeQuery=true)
	public List<PerformanceTeam> getPerformanceThisWeek(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select * from perf_team WHERE  Yearweek(created_dt, 3) = Yearweek(Curdate(), 3) AND period='weekly' AND category='sa';", nativeQuery=true)
	public List<PerformanceTeam> getPerformanceThisWeek();

}
