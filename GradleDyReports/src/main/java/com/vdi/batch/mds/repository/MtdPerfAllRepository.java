package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.performance.PerformanceOverall;

@Repository
public interface MtdPerfAllRepository extends CrudRepository<PerformanceOverall, Long>{
	
	@Query(value=			 
			"select count(1) as total_ticket from incident i  " + 
					"			INNER JOIN agent agent  " + 
					"				ON i.agent_fullname = agent.NAME  " + 
					"		    left outer join  " + 
					"				( " + 
					"					SELECT  " + 
					"						ref " + 
					"					FROM  " + 
					"						mds_itop.exception_ticket e " + 
					"					inner join  " + 
					"						exception_header h " + 
					"						on e.exception_header_id = h.id " + 
					"					inner join exception_approval apprv " + 
					"						on h.approval_id = apprv.id " + 
					"					where " + 
					"						h.type_id=1 " + 
					"						and h.category_id=1 " + 
					"						and apprv.status_id=4 " + 
					"				) exception " + 
					"			on exception.ref = i.ref " + 
					"            where   " + 
					"			 date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and agent.is_active=1  " + 
					"             and exception.ref is null "+
             ";"			
			,nativeQuery=true)
	public int getTicketCount();
	
	
	@Query(value=
			"select count(1) as total_ticket from incident i  " + 
					"			INNER JOIN agent agent  " + 
					"				ON i.agent_fullname = agent.NAME  " + 
					"		    left outer join  " + 
					"				( " + 
					"					SELECT  " + 
					"						ref " + 
					"					FROM  " + 
					"						mds_itop.exception_ticket e " + 
					"					inner join  " + 
					"						exception_header h " + 
					"						on e.exception_header_id = h.id " + 
					"					inner join exception_approval apprv " + 
					"						on h.approval_id = apprv.id " + 
					"					where " + 
					"						h.type_id=1 " + 
					"						and h.category_id=1 " + 
					"						and apprv.status_id=4 " + 
					"				) exception " + 
					"			on exception.ref = i.ref " + 
					"            where   " + 
					"			 ttr_passed='no' "+ 
					"			 and date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and agent.is_active=1  " + 
					"             and exception.ref is null "+
             ";"	 ,nativeQuery=true)
	public int getAchievedTicketCount();
	
	
	@Query(value=
			"select count(1) as total_ticket from incident i  " + 
					"			INNER JOIN agent agent  " + 
					"				ON i.agent_fullname = agent.NAME  " + 
					"		    left outer join  " + 
					"				( " + 
					"					SELECT  " + 
					"						ref " + 
					"					FROM  " + 
					"						mds_itop.exception_ticket e " + 
					"					inner join  " + 
					"						exception_header h " + 
					"						on e.exception_header_id = h.id " + 
					"					inner join exception_approval apprv " + 
					"						on h.approval_id = apprv.id " + 
					"					where " + 
					"						h.type_id=1 " + 
					"						and h.category_id=1 " + 
					"						and apprv.status_id=4 " + 
					"				) exception " + 
					"			on exception.ref = i.ref " + 
					"            where   " + 
					"			 ttr_passed='yes' "+ 
					"			 and date_format(start_date, '%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-01')       " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and agent.is_active=1  " + 
					"             and exception.ref is null "+
             ";"	 ,nativeQuery=true)
	public int getMissedTicketCount();
	
	@Query(value="select * from perf_overall "
			+ "WHERE  month=month(curdate()) and year(created_dt)=year(curdate()) "
			+ "AND period='monthly' AND category='sa';", nativeQuery=true)
	public PerformanceOverall getExistingPerformance();
	

}
