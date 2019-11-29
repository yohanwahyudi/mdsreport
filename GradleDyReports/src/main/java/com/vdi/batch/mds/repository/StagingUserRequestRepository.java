package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.staging.StagingUserRequest;

public interface StagingUserRequestRepository extends CrudRepository<StagingUserRequest, Long>{
	
	@Query(value="select " + 
			"	* " + 
			"from staging_userrequest " + 
			"where year(urequest_startdate)=year(curdate()) "+   
			"and month(urequest_startdate)= :month "+
			"and week(urequest_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and scalar_user like 'EXT%' "+
			"order by urequest_slattopassed DESC "+
			";", nativeQuery=true)
	public List<StagingUserRequest> getAllIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value=" select      " + 
			"			 	*      " + 
			"			 from staging_userrequest ur     " + 
			"			 	    		 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME    " + 
			"							 left join       " + 
			"							(      " + 
			"								SELECT       " + 
			"									ref      " + 
			"								FROM       " + 
			"									mds_itop.exception_ticket e      " + 
			"								inner join       " + 
			"									exception_header h      " + 
			"									on e.exception_header_id = h.id      " + 
			"								inner join exception_approval apprv      " + 
			"									on h.approval_id = apprv.id      " + 
			"								where      " + 
			"									h.type_id=2      " + 
			"									and h.category_id=2    " + 
			"									and apprv.status_id=4      " + 
			"							) e      " + 
			"							on e.ref = ur.scalar_urequestref 	     " + 
			"			 where DATE_FORMAT(ur.urequest_startdate,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
			"			 and DATE_FORMAT(ur.urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
			"			 and ur.scalar_previousvalue in ('escalated_tto','new') and ur.scalar_newvalue = 'assigned'    " + 
			"			 and ur.scalar_user like 'EXT%'    " + 
			"			 and agent.is_active=1    " + 
			"			 and e.ref is null    " + 
			"			 order by urequest_slattopassed DESC    "+
			";", nativeQuery=true)
	public List<StagingUserRequest> getAllIncidentByMonth(@Param("month") int month);
	
	@Query(value=" select      " + 
			"			 	*      " + 
			"			 from staging_userrequest ur     " + 
			"			 	    		 LEFT JOIN agent         " + 
			"			 	 			   ON ur.scalar_user = agent.NAME  " + 
			"							 left join       " + 
			"								(      " + 
			"									SELECT       " + 
			"										ref      " + 
			"									FROM       " + 
			"										mds_itop.exception_ticket e      " + 
			"									inner join       " + 
			"										exception_header h      " + 
			"										on e.exception_header_id = h.id      " + 
			"									inner join exception_approval apprv      " + 
			"										on h.approval_id = apprv.id      " + 
			"									where      " + 
			"										h.type_id=2      " + 
			"										and h.category_id=2    " + 
			"										and apprv.status_id=4      " + 
			"								) e      " + 
			"								on e.ref = ur.scalar_urequestref   " + 
			"							    " + 
			"			 where DATE_FORMAT(ur.urequest_startdate,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
			"			 and ur.scalar_previousvalue in ('escalated_tto','new') and ur.scalar_newvalue = 'assigned'    " + 
			"			 and ur.scalar_user like 'EXT%'    " + 
			"			 and agent.is_active=1    " + 
			"			 and e.ref is null    " + 
			"			 order by ur.urequest_slattopassed DESC    "+
			";", nativeQuery=true)
	public List<StagingUserRequest> getAllIncidentByMtd();

}
