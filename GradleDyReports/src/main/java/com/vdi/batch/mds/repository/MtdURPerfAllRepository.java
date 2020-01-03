package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceOverall;

@Repository
public interface MtdURPerfAllRepository extends CrudRepository<PerformanceOverall, Long> {
	
	@Query(value="select        " + 
			"			 				count(1)       " + 
			"			 			from staging_userrequest   ur     " + 
			"			 	    		 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME    " + 
			"							 LEFT JOIN  " + 
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
			"									 ON e.ref = ur.scalar_urequestref      " + 
			"			 			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
			"			 			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'      " + 
			"			 			and scalar_user like 'EXT%'      " + 
			"			 			and agent.is_active=1    " + 
			"			            and e.ref is null; ",
			 nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select        " + 
			"			 				count(1)       " + 
			"			 			from staging_userrequest   ur     " + 
			"			 	    		 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME    " + 
			"							 LEFT JOIN  " + 
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
			"									 ON e.ref = ur.scalar_urequestref      " + 
			"			 			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
			"			 			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'      " + 
			"						and urequest_slattopassed='no'  "+
			"			 			and scalar_user like 'EXT%'      " + 
			"			 			and agent.is_active=1    " + 
			"			            and e.ref is null "+
			";", nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select        " + 
			"			 				count(1)       " + 
			"			 			from staging_userrequest   ur     " + 
			"			 	    		 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME    " + 
			"							 LEFT JOIN  " + 
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
			"									 ON e.ref = ur.scalar_urequestref      " + 
			"			 			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
			"			 			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'      " + 
			"						and urequest_slattopassed='yes'  "+
			"			 			and scalar_user like 'EXT%'      " + 
			"			 			and agent.is_active=1    " + 
			"			            and e.ref is null "+
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