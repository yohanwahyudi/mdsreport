package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.DedicatedAgentTeam;

public interface DedicatedAgentTeamRepository extends CrudRepository<DedicatedAgentTeam, Long> {
	
	@Query(value="select *  " + 
			"from dedicated_team  " + 
			"where  " + 
			"report_dt < DATE_FORMAT(NOW(),'%Y-%m-01') " + 
			"and  " + 
			"report_dt >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+ 
			"and "+
			"category = :category ;", nativeQuery=true)
	public List<DedicatedAgentTeam> getPreviousMonthAgentTeam(@Param("category") String category);
	
	@Query(value="select  " + 
			" teamName, sum(totalTicket) as ticketTotal,  " + 
			" round( " + 
			"	 sum(totalTicket) *100/ " + 
			"		(select sum(totalTicket)  " + 
			"		 from dedicated_team  " + 
			"		 where report_dt < DATE_FORMAT(NOW(),'%Y-%m-01')  " + 
			"		 and report_dt >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') and category = :category " + 
			"		 )  " + 
			"   ,2) as contribution " + 
			"from dedicated_team       " + 
			"where       " + 
			" report_dt < DATE_FORMAT(NOW(),'%Y-%m-01')      " + 
			"and       " + 
			" report_dt >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')     " + 
			"and     " + 
			" category = :category              " + 
			"group by teamName; ", nativeQuery=true)
	public List<Object[]> getPreviousMonthTeam(@Param("category") String category);

}
