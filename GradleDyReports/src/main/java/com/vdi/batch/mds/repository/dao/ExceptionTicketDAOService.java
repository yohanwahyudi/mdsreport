package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.TicketException;

public interface ExceptionTicketDAOService {
	
	public List<TicketException> getExceptionTicketThisMonth();
	public List<TicketException> getExceptionTicketLastMonth();
	
	public List<Object[]> getExceptionTicketForMonth();
	public List<Object[]> getExceptionTicketForLastMonth();
	public List<Object[]> getExceptionTicketByMonthYear(int month, int year);

}
