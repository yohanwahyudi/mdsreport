package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.ServiceDesk;
import com.vdi.model.staging.StagingServiceDesk;

public interface ServiceDeskRepository extends CrudRepository<ServiceDesk, Long>{

	@Query(value="select " + 
			"	* " + 
			"from staging_servicedesk " + 
			"where year(incident_startdate)=year(curdate()) "+   
			"and month(incident_startdate)= :month "+
			"and week(incident_startdate,3)= :week "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and scalar_user like 'EXT%' "+
			"order by incident_ref "+
			";", nativeQuery=true)
	public List<StagingServiceDesk> getAllIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value="select " + 
			"	* " + 
			"from staging_servicedesk " + 
			"where DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"and scalar_previousvalue in ('escalated_tto','new') and scalar_newvalue = 'assigned' "+
			"and scalar_user like 'EXT%' "+
			"order by incident_ref "+
			";", nativeQuery=true)
	public List<StagingServiceDesk> getAllIncidentByMonth(@Param("month") int month);
	
}
