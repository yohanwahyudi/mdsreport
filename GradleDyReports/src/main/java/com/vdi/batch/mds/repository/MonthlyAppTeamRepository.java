package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.performance.PerformanceAgent;

public interface MonthlyAppTeamRepository extends CrudRepository<PerformanceAgent, Long> {

	@Query(value = 
			"  select   	          " + 
					"		 agent.team_name,            " + 
					"		 agent.name ,          " + 
					"		 count(incident.ref) as ticket_count,          " + 
					"		 count(case when ttr_passed='no' then ref end) as achieved,          " + 
					"		 count(case when ttr_passed='yes' then ref end) as missed,          " + 
					"		 round(count(case when ttr_passed='no' then ref end)*100/count(incident.ref),2) as achievement,          " + 
					"		 round(count(incident.ref)*100/          " + 
					"		 (select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"		  where agent.team_name not in ('DCU','OCC','SERVER','NETWORK','OFA','SUPPLIER PORTAL TEAM', 'Service Desk')        " + 
					"		  AND agent.is_active=1        " + 
					"		  AND incident.team_id not in (30278)    " + 
					"		  AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"		  AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')          " + 
					"		 ),2) as contribution,         " + 
					"				  " + 
					"		 ifnull(case          " + 
					"			 when agent.team_name='RMS, ReSA, DWH'         " + 
					"			 then round(count(incident.ref)*100/(select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"				 where agent.team_name = 'RMS, ReSA, DWH'         " + 
					"				 AND agent.is_active=1         " + 
					"				 AND incident.team_id not in (30278)    " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')),2)          " + 
					"			 when agent.team_name='WMS'         " + 
					"			 then round(count(incident.ref)*100/(select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"				 where agent.team_name = 'WMS'         " + 
					"				 AND agent.is_active=1            " + 
					"				 AND incident.team_id not in (30278)    " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')),2)         " + 
					"			 when agent.team_name='MFP, OBIEE'         " + 
					"			 then round(count(incident.ref)*100/(select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"				 where agent.team_name = 'MFP, OBIEE'         " + 
					"				 AND agent.is_active=1    " + 
					"				 AND incident.team_id not in (30278)				     " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')),2)              " + 
					"			 when agent.team_name='DWI, RDW'         " + 
					"			 then round(count(incident.ref)*100/(select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"				 where agent.team_name = 'DWI, RDW'         " + 
					"				 AND agent.is_active=1        " + 
					"				 AND incident.team_id not in (30278)    " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')),2) " + 
					"             when agent.team_name='OTHERS'         " + 
					"			 then round(count(incident.ref)*100/(select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"				 where agent.team_name = 'OTHERS'         " + 
					"				 AND agent.is_active=1        " + 
					"				 AND incident.team_id not in (30278)    " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')),2)       " + 
					"			 when agent.team_name='RIB RSIM, ReIM, Alloc'         " + 
					"			 then round(count(incident.ref)*100/(select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname          " + 
					"				 where agent.team_name = 'RIB RSIM, ReIM, Alloc'         " + 
					"				 AND agent.is_active=1             " + 
					"				 AND incident.team_id not in (30278)    " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')          " + 
					"				 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')),2)         " + 
					"			 end,0) as team_contribution         " + 
					"		 from incident    " + 
					"         JOIN agent          " + 
					"			 on agent.name=incident.agent_fullname          " + 
					"			 AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
					"			 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')          " + 
					"			 WHERE agent.team_name not in ('DCU','OCC','SERVER','NETWORK','OFA','SUPPLIER PORTAL TEAM', 'Service Desk')         " + 
					"			 AND agent.is_active=1            " + 
					"			 AND incident.team_id not in (30278)    " + 
					"		 group by agent.name   order by team_name desc;   "	
			, nativeQuery = true)
	public List<Object[]> getAgentPerformance();

	@Query(value = "select    " + 
			"	 agent.team_name,   count(incident.ref) as ticket_count,  " + 
			"	 count(case when ttr_passed='no' then ref end) as achieved,  " + 
			"	 count(case when ttr_passed='yes' then ref end) as missed,  " + 
			"	 round(count(case when ttr_passed='no' then ref end)*100/count(incident.ref),2) as achievement,  " + 
			"	 round(count(incident.ref)*100/  " + 
			"		  (select count(incident.ref) from incident join agent on agent.name=incident.agent_fullname  " + 
			"			 where agent.team_name not in ('DCU','OCC','SERVER','NETWORK','OFA','SUPPLIER PORTAL TEAM', 'Service Desk') " + 
			"			 AND agent.is_active=1   " + 
			"			 AND incident.team_id not in (30278) " + 
			"			 AND DATE_FORMAT(start_date,'%Y-%m-01')  < DATE_FORMAT(NOW(),'%Y-%m-01')  " + 
			"			 AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')  " + 
			"		   ),2) as contribution   " + 
			"from incident    JOIN agent   on agent.name=incident.agent_fullname  " + 
			"WHERE agent.team_name not in ('DCU','OCC','SERVER','NETWORK','OFA','SUPPLIER PORTAL TEAM', 'Service Desk') " + 
			"	  AND agent.is_active=1   " + 
			"      AND incident.team_id not in (30278) " + 
			"	  AND DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  " + 
			"	  AND DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')  " + 
			"group by agent.team_name   order by contribution desc;", nativeQuery = true)
	public List<Object[]> getTeamPerformance();

}
