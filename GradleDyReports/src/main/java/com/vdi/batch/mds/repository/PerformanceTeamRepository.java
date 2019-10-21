package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.performance.PerformanceTeam;

public interface PerformanceTeamRepository extends CrudRepository<PerformanceTeam, Long>{
	
	/**
	 * sometimes assignement ticket has been moved to another agent,
	 * therefore we should delete those ticket from tbl perf_team
	 */
	@Query(value = "delete from perf_team  " + 
			"where category='sa' and period='monthly' " + 
			"and date_format(created_dt, '%Y-%m') = date_format(str_to_date('2019-08','%Y-%m'), '%Y-%m') " + 
			"and teamName not in ( " + 
			"	select " + 
			"		agent.division " + 
			"	from incident  " + 
			"	inner join agent  " + 
			"	on incident.agent_fullname=agent.name " + 
			"	where " + 
			"	DATE_FORMAT(str_to_date(start_date, '%Y-%m-%d'),'%Y-%m') = DATE_FORMAT(str_to_date('2019-08','%Y-%m'),'%Y-%m') " + 
			"	group by agent.division " + 
			");", nativeQuery=true)
	public void deleteUnassignedTeam();

}
