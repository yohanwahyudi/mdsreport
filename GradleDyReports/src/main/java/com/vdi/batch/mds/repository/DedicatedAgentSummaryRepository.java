package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.performance.DedicatedAgentSummary;

public interface DedicatedAgentSummaryRepository extends CrudRepository<DedicatedAgentSummary, Long> {

	@Query(value="select *  " + 
			"from dedicated_summary  " + 
			"where  " + 
			"report_dt < DATE_FORMAT(NOW(),'%Y-%m-01 00:00:00') " + 
			"and  " + 
			"report_dt >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+ 
			"and "+ 
			"category = :category ;", nativeQuery=true)
	public DedicatedAgentSummary getPreviousMonthSummary(@Param("category") String category);

}
