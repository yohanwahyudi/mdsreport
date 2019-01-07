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
	
	@Query(value="select " + 
			"	* " + 
			"from staging_userrequest " + 
			"where urequest_startdate < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') "+   
			"and urequest_startdate >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and scalar_user like 'EXT%' "+
			"order by urequest_slattopassed DESC "+
			";", nativeQuery=true)
	public List<StagingUserRequest> getAllIncidentByMonth(@Param("month") int month);

}
