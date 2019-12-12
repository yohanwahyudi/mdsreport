package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceAgent;

@Repository
public interface MtdURPerfAgentRepository extends CrudRepository<PerformanceAgent, Long>{
	
	@Query(value=" SELECT      " + 
			"			 	one.division,    " + 
			"			 	one.agent,        " + 
			"			 	IFNULL(two.achieved_ticket,0) AS achieved_ticket,       " + 
			"			    IFNULL(three.missed_ticket,0) AS missed_ticket,       " + 
			"			    IFNULL(one.total_ticket,0) AS total_ticket       " + 
			"			           " + 
			"			 FROM (      " + 
			"			 	SELECT        " + 
			"			 		IFNULL(agent.division,'') AS division,    " + 
			"			 		staging.scalar_user AS agent,         " + 
			"			 		Count(1) AS total_ticket         " + 
			"			 	FROM  staging_userrequest staging         " + 
			"			 	LEFT JOIN agent         " + 
			"			 	 ON staging.scalar_user = agent.NAME         " + 
			"			 	LEFT JOIN  " + 
			"									( " + 
			"										SELECT       " + 
			"											ref      " + 
			"										FROM       " + 
			"											mds_itop.exception_ticket e      " + 
			"										inner join       " + 
			"											exception_header h      " + 
			"											on e.exception_header_id = h.id      " + 
			"										inner join exception_approval apprv      " + 
			"											on h.approval_id = apprv.id      " + 
			"										where      " + 
			"											h.type_id=2      " + 
			"											and h.category_id=2      " + 
			"											and apprv.status_id=4  " + 
			"									) e " + 
			"									 ON e.ref = staging.scalar_urequestref       " + 
			"			 	WHERE    " + 
			"			     scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'       " + 
			"			 	AND agent.is_active=1    " + 
			"			 	and DATE_FORMAT(urequest_startdate,'%Y-%m-01 00:00:00') >= DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')     " + 
			"			 	AND staging.scalar_user like 'EXT%'    " + 
			"			 	and e.ref is null    " + 
			"			 	GROUP  BY staging.scalar_user         " + 
			"			 	ORDER BY agent ASC       " + 
			"			 ) one      " + 
			"			 LEFT JOIN (       " + 
			"			 	SELECT       " + 
			"			 		staging.scalar_user AS agent,       " + 
			"			         count(staging.scalar_urequestref) AS achieved_ticket       " + 
			"			     FROM       " + 
			"			 		staging_userrequest staging       " + 
			"			 	LEFT JOIN agent        " + 
			"			 		ON agent.name = staging.scalar_user       " + 
			"			 	LEFT JOIN  " + 
			"									( " + 
			"										SELECT       " + 
			"											ref      " + 
			"										FROM       " + 
			"											mds_itop.exception_ticket e      " + 
			"										inner join       " + 
			"											exception_header h      " + 
			"											on e.exception_header_id = h.id      " + 
			"										inner join exception_approval apprv      " + 
			"											on h.approval_id = apprv.id      " + 
			"										where      " + 
			"											h.type_id=2      " + 
			"											and h.category_id=2      " + 
			"											and apprv.status_id=4  " + 
			"									) e " + 
			"									 ON e.ref = staging.scalar_urequestref     " + 
			"			     WHERE      " + 
			"			     scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'       " + 
			"			 	AND agent.is_active=1    " + 
			"			 	AND staging.urequest_slattopassed = 'no'        " + 
			"			 	and DATE_FORMAT(urequest_startdate,'%Y-%m-01 00:00:00') >= DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')     " + 
			"			 	AND staging.scalar_user like 'EXT%'    " + 
			"			 	and e.ref is null    " + 
			"			     GROUP BY staging.scalar_user       " + 
			"			 )	two       " + 
			"			 	ON one.agent = two.agent       " + 
			"			 LEFT JOIN (       " + 
			"			 	SELECT       " + 
			"			 		staging.scalar_user AS agent,       " + 
			"			         count(staging.scalar_urequestref) AS missed_ticket       " + 
			"			     FROM       " + 
			"			 		staging_userrequest staging       " + 
			"			 	LEFT JOIN agent        " + 
			"			 		ON agent.name = staging.scalar_user       " + 
			"			 	LEFT JOIN  " + 
			"									( " + 
			"										SELECT       " + 
			"											ref      " + 
			"										FROM       " + 
			"											mds_itop.exception_ticket e      " + 
			"										inner join       " + 
			"											exception_header h      " + 
			"											on e.exception_header_id = h.id      " + 
			"										inner join exception_approval apprv      " + 
			"											on h.approval_id = apprv.id      " + 
			"										where      " + 
			"											h.type_id=2      " + 
			"											and h.category_id=2      " + 
			"											and apprv.status_id=4  " + 
			"									) e " + 
			"									 ON e.ref = staging.scalar_urequestref     " + 
			"			     WHERE        " + 
			"			     scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'       " + 
			"			 	AND agent.is_active=1    " + 
			"			 	AND staging.urequest_slattopassed = 'yes'        " + 
			"			 	and DATE_FORMAT(urequest_startdate,'%Y-%m-01 00:00:00') >= DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')     " + 
			"			 	AND staging.scalar_user like 'EXT%'    " + 
			"			 	and e.ref is null    " + 
			"			    GROUP BY staging.scalar_user       " + 
			"			 )	three                                    " + 
			"			 	ON one.agent = three.agent;   ", nativeQuery=true)
	public List<Object[]> getAgentTicket();
	
	@Query(value="select " + 
			"	* " + 
			"from perf_agent " + 
			"where "+
			"year(created_dt)=year(curdate()) "+   
			"AND month = month(curdate()) "+
			"AND period='monthly'  "+
			"AND category='ur' "+
			";", nativeQuery=true)
	public List<PerformanceAgent> getExistingPerformance();

}
