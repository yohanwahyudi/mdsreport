package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.Change;

public interface MonthlyChangeRepository extends CrudRepository<Change, Long>{
	

	@Query(value="select " + 
			"	count(changes.ref) as total,  " + 
			"    count(case " + 
			"		when " + 
			"			status in ('Approved','Assigned','Implemented','New','Planned and scheduled','Validated') " + 
			"		then changes.ref " + 
			"        end " + 
			"	) as open, " + 
			"    count(case " + 
			"		when " + 
			"			status in ('Closed') " + 
			"		then changes.ref " + 
			"        end " + 
			"	) as closed, " + 
			"	count(case  " + 
			"		when " + 
			"			status in ('Rejected','Not approved') " + 
			"        then changes.ref " + 
			"		end " + 
			"	) as rejected " + 
			"from staging_change as changes " + 
			"	inner join agent " + 
			"	on agent.name = changes.agent_id_friendlyname " + 
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') "+
			"	AND agent.is_active=1 "+
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			";", nativeQuery=true)
	public List<Object[]> getChangeSummary();
	
	@Query(value="select " + 
			"	agent.team_name,  " + 
			"    count(changes.ref) as ticket_count,  " + 
			"    round(count(changes.ref)*100/ " + 
			"		    (select count(1) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') "+
			"			 AND agent.is_active=1 " +
			"			 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ),2) as contribution " + 
			"from staging_change as changes " + 
			"	right join agent " + 
			"	on agent.name = changes.agent_id_friendlyname " + 
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " + 
			"AND agent.is_active=1 " +
			"group by agent.team_name " + 
			"order by team_name asc;", nativeQuery=true)
	public List<Object[]> getChangeAchievementByTeam();
	
	@Query(value="select " + 
			"	agent.team_name,  " + 
			"    agent.name, " + 
			"    count(changes.ref) as ticket_count,  " + 
			"    round(count(changes.ref)*100/ " + 
			"		    (select count(1) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') AND agent.is_active=1 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') ),2) as contribution, " + 
			"	ifnull(round(case " + 
			"		when agent.team_name='RMS, ReSA, DWH'  " + 
			"        then count(changes.ref)*100/ " + 
			"			(select count(changes.ref) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name = 'RMS, ReSA, DWH') AND agent.is_active=1 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') " + 
			"		when agent.team_name='WMS'  " + 
			"        then count(changes.ref)*100/ " + 
			"			(select count(changes.ref) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name = 'WMS') AND agent.is_active=1 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') " + 
			"		when agent.team_name='MFP, OBIEE'  " + 
			"        then count(changes.ref)*100/ " + 
			"			(select count(changes.ref) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name = 'MFP, OBIEE') AND agent.is_active=1 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') " + 
			"		when agent.team_name='DWI, RDW'  " + 
			"        then count(changes.ref)*100/ " + 
			"			(select count(changes.ref) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name = 'DWI, RDW') AND agent.is_active=1 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') " + 
			"		when agent.team_name='RIB RSIM, ReIM, Alloc'  " + 
			"        then count(changes.ref)*100/ " + 
			"			(select count(changes.ref) from staging_change as changes join agent on agent.name=changes.agent_id_friendlyname " + 
			"			 where agent.team_name = 'RIB RSIM, ReIM, Alloc') AND agent.is_active=1 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') " + 
			"    end,2),0) as team_contribution " + 
			"from staging_change as changes " + 
			"	right join agent " + 
			"	on agent.name = changes.agent_id_friendlyname " + 
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " + 
			"	AND agent.is_active=1 "+
			"group by agent.name " + 
			"order by team_name asc;", nativeQuery=true)
	public List<Object[]> getChangeAchievementByAgent();
	
	@Query(value="select " + 
			"	changes.ref,  " + 
			"    changes.title,  " + 
			"    changes.start_date,  " + 
			"    changes.close_date, " + 
			"    changes.status,  " + 
			"    changes.caller_name, " + 
			"    changes.agent_id_friendlyname as agent, " + 
			"    agent.team_name " + 
			"from staging_change as changes  " + 
			"right join agent " + 
			"	on agent.name = changes.agent_id_friendlyname " + 
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+
			"   AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"where " + 
			"	agent.team_name not in ('DCU','OCC','OTHERS','SERVER','NETWORK','OFA') " +
			"	AND agent.is_active=1 "+
			"order by team_name asc;", nativeQuery=true)
	public List<Object[]> getLastMonthChangeList();
	
	@Query(value="select    " + 
			"				count(changes.ref) as total,     " + 
			"			    count(case    " + 
			"					when    " + 
			"						status in ('Approved','Assigned','Implemented','New','Planned and scheduled','Validated')    " + 
			"					then changes.ref    " + 
			"			        end    " + 
			"				) as open,    " + 
			"			    count(case    " + 
			"					when    " + 
			"						status in ('Closed')    " + 
			"					then changes.ref    " + 
			"			        end    " + 
			"				) as closed,    " + 
			"				count(case     " + 
			"					when    " + 
			"						status in ('Rejected','Not approved')    " + 
			"			        then changes.ref    " + 
			"					end    " + 
			"				) as rejected    " + 
			"			from staging_change as changes    " + 
			"			where    " + 
			"				agent_id_friendlyname like 'EXT%' " +
			"				and "+
			"				year(start_date) = year(now()) "+
			";", nativeQuery=true)
	public List<Object[]> getAllChangeSummary();
	
	@Query(value="select agent.team_name as team, agent.name as agent, count(changes.ref) as count " + 
			"from staging_change as changes " + 
			"			join agent " + 
			"			on agent.name = changes.agent_id_friendlyname " + 
			"where " + 
			"	status in ('Approved','Assigned','Implemented','New','Planned and scheduled','Validated') " + 
			//"	AND agent.is_active=1 "+
			"   and year(start_date) = year(now()) "+
			"group by agent.name " + 
			"order by team asc;", nativeQuery=true)
	public List<Object[]> getAllOpenedChangeByTeamAgent();
	
	@Query(value="SELECT ref, agent_id_friendlyname as agent, title, caller_name, start_date " + 
			"FROM staging_change where agent_id_friendlyname like 'EXT%' " + 
			"and status in ('Approved','Assigned','Implemented','New','Planned and scheduled','Validated') " +
			"and year(start_date) = year(now()) " +
			"order by agent_id_friendlyname asc;", nativeQuery=true)
	public List<Change> getAllOpenedChangeList();

}
