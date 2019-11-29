package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.performance.PerformanceAgent;

public interface PerformanceAgentRepository extends CrudRepository<PerformanceAgent, Long>{

	/**
	 * sometimes assignement ticket has been moved to another agent,
	 * therefore we should delete those ticket from tbl perf_agent
	 */
	@Query(value = "  delete from perf_agent where category='sa' and period='monthly'      " + 
			"			 and date_format(created_dt, '%Y-%m') = date_format(now(), '%Y-%m')      " + 
			"			 and agentName not in (      " + 
			"			 	select agent_fullname from incident     " + 
			"			 		 		    left outer join       " + 
			"					 				(      " + 
			"					 					SELECT       " + 
			"					 						ref      " + 
			"					 					FROM       " + 
			"					 						mds_itop.exception_ticket e      " + 
			"					 					inner join       " + 
			"					 						exception_header h      " + 
			"					 						on e.exception_header_id = h.id      " + 
			"					 					inner join exception_approval apprv      " + 
			"					 						on h.approval_id = apprv.id      " + 
			"					 					where      " + 
			"					 						h.type_id=1      " + 
			"					 						and h.category_id=1      " + 
			"					 						and apprv.status_id=4      " + 
			"					 				) e      " + 
			"					 			on e.ref = incident.ref     	     " + 
			"			 		where      " + 
			"			 			DATE_FORMAT(str_to_date(start_date, '%Y-%m-%d'),'%Y-%m') = DATE_FORMAT(now(),'%Y-%m')     " + 
			"			 			AND e.ref is null     " + 
			"			 		group by agent_fullname      " + 
			"			 );  " , nativeQuery=true)
	public void deleteUnassignedAgent();
	
}
