package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.performance.PerformanceAgent;

public interface PerformanceAgentRepository extends CrudRepository<PerformanceAgent, Long>{

	/**
	 * sometimes assignement ticket has been moved to another agent,
	 * therefore we should delete those ticket from tbl perf_agent
	 */
	@Query(value = " delete from perf_agent where category='sa' and period='monthly' " + 
			"and date_format(created_dt, '%Y-%m') = date_format(now(), '%Y-%m') " + 
			"and agentName not in ( " + 
			"	select agent_fullname from incident " + 
			"		where " + 
			"			DATE_FORMAT(str_to_date(start_date, '%Y-%m-%d'),'%Y-%m') = DATE_FORMAT(now(),'%Y-%m') " + 
			"		group by agent_fullname " + 
			");", nativeQuery=true)
	public void deleteUnassignedAgent();
	
}
