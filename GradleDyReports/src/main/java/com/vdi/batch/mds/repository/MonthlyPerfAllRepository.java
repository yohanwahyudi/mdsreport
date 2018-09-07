package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceOverall;

@Repository
public interface MonthlyPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where month(start_date)=month(curdate())-1 and year(start_date)=year(curdate())"
			+ "and agent_fullname like 'EXT%' "
			+ ";" ,nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where status in ('closed','resolved') and month(start_date)=month(curdate())-1 and year(start_date)=year(curdate())"
			+ "and agent_fullname like 'EXT%' "
			+ ";" ,nativeQuery=true)
	public int getTicketCount();
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where status in ('closed','resolved') and ttr_passed='no' and month(start_date)=month(curdate())-1 and year(start_date)=year(curdate())"
			+ "and agent_fullname like 'EXT%' "
			+ ";" ,nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value="select count(ref) as total_ticket from incident "
			+ "where status in ('closed','resolved') and ttr_passed='yes' and month(start_date)=month(curdate())-1 and year(start_date)=year(curdate())"
			+ "and agent_fullname like 'EXT%' "
			+ ";" ,nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall WHERE  month(created_dt)=month(curdate()) and year(created_dt)=year(curdate()) AND period='monthly' AND category='sa';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
