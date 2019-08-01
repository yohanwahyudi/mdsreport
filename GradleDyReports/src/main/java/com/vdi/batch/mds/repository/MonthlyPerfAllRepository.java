package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceOverall;

@Repository
public interface MonthlyPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value="select count(ref) as total_ticket from incident "+ 
			 "where date_format(start_date, '%Y-%m-%d') < DATE_FORMAT(NOW(),'%Y-%m-01') "+ 
			 "and date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			 "and agent_fullname like 'EXT%' "+ 
			 "; " ,nativeQuery=true)
	public int getAllTicketCount();
	
	@Query(value=			 
			"select count(1) as total_ticket from incident i "+
		    "left outer join ticket_exception e "+
            "on i.ref=e.ref and e.type='sa' "+
            "where "+ 
			 "date_format(start_date, '%Y-%m-%d') < DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			 "and date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+  
			 "and agent_fullname like 'EXT%' "+  
			 "and team_name not like '%SUPPLIER PORTAL%' "+
             "and e.ref is null "+
             ";"			
			,nativeQuery=true)
	public int getTicketCount();
	
	@Query(value=
			"select count(1) as total_ticket from incident  i "+
			"left outer join ticket_exception e "+
            "on i.ref=e.ref and e.type='sa'  "+
			 "where "+
			 "ttr_passed='no' "+  
			 "and date_format(start_date,'%Y-%m-%d') < DATE_FORMAT(NOW(),'%Y-%m-01') "+  
			 "and date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+  
			 "and agent_fullname like 'EXT%'  "+
			 "and team_name not like '%SUPPLIER PORTAL%' "+ 
             "and e.ref is null "+
			 "; " ,nativeQuery=true)
	public int getAchievedTicketCount();
	
	@Query(value=
			"select count(1) as total_ticket from incident i "+
			"left outer join ticket_exception e "+
            "on i.ref=e.ref and e.type='sa' "+
			" where "+
			" ttr_passed='yes' "+ 
			" and date_format(start_date,'%Y-%m-%d') < DATE_FORMAT(NOW(),'%Y-%m-01') "+  
			" and date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+  
			" and agent_fullname like 'EXT%' "+
			" and team_name not like '%SUPPLIER PORTAL%' "+
            " and e.ref is null "+
			 ";" ,nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall WHERE  month(created_dt)=month(curdate()) and year(created_dt)=year(curdate()) AND period='monthly' AND category='sa';", nativeQuery=true)
	public PerformanceOverall getPerformanceThisWeek();

}
