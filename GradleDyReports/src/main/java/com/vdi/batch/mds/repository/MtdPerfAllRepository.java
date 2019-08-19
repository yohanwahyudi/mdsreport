package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceOverall;

@Repository
public interface MtdPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value=			 
			"select count(1) as total_ticket from incident i "+
			"INNER JOIN agent agent "+
			"ON i.agent_fullname = agent.NAME "+
		    "left outer join ticket_exception e "+
            "on i.ref=e.ref and e.type='sa' "+
            "where "+ 
			 "date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01') "+     
			 "and agent_fullname like 'EXT%' "+  
             "and e.ref is null "+
			 "and agent.is_active=1 "+
             ";"			
			,nativeQuery=true)
	public int getTicketCount();
	
	
	@Query(value=
			"select count(1) as total_ticket from incident  i "+
			"INNER JOIN agent agent "+
			"ON i.agent_fullname = agent.NAME "+
			"left outer join ticket_exception e "+
            "on i.ref=e.ref and e.type='sa'  "+
			 "where "+
			 "ttr_passed='no' "+  
			 "and date_format(start_date,'%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01') "+  
			 "and agent_fullname like 'EXT%'  "+
             "and e.ref is null "+
             "and agent.is_active=1 "+
			 "; " ,nativeQuery=true)
	public int getAchievedTicketCount();
	
	
	@Query(value=
			"select count(1) as total_ticket from incident i "+
			"INNER JOIN agent agent "+	
			"ON i.agent_fullname = agent.NAME "+
			"left outer join ticket_exception e "+
            "on i.ref=e.ref and e.type='sa' "+
			" where "+
			" ttr_passed='yes' "+ 
			" and date_format(start_date,'%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01') "+  
			" and agent_fullname like 'EXT%' "+
            " and e.ref is null "+
            " and agent.is_active=1 "+
			 ";" ,nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall "
			+ "WHERE  month=month(curdate()) and year(created_dt)=year(curdate()) "
			+ "AND period='monthly' AND category='sa';", nativeQuery=true)
	public PerformanceOverall getExistingPerformance();
	

}
