package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.ExceptionTicketRepository;
import com.vdi.batch.mds.repository.dao.ExceptionTicketDAOService;
import com.vdi.model.TicketException;

@Service("exceptionTicketDAO")
public class ExceptionTicketDAOImpl implements ExceptionTicketDAOService{

	@Autowired
	private ExceptionTicketRepository exceptionRepository;
	
	@Override
	public List<TicketException> getExceptionTicketThisMonth() {
		return exceptionRepository.getExceptionTicketThisMonth();
	}
	
	@Override
	public List<TicketException> getExceptionTicketLastMonth() {
		return exceptionRepository.getExceptionTicketLastMonth();
	}

}
