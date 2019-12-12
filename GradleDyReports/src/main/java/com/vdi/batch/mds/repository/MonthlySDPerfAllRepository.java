package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.performance.PerformanceOverall;

public interface MonthlySDPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select " + 
			"	count(incident_ref) " + 
			"from staging_servicedesk " + 
			"where month(incident_startdate)=month(curdate())-1 and year(incident_startdate)=year(curdate());", nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value=" SELECT      " + 
			"			 	count(1)      " + 
			"			 FROM staging_servicedesk s      " + 
			"              " + 
			"             left join       " + 
			"				(      " + 
			"					SELECT       " + 
			"						ref      " + 
			"					FROM       " + 
			"						mds_itop.exception_ticket e      " + 
			"					inner join       " + 
			"						exception_header h      " + 
			"						on e.exception_header_id = h.id      " + 
			"					inner join exception_approval apprv      " + 
			"						on h.approval_id = apprv.id      " + 
			"					where      " + 
			"						h.type_id=1      " + 
			"						and h.category_id=2      " + 
			"						and apprv.status_id=4      " + 
			"				) e      " + 
			"			  on e.ref = s.incident_ref   " + 
			"			 where scalar_previousvalue in ('escalated_tto','new') AND scalar_newvalue = 'assigned'    " + 
			"			 	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')     " + 
			"			 	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
			"			 	   AND scalar_user like 'EXT%' AND e.ref is null; ", nativeQuery=true)
	public int getTicketCount();
	
	@Query(value=" SELECT      " + 
			"			 	count(1)      " + 
			"			 FROM staging_servicedesk s      " + 
			"              " + 
			"             left join       " + 
			"				(      " + 
			"					SELECT       " + 
			"						ref      " + 
			"					FROM       " + 
			"						mds_itop.exception_ticket e      " + 
			"					inner join       " + 
			"						exception_header h      " + 
			"						on e.exception_header_id = h.id      " + 
			"					inner join exception_approval apprv      " + 
			"						on h.approval_id = apprv.id      " + 
			"					where      " + 
			"						h.type_id=1      " + 
			"						and h.category_id=2      " + 
			"						and apprv.status_id=4      " + 
			"				) e      " + 
			"			  on e.ref = s.incident_ref   " + 
			"			 where scalar_previousvalue in ('escalated_tto','new') AND scalar_newvalue = 'assigned'    " + 
			"				   AND incident_slattopassed='no' "+
			"			 	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')     " + 
			"			 	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
			"			 	   AND scalar_user like 'EXT%' AND e.ref is null; ", nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value=" SELECT      " + 
			"			 	count(1)      " + 
			"			 FROM staging_servicedesk s      " + 
			"              " + 
			"             left join       " + 
			"				(      " + 
			"					SELECT       " + 
			"						ref      " + 
			"					FROM       " + 
			"						mds_itop.exception_ticket e      " + 
			"					inner join       " + 
			"						exception_header h      " + 
			"						on e.exception_header_id = h.id      " + 
			"					inner join exception_approval apprv      " + 
			"						on h.approval_id = apprv.id      " + 
			"					where      " + 
			"						h.type_id=1      " + 
			"						and h.category_id=2      " + 
			"						and apprv.status_id=4      " + 
			"				) e      " + 
			"			  on e.ref = s.incident_ref   " + 
			"			 where scalar_previousvalue in ('escalated_tto','new') AND scalar_newvalue = 'assigned'    " + 
			"				   AND incident_slattopassed='yes' "+
			"			 	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')     " + 
			"			 	   AND DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
			"			 	   AND scalar_user like 'EXT%' AND e.ref is null; ", nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall WHERE  month=month(curdate())-1 and "+
			"year(created_dt)=year(curdate()) AND period='monthly' AND category='sd';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
