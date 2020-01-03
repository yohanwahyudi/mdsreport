package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceOverall;

public interface MonthlyURPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_userrequest " + 
			"where yearweek(urequest_startdate,3)=yearweek(curdate(),3)-1 and scalar_user like 'EXT%';", nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value=" select        " + 
			"			 				count(1)       " + 
			"			 			from staging_userrequest   ur     " + 
			"			 				 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME   " + 
			"							 left join       " + 
			"									(      " + 
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
			"											and apprv.status_id=4      " +
			"											and DATE_FORMAT(start_dt,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')      " +
			"											and DATE_FORMAT(start_dt,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')            " +
			"									) e      " + 
			"								  on e.ref = ur.scalar_urequestref       " + 
			"			 			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')         " + 
			"			 			and DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')      " + 
			"			 			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'      " + 
			"			 			and scalar_user like 'EXT%'      " + 
			"			 			and agent.is_active=1    " + 
			"			            and e.ref is null; ",
			 nativeQuery=true)
	public int getTicketCount(@Param("month") int month);
	
	@Query(value=
			" select        " + 
					"			 				count(1)       " + 
					"			 			from staging_userrequest   ur     " + 
					"			 				 LEFT JOIN agent         " + 
					"			 	 			   ON ur.scalar_user = agent.NAME   " + 
					"							 left join       " + 
					"									(      " + 
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
					"											and apprv.status_id=4      " + 
					"											and DATE_FORMAT(start_dt,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')      " +
					"											and DATE_FORMAT(start_dt,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')            " +
					"									) e      " + 
					"								  on e.ref = ur.scalar_urequestref       " + 
					"			 			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')         " + 
					"						and urequest_slattopassed='no'  "+
					"			 			and DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')      " + 
					"			 			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'      " + 
					"			 			and scalar_user like 'EXT%'      " + 
					"			 			and agent.is_active=1    " + 
					"			            and e.ref is null; ", nativeQuery=true)
	public int getAchievedTicketCount(@Param("month") int month);
	
	@Query(value=" select        " + 
			"			 				count(1)       " + 
			"			 			from staging_userrequest   ur     " + 
			"			 				 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME   " + 
			"							 left join       " + 
			"									(      " + 
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
			"											and apprv.status_id=4      " +
			"											and DATE_FORMAT(start_dt,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')      " +
			"											and DATE_FORMAT(start_dt,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')            " +
			"									) e      " + 
			"								  on e.ref = ur.scalar_urequestref       " + 
			"			 			where DATE_FORMAT(urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')         " + 
			"						and urequest_slattopassed='yes'  "+
			"			 			and DATE_FORMAT(urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')      " + 
			"			 			and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'      " + 
			"			 			and scalar_user like 'EXT%'      " + 
			"			 			and agent.is_active=1    " + 
			"			            and e.ref is null; ", nativeQuery=true)
	public int getMissedTicketCount(@Param("month") int month);
	
	@Query(value="select * from perf_overall "+
			"WHERE  " +
				"DATE_FORMAT(created_dt,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+   
				"AND DATE_FORMAT(created_dt,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
				"AND period='monthly' "+
				"AND category='ur';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisMonth( int month);
	
	@Query(value="select * from perf_overall WHERE  " +
			"DATE_FORMAT(created_dt,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(created_dt,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"AND period='monthly' AND category='ur';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisMonth();

}
