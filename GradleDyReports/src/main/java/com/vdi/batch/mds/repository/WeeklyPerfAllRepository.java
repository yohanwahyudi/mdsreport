package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.PerformanceOverall;

public interface WeeklyPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where yearweek(start_date,3)=yearweek(curdate(),3);" ,nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value="select " + 
			"	count(ref) " + 
			"from incident " + 
			"INNER JOIN agent agent "+ 
			"ON incident.agent_fullname = agent.NAME "+ 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			//"and status in ('closed','resolved') "+
			";", nativeQuery=true)
	public int getTicketCount(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where "
			//+ "status in ('closed','resolved') and "
			+ "yearweek(start_date,3)=yearweek(curdate(),3);" ,nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select " + 
			"	count(ref) " + 
			"from incident " + 
			"INNER JOIN agent agent "+ 
			"ON incident.agent_fullname = agent.NAME "+ 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			//"and status in ('closed','resolved') "+
			"and ttr_passed='no' "+
			";", nativeQuery=true)
	public int getAchievedTicketCount(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where "
			//+ "status in ('closed','resolved') and "
			+ "ttr_passed='no' and yearweek(start_date,3)=yearweek(curdate(),3);" ,nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select " + 
			"	count(ref) " + 
			"from incident " + 
			"INNER JOIN agent agent "+ 
			"ON incident.agent_fullname = agent.NAME "+ 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			//"and status in ('closed','resolved') "+
			"and ttr_passed='yes' "+
			";", nativeQuery=true)
	public int getMissedTicketCount(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where "
			//+ "status in ('closed','resolved') and "
			+ "ttr_passed='yes' and yearweek(start_date,3)=yearweek(curdate(),3);" ,nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall "+
			"WHERE  " +
				"year(created_dt)=year(curdate()) "+   
				"AND month= :month "+
				"AND week(created_dt,3)= :week "+
				"AND period='weekly' "+
				"AND category='sa';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek(@Param("week") int week, @Param("month") int month);
	
	@Query(value="select * from perf_overall WHERE  Yearweek(created_dt, 3) = Yearweek(Curdate(), 3) AND period='weekly' AND category='sa';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
