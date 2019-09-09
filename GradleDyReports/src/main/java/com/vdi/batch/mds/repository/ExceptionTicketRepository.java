package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.TicketException;

public interface ExceptionTicketRepository extends CrudRepository<TicketException, Long>{
	
	@Query(nativeQuery = true, 
		   value = "select id, created_dt, updated_dt, version, ref, reason, "
		   		+ "case type "
		   		+ "when 'sd' then'Service Desk' "
		   		+ "when 'ur' then 'Service Desk' "
		   		+ "when 'sa' then 'Support Agent' "
		   		+ "end as type "
		   		+ "from ticket_exception "
		   + "where month(created_dt) = month(now()) "
		   + "and year(created_dt) = year(now()); ")
	public List<TicketException> getExceptionTicketThisMonth();
	
	@Query(nativeQuery = true, 
			   value = "select id, created_dt, updated_dt, version, ref, reason, "
				   		+ "case type "
				   		+ "when 'sd' then'Service Desk' "
				   		+ "when 'ur' then 'Service Desk' "
				   		+ "when 'sa' then 'Support Agent' "
				   		+ "end as type "
				   		+ "from ticket_exception "
			   + "where month(created_dt) = month(now())-1 "
			   + "and year(created_dt) = year(now()); ")
		public List<TicketException> getExceptionTicketLastMonth();

}
