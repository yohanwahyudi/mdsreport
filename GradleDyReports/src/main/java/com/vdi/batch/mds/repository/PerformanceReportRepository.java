package com.vdi.batch.mds.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vdi.model.Incident;
import com.vdi.model.ServiceDesk;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.performance.PerformanceTeam;

//@Repository
public interface PerformanceReportRepository {
	
	@Query(value="select p.totalTicket, p.totalAchieved, p.totalMissed, p.achievement "+
				"from perf_overall p where p.period = :period and p.category = :category "+
				"and p.created_dt >= :dtFrom and p.created_dt <= :dtTo", nativeQuery=true)
	public List<PerformanceOverall> getPerformanceAllList(@Param("period") String period, @Param("category") String category, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo);
	
	@Query(value="select p.teamName, p.totalTicket, p.totalAchieved, p.totalMissed, p.achievement "+
			"from perf_team p where p.period = :period and p.category = :category "+
			"and o.created_dt >= :dtFrom and p.created_dt <= :dtTo", nativeQuery=true)
	public List<PerformanceTeam> getPerformanceTeamList(@Param("period") String period, @Param("category") String category, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo);
	
	@Query(value="select p.division, p.totalTicket, p.totalAchieved, p.totalMissed, p.achievement "+
			"p.totalAssigned, p.totalPending  "+
			"from perf_agent	 p where p.period = :period and p.category = :category "+
			"and p.created_dt >= :dtFrom and p.created_dt <= :dtTo", nativeQuery=true)
	public List<PerformanceAgent> getPerformanceAgentList(@Param("period") String period, @Param("category") String category, @Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo);	
	
	@Query(value="SELECT ref, title, status, CONCAT(start_date,' ',start_time) AS date, agent_fullname AS agent, priority, person_org_name AS organization, " + 
			"resolution_date AS resolutionDate, user_satisfaction AS userSatisfaction, IF(ttr_passed='no','achieved','missed') as ttrPassed " + 
			"FROM incident WHERE start_date >= :dtFrom AND start_date <= :dateTo;", nativeQuery=true)
	public List<Incident> getSupportAgentIncidentList(@Param("dtFrom") Date dtFrom, @Param("dtTo")Date dtTo);	
	
	@Query(value="SELECT ref, title, status, CONCAT(start_date,' ',start_time) AS date, agent_fullname AS agent, priority, person_org_name AS organization, " + 
			"resolution_date AS resolutionDate, user_satisfaction AS userSatisfaction, IF(ttr_passed='no','achieved','missed') as ttrPassed " + 
			"FROM incident WHERE start_date >= :dtFrom AND start_date <= :dateTo "+
			"AND status = :status;", nativeQuery=true)
	public List<Incident> getSupportAgentIncidentByStatusList(@Param("dtFrom") Date dtFrom, @Param("dtTo") Date dtTo, @Param("status") String status);
	
	@Query(value="SELECT incident_ref, scalar_user, incident_title, CONCAT(incident_startdate,' ',incident_starttime) AS date,  " + 
			"incident_priority, incident_status, incident_caller, person_organization, incident_usersatisfaction " + 
			"FROM staging_servicedesk " + 
			"WHERE incident_startdate >= :dtFrom AND incident_startdate <= :dtTo;", nativeQuery=true)
	public List<ServiceDesk> getServiceDeskIncidentList(Date dtFrom, Date dtTo);

}
