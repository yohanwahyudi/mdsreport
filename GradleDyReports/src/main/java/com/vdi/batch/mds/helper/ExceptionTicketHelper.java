package com.vdi.batch.mds.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.ExceptionTicketDAOService;
import com.vdi.model.TicketException;

@Component
public class ExceptionTicketHelper {
	
	@Autowired
	@Qualifier("exceptionTicketDAO")
	private ExceptionTicketDAOService exceptionTicketService;
	
//	private List<TicketException> exceptionThisMonth;
//	private List<TicketException> exceptionLastMonth;
	
	public ExceptionTicketHelper() {
		
	}

	public List<TicketException> getExceptionThisMonth() {
		return exceptionTicketService.getExceptionTicketThisMonth();
	}

	public List<TicketException> getExceptionLastMonth() {
		return exceptionTicketService.getExceptionTicketLastMonth();
	}	
	

}
