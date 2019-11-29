package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.staging.StagingServiceDesk;

public interface StagingServiceDeskRepository extends CrudRepository<StagingServiceDesk, Long> {

	@Query(value = "select   	scalar_user   from "+ 
		 "(select scalar_user from staging_servicedesk where scalar_user like '%EXT%' group by scalar_user) AS agentIncident "+ 
		 "where agentIncident.scalar_user not in (select name from agent group by name);", nativeQuery = true)
	public List<Object[]> getUnregisteredAgent();
	
	@Query(value="select " + 
			"	* " + 
			"from staging_servicedesk " + 
			"where year(incident_startdate)=year(curdate()) "+   
			"and month(incident_startdate)= :month "+
			"and week(incident_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and scalar_user like 'EXT%' "+
			"order by incident_slattopassed DESC  "+
			";", nativeQuery=true)
	public List<StagingServiceDesk> getAllIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value=" select      " + 
			"			 	*      " + 
			"			 from staging_servicedesk      " + 
			"			 	    LEFT JOIN agent         " + 
			"			 	 		 ON staging_servicedesk.scalar_user = agent.NAME         " + 
			"			 		 " + 
			"                    left join       " + 
			"						(      " + 
			"							SELECT       " + 
			"								ref      " + 
			"							FROM       " + 
			"								mds_itop.exception_ticket e      " + 
			"							inner join       " + 
			"								exception_header h      " + 
			"								on e.exception_header_id = h.id      " + 
			"							inner join exception_approval apprv      " + 
			"								on h.approval_id = apprv.id      " + 
			"							where      " + 
			"								h.type_id=1      " + 
			"								and h.category_id=2    " + 
			"								and apprv.status_id=4      " + 
			"						) e      " + 
			"						on e.ref = staging_servicedesk.incident_ref 			  " + 
			"             where DATE_FORMAT(incident_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
			"			 and DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
			"			 and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'    " + 
			"			 and scalar_user like 'EXT%'    " + 
			"			 and agent.is_active=1    " + 
			"			 and e.ref is null    " + 
			"			 order by incident_slattopassed DESC     " + 
			";", nativeQuery=true)
	public List<StagingServiceDesk> getAllIncidentByMonth(@Param("month") int month);
	
	@Query(value=" select      " + 
			"			 	*      " + 
			"			 from staging_servicedesk      " + 
			"			 	    LEFT JOIN agent         " + 
			"			 	 		 ON staging_servicedesk.scalar_user = agent.NAME         " + 
			"			 		left join       " + 
			"						(      " + 
			"							SELECT       " + 
			"								ref      " + 
			"							FROM       " + 
			"								mds_itop.exception_ticket e      " + 
			"							inner join       " + 
			"								exception_header h      " + 
			"								on e.exception_header_id = h.id      " + 
			"							inner join exception_approval apprv      " + 
			"								on h.approval_id = apprv.id      " + 
			"							where      " + 
			"								h.type_id=1      " + 
			"								and h.category_id=2    " + 
			"								and apprv.status_id=4      " + 
			"						) e      " + 
			"						on e.ref = staging_servicedesk.incident_ref    " + 
			"			 where DATE_FORMAT(incident_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
			"			 and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned'    " + 
			"			 and scalar_user like 'EXT%'    " + 
			"			 and agent.is_active=1    " + 
			"			 and e.ref is null    " + 
			"			 order by incident_slattopassed DESC   "+
			";", nativeQuery=true)
	public List<StagingServiceDesk> getAllIncidentByMtd();

}
