package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.Problem;

@Repository
public interface MonthlyProblemRepository extends CrudRepository<Problem, Long>{
	
	@Query(value=
			"select " + 
					"	count(problem.ref) as total,  " + 
					"    count(case  " + 
					"		when " + 
					"			status in ('Assigned','New') " + 
					"        then problem.ref " + 
					"		end " + 
					"	) as open, " + 
					"    count(case " + 
					"		when " + 
					"			status in ('Resolved','Closed') " + 
					"		then problem.ref " + 
					"        end " + 
					"	) as resolved_closed " + 
					"from staging_problem as problem " + 
					"	right join agent " + 
					"	on agent.name = problem.agent_id_friendlyname " + 
					"where " + 
					"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " + 
					"	and start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00')  " + 
					"    and start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01');", nativeQuery=true)
	public List<Object[]> getProblemSummary();
	
	@Query(value="select " + 
			"	agent.team_name,  " + 
			"    count(problem.ref) as ticket_count,  " + 
			"    round(count(problem.ref)*100/ " + 
			"		    (select count(1) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA')),2) as contribution " + 
			"from staging_problem as problem " + 
			"	right join agent " + 
			"	on agent.name = problem.agent_id_friendlyname " + 
			"   AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') "+
			"   AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " + 
			"group by agent.team_name " + 
			"order by team_name asc;", nativeQuery=true)
	public List<Object[]> getProblemAchievementByTeam();
	
	@Query(value="select " + 
			"	agent.team_name,  " + 
			"    agent.name, " + 
			"    count(problem.ref) as ticket_count,  " + 
			"    round(count(problem.ref)*100/ " + 
			"		    (select count(1) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ) " + 
			"		  ,2) as contribution, " + 
			"	ifnull(round(case  " + 
			"		when agent.team_name='RMS, ReSA, DWH'  " + 
			"        then count(problem.ref)*100/ " + 
			"			(select count(problem.ref) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name = 'RMS, ReSA, DWH' AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ) " + 
			"		when agent.team_name='WMS'  " + 
			"        then count(problem.ref)*100/ " + 
			"			(select count(problem.ref) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name = 'WMS' AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ) " + 
			"		when agent.team_name='MFP, OBIEE'  " + 
			"        then count(problem.ref)*100/ " + 
			"			(select count(problem.ref) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name = 'MFP, OBIEE' AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ) " + 
			"		when agent.team_name='DWI, RDW'  " + 
			"        then count(problem.ref)*100/ " + 
			"			(select count(problem.ref) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name = 'DWI, RDW' AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ) " + 
			"		when agent.team_name='RIB RSIM, ReIM, Alloc'  " + 
			"        then count(problem.ref)*100/ " + 
			"			(select count(problem.ref) from staging_problem as problem join agent on agent.name=problem.agent_id_friendlyname " + 
			"			 where agent.team_name = 'RIB RSIM, ReIM, Alloc' AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ) " + 
			"	end,2),0) as team_contribution " + 
			"from staging_problem as problem " + 
			"	right join agent " + 
			"	on agent.name = problem.agent_id_friendlyname " + 
			"   AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') "+
			"   AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " + 
			"group by agent.name " + 
			"order by team_name asc;", nativeQuery=true)
	public List<Object[]> getProblemAchievementByAgent();
	
	@Query(value="select " + 
			"	problem.ref,  " + 
			"    problem.title,  " + 
			"    problem.start_date,  " + 
			"    problem.resolution_date, " + 
			"    problem.status,  " + 
			"    problem.priority, " + 
			"    problem.caller_name, " + 
			"    problem.agent_id_friendlyname as agent, " + 
			"    agent.team_name " + 
			"from staging_problem as problem  " + 
			"inner join agent " + 
			"	on agent.name = problem.agent_id_friendlyname " + 
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " + 
			" AND start_date < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') AND start_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"order by team_name asc;", nativeQuery=true)
	public List<Object[]> getLastMonthProblemList();
	
	@Query(value="select count(1) as total,  " + 
			"count( " + 
			"	case when status in ('Assigned','New') then ref " + 
			"end) as open, " + 
			"count( " + 
			"	case when status in ('Resolved','Closed') then ref " + 
			"end) as resolved_closed " + 
			" " + 
			"from staging_problem problem " + 
			"where " + 
			"agent_id_friendlyname like 'EXT%'" +
			"and year(start_date) = year(now()); "+
			";", nativeQuery=true)
	public List<Object[]> getAllProblemSummary();
	
	@Query(value="select  " + 
			"agent.team_name as team, agent.name, problem.priority, count(1) as total " + 
			"from staging_problem problem  " + 
			"inner join agent agent " + 
			"on problem.agent_id_friendlyname = agent.name " + 
			"where  " + 
			"agent_id_friendlyname like 'EXT%' " + 
			"and " + 
			"status not in ('Resolved','Closed') " +
			"and "+
			"year(start_date) = year(now()) "+
			"group by name, priority " + 
			"order by team, name asc;", nativeQuery=true)
	public List<Object[]> getAllOpenedProblemByTeamAgent();
	
	@Query(value="select " + 
			"ref, title, priority, agent_id_friendlyname, caller_name, start_date " + 
			"from staging_problem problem " + 
			"where " + 
			"( agent_id_friendlyname like 'EXT%' " +
			"or agent_id_friendlyname = '' )" + 
			"and " + 
			"status not in ('Resolved','Closed') " + 
			"and "+
			"year(start_date) = year(now()) "+
			"order by priority, agent_id_friendlyname asc;", nativeQuery=true)
	public List<Problem> getAllOpenedProblemList();
	
}
