package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.staging.StagingAgent;

public interface StagingAgentRepository extends CrudRepository<StagingAgent, Integer>{

	@Query(value="select stage.* "
			+ "from agent as main "
			+ "right outer join staging_agent as stage "
			+ "on main.email=stage.email where main.email is null", nativeQuery=true)
	public List<StagingAgent> getDataForInsert();
	
	@Query(value="update seq1 set next_val= 1", nativeQuery=true)
	public void resetSequenceTo1();
	
	@Query(value="SET SQL_SAFE_UPDATES = 0", nativeQuery=true)
	public void disableSafeUpdates();
	
	@Query(value="SET SQL_SAFE_UPDATES = 1", nativeQuery=true)
	public void enableSafeUpdates();
}
