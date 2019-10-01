package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	public void constructedExceptionLastMonth(List<TicketException> list){
		list = new ArrayList<TicketException>();
		list = getExceptionLastMonth();
		
		List<String> typeGroup = getTypeGroup(list);	
		
		List<Map<String, Map<String, String>>> listObject = new ArrayList<>();
		for(String type : typeGroup) {
			Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
			for(TicketException ticket : list ) {
				List<Map<String, String>> mapList = new ArrayList<>();
 				
			}
			//map.put(type, list);
		}
		
	}
	
	private List<String> getTypeGroup(List<TicketException> list){
		list = new ArrayList<TicketException>();
		list = getExceptionLastMonth();
		
		List<String> typeList = new ArrayList<String>();
		for(TicketException t : list) {
			typeList.add(t.getType());			
		}
		
		List<String> typeGroup = typeList.stream()
				.distinct().collect(Collectors.toList());
		
		return typeGroup;
	}

}
