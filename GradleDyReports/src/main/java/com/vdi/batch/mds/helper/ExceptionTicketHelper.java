package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.ExceptionTicketDAOService;
import com.vdi.configuration.PropertyNames;
import com.vdi.model.IncidentOpen;
import com.vdi.model.TicketException;
import com.vdi.tools.TimeStatic;

@Component
public class ExceptionTicketHelper {
	
	@Autowired
	@Qualifier("exceptionTicketDAO")
	private ExceptionTicketDAOService exceptionTicketService;
	
	public ExceptionTicketHelper() {
		
	}

	public List<TicketException> getExceptionThisMonth() {
		return exceptionTicketService.getExceptionTicketThisMonth();
	}

	public List<TicketException> getExceptionLastMonth() {
		return exceptionTicketService.getExceptionTicketLastMonth();
	}	
	
	private List<Object[]> getExceptionForMonth(){
		return exceptionTicketService.getExceptionTicketForMonth();
	}
	
	private List<Object[]> getExceptionForLastMonth(){
		return exceptionTicketService.getExceptionTicketForLastMonth();
	}
	
	private List<Object[]> getExceptionByMonthYear(int month, int year){
		return exceptionTicketService.getExceptionTicketByMonthYear(month, year);
	}
	
	public List<IncidentOpen> constructedException(String type){
		
		List<Object[]> temp = new ArrayList<>();
		List<IncidentOpen> incidentList = new ArrayList<>();
		
		if(type!=null && type.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_MTD)) {
			temp = getExceptionForMonth();
			
			for(Object[] a : temp) {
				String date = (String)a[0];
				String ref = (String)a[1];
				String agent = (String)a[2];
				String category = (String)a[3];
				String type1 = (String)a[4];
				String reason = (String)a[5];
				
				IncidentOpen incident = new IncidentOpen();
				incident.setStartDate(date);
				incident.setRef(ref);
				incident.setAgentFullname(agent);
				incident.setService(category);
				incident.setServiceSubcategory(type1);
				incident.setDescription(reason);
				
				incidentList.add(incident);
			}
			
			
		} else if (type!=null && type.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_MONTHLY)) {
			temp = getExceptionForLastMonth();
			
			for(Object[] a : temp) {
				String date = (String)a[0];
				String ref = (String)a[1];
				String agent = (String)a[2];
				String category = (String)a[3];
				String type1 = (String)a[4];
				String reason = (String)a[5];
				
				IncidentOpen incident = new IncidentOpen();
				incident.setStartDate(date);
				incident.setRef(ref);
				incident.setAgentFullname(agent);
				incident.setService(category);
				incident.setServiceSubcategory(type1);
				incident.setDescription(reason);
				
				incidentList.add(incident);
			}
			
		} else {
			int year = TimeStatic.currentYear - 1;
			temp = getExceptionByMonthYear(12, year);
			
			for(Object[] a : temp) {
				String date = (String)a[0];
				String ref = (String)a[1];
				String agent = (String)a[2];
				String category = (String)a[3];
				String type1 = (String)a[4];
				String reason = (String)a[5];
				
				IncidentOpen incident = new IncidentOpen();
				incident.setStartDate(date);
				incident.setRef(ref);
				incident.setAgentFullname(agent);
				incident.setService(category);
				incident.setServiceSubcategory(type1);
				incident.setDescription(reason);
				
				incidentList.add(incident);
			}
			
		}
		
		return incidentList;
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
