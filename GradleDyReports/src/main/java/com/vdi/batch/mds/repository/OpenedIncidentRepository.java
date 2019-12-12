package com.vdi.batch.mds.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.Incident;

public interface OpenedIncidentRepository extends CrudRepository<Incident, Long>{
	
	@Query(value="select * from incident where status not in ('Resolved','Closed') and agent_fullname like 'EXT%';", nativeQuery=true)
	public List<Incident> getOpenedIncident();
	
	@Query(value="SELECT incident FROM Incident incident WHERE ref IN :refList ")
	public List<Incident> findByRefList(@Param("refList") Iterable<String> refList );

}
