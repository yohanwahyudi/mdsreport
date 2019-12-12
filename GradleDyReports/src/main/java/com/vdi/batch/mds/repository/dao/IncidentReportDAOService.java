package com.vdi.batch.mds.repository.dao;

import java.util.List;

import com.vdi.model.Incident;

public interface IncidentReportDAOService {
	
	public List<Incident> getAllIncidentByWeek(int month, int week); 
	public List<Incident> getAssignedIncidentByWeek(int month, int week); 
	public List<Incident> getPendingIncidentByWeek(int month, int week); 
	public List<Incident> getAchievedIncidentByWeek(int month, int week); 
	public List<Incident> getMissedIncidentByWeek(int month, int week); 
	
	public List<Incident> getAllIncidentByMtd(); 
	public List<Incident> getAssignedIncidentByMtd(); 
	public List<Incident> getPendingIncidentByMtd(); 
	public List<Incident> getAchievedIncidentByMtd(); 
	public List<Incident> getMissedIncidentByMtd(); 
	
	public List<Incident> getAllIncidentByMonth(int month); 
	public List<Incident> getAssignedIncidentByMonth(int month); 
	public List<Incident> getPendingIncidentByMonth(int month); 
	public List<Incident> getAchievedIncidentByMonth(int month); 
	public List<Incident> getMissedIncidentByMonth(int month); 

}
