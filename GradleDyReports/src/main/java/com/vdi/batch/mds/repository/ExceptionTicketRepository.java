package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	
	
	@Query(nativeQuery=true, value="select  " + 
			"	concat (ticket.start_dt,' ', ticket.start_time ) as date, ticket.ref, ticket.agent, cat.category, type.type, header.reason  " + 
			"from exception_header header " + 
			"	inner join ticket_category cat " + 
			"		on header.category_id = cat.id " + 
			"	inner join ticket_type type " + 
			"		on header.type_id = type.id " + 
			"	inner join exception_approval approve " + 
			"		on header.approval_id = approve.id " + 
			"	inner join exception_ticket ticket " + 
			"		on ticket.exception_header_id = header.id " + 
			"where " + 
			"	approve.status_id=4 " + 
			"and " + 
			"	month(header.created_dt) = month(now()) " + 
			"and " + 
			"	year(header.created_dt) = year(now()) " + 
			"order by  " + 
			"	cat.category desc, " + 
			"    type.type asc " + 
			";")
	public List<Object[]> getExceptionTicketForMonth();
	
	@Query(nativeQuery=true, value="select  " + 
			"	concat (ticket.start_dt,' ', ticket.start_time ) as date, ticket.ref, ticket.agent, cat.category, type.type, header.reason  " + 
			"from exception_header header " + 
			"	inner join ticket_category cat " + 
			"		on header.category_id = cat.id " + 
			"	inner join ticket_type type " + 
			"		on header.type_id = type.id " + 
			"	inner join exception_approval approve " + 
			"		on header.approval_id = approve.id " + 
			"	inner join exception_ticket ticket " + 
			"		on ticket.exception_header_id = header.id " + 
			"where " + 
			"	approve.status_id=4 " + 
			"and " + 
			"	month(header.created_dt) = month(now())-1 " + 
			"and " + 
			"	year(header.created_dt) = year(now()) " + 
			"order by  " + 
			"	cat.category desc, " + 
			"    type.type asc " + 
			";")
	public List<Object[]> getExceptionTicketForLastMonth();
	
	@Query(nativeQuery=true, value="select  " + 
			"	concat (ticket.start_dt,' ', ticket.start_time ) as date, ticket.ref, ticket.agent, cat.category, type.type, header.reason  " + 
			"from exception_header header " + 
			"	inner join ticket_category cat " + 
			"		on header.category_id = cat.id " + 
			"	inner join ticket_type type " + 
			"		on header.type_id = type.id " + 
			"	inner join exception_approval approve " + 
			"		on header.approval_id = approve.id " + 
			"	inner join exception_ticket ticket " + 
			"		on ticket.exception_header_id = header.id " + 
			"where " + 
			"	approve.status_id=4 " + 
			"and " + 
			"	month(header.created_dt) = :month " + 
			"and " + 
			"	year(header.created_dt) = :year " + 
			"order by  " + 
			"	cat.category desc, " + 
			"    type.type asc " + 
			";")
	public List<Object[]> getExceptionTicketByMonthYear(@Param("month") int month, @Param("year") int year);

}
