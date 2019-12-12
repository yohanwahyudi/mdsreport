package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceTeam;

@Repository
public interface MtdPerfTeamRepository extends CrudRepository<PerformanceTeam, Long>{
	
	@Query(value=" SELECT one.division,        " + 
			"			 			       Ifnull(one.total_ticket, 0) as total_ticket,        " + 
			"			 			       Ifnull(two.achieved_ticket, 0) as achieved_ticket,        " + 
			"			 			       Ifnull(three.missed_ticket, 0) as missed_ticket        " + 
			"			 			FROM   (SELECT agent.division,        " + 
			"			 			               Count(1) AS total_ticket        " + 
			"			 			        FROM   incident incident        " + 
			"			 			               INNER JOIN agent agent        " + 
			"			 			                       ON incident.agent_fullname = agent.NAME       " + 
			"			 						   Left JOIN  " + 
			"													( " + 
			"														SELECT  " + 
			"															ref " + 
			"														FROM  " + 
			"															mds_itop.exception_ticket e " + 
			"														inner join  " + 
			"															exception_header h " + 
			"															on e.exception_header_id = h.id " + 
			"														inner join exception_approval apprv " + 
			"															on h.approval_id = apprv.id " + 
			"														where " + 
			"															h.type_id=1 " + 
			"															and h.category_id=1 " + 
			"															and apprv.status_id=4 " + 
			"													) e " + 
			"												on e.ref = incident.ref       " + 
			"			 					 WHERE 	     " + 
			"			 							agent.is_active=1      " + 
			"			 							AND date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
			"			                             AND e.ref is null     " + 
			"			 			        GROUP  BY division) one        " + 
			"			 			       LEFT JOIN (SELECT agent.division,        " + 
			"			 			                         Count(1) AS achieved_ticket        " + 
			"			 			                  FROM   incident incident        " + 
			"			 			                         INNER JOIN agent agent        " + 
			"			 			                                 ON incident.agent_fullname = agent.NAME       " + 
			"			 									 Left JOIN  " + 
			"													( " + 
			"														SELECT  " + 
			"															ref " + 
			"														FROM  " + 
			"															mds_itop.exception_ticket e " + 
			"														inner join  " + 
			"															exception_header h " + 
			"															on e.exception_header_id = h.id " + 
			"														inner join exception_approval apprv " + 
			"															on h.approval_id = apprv.id " + 
			"														where " + 
			"															h.type_id=1 " + 
			"															and h.category_id=1 " + 
			"															and apprv.status_id=4 " + 
			"													) e " + 
			"												on e.ref = incident.ref       " + 
			"			 								WHERE      " + 
			"			 			                         ttr_passed = 'no'       " + 
			"			 									  AND agent.is_active=1      " + 
			"			 									  AND date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
			"			                                       AND e.ref is null     " + 
			"			 			                  GROUP  BY division) two        " + 
			"			 			              ON one.division = two.division        " + 
			"			 			       LEFT JOIN (SELECT agent.division,        " + 
			"			 			                         Count(1) AS missed_ticket        " + 
			"			 			                  FROM   incident incident        " + 
			"			 			                         INNER JOIN agent agent        " + 
			"			 			                                 ON incident.agent_fullname = agent.NAME       " + 
			"			 									 Left JOIN  " + 
			"													( " + 
			"														SELECT  " + 
			"															ref " + 
			"														FROM  " + 
			"															mds_itop.exception_ticket e " + 
			"														inner join  " + 
			"															exception_header h " + 
			"															on e.exception_header_id = h.id " + 
			"														inner join exception_approval apprv " + 
			"															on h.approval_id = apprv.id " + 
			"														where " + 
			"															h.type_id=1 " + 
			"															and h.category_id=1 " + 
			"															and apprv.status_id=4 " + 
			"													) e " + 
			"												on e.ref = incident.ref      " + 
			"			 			 				   WHERE 		     " + 
			"			 			                         ttr_passed = 'yes'       " + 
			"			 									  AND agent.is_active=1      " + 
			"			 									  AND date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
			"			                                       AND e.ref is null     " + 
			"			 			                  GROUP  BY division) three        " + 
			"			 			              ON one.division = three.division;   ",nativeQuery=true)
	public List<Object[]> getTeamTicketByDivision();
	
	@Query(value="select * from perf_team "
			+ "WHERE  month=month(curdate()) and year(created_dt)=year(curdate()) "
			+ "AND period='monthly' AND category='sa';", nativeQuery=true)
	public List<PerformanceTeam> getExistingPerformance();

}
