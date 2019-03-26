package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.staging.StagingAgent;

public interface StagingAgentRepository extends CrudRepository<StagingAgent, Integer>{

	@Query(value="select stage.* "
			+ "from agent as main "
			+ "right outer join staging_agent as stage "
			+ "on main.email=stage.email where main.email is null ", nativeQuery=true)
	public List<StagingAgent> getDataForInsert();
	
	@Query(value="update agent  " + 
			"inner join staging_agent as staging " + 
			"on agent.email=staging.email " + 
			"set agent.name=staging.name, agent.division=staging.division, agent.organization_name=staging.organization_name, " + 
			"agent.resource=staging.resource, agent.team_name=staging.team_name, agent.is_active=staging.is_active;", nativeQuery=true)
	public void updateAgentTableMaster();
	
	@Query(value="insert into agent " + 
			"select staging.* " + 
			"from staging_agent as staging " + 
			"left outer join agent as agent " + 
			"on staging.email=agent.email " + 
			"where agent.email is null;", nativeQuery=true)
	public void insertAgentTableMaster();
	
	@Query(value="update seq1 set next_val= 1", nativeQuery=true)
	public void resetSequenceTo1();
	
	@Query(value="SET SQL_SAFE_UPDATES = 0", nativeQuery=true)
	public void disableSafeUpdates();
	
	@Query(value="SET SQL_SAFE_UPDATES = 1", nativeQuery=true)
	public void enableSafeUpdates();
}
