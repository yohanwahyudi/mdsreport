package com.vdi.batch.mds.repository.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.IncidentRepository;
import com.vdi.batch.mds.repository.dao.IncidentReportDAOService;
import com.vdi.model.Incident;

@Transactional
@Repository("incidentReportDAO")//change from service
public class IncidentReportDAOImpl implements IncidentReportDAOService{

	@Autowired
	private IncidentRepository incidentRepo;
	
	@Override
	public List<Incident> getAllIncidentByWeek(int month, int week) {
		
		return incidentRepo.getAllIncidentByWeek(month, week);
	}

	@Override
	public List<Incident> getAssignedIncidentByWeek(int month, int week) {
		
		return incidentRepo.getAssignedIncidentByWeek(month, week);
	}

	@Override
	public List<Incident> getPendingIncidentByWeek(int month, int week) {
		
		return incidentRepo.getPendingIncidentByWeek(month, week);
	}

	@Override
	public List<Incident> getAchievedIncidentByWeek(int month, int week) {
		
		return incidentRepo.getAchievedIncidentByWeek(month, week);
	}

	@Override
	public List<Incident> getMissedIncidentByWeek(int month, int week) {
		
		return incidentRepo.getMissedIncidentByWeek(month, week);
	}

	@Override
	public List<Incident> getAllIncidentByMonth(int month) {
		
		return incidentRepo.getAllIncidentByMonth(month);
	}

	@Override
	public List<Incident> getAssignedIncidentByMonth(int month) {
		
		return incidentRepo.getAssignedIncidentByMonth(month);
	}

	@Override
	public List<Incident> getPendingIncidentByMonth(int month) {
		
		return incidentRepo.getPendingIncidentByMonth(month);
	}

	@Override
	public List<Incident> getAchievedIncidentByMonth(int month) {
		
		return incidentRepo.getAchievedIncidentByMonth(month);
	}

	@Override
	public List<Incident> getMissedIncidentByMonth(int month) {
		
		return incidentRepo.getMissedIncidentByMonth(month);
	}

	@Override
	public List<Incident> getAllIncidentByMtd() {
		
		return incidentRepo.getAllIncidentByMtd();
	}

	@Override
	public List<Incident> getAssignedIncidentByMtd() {
		
		return incidentRepo.getAssignedIncidentByMtd();
	}

	@Override
	public List<Incident> getPendingIncidentByMtd() {
		
		return incidentRepo.getPendingIncidentByMtd();
	}

	@Override
	public List<Incident> getAchievedIncidentByMtd() {
		
		return incidentRepo.getAchievedIncidentByMtd();
	}

	@Override
	public List<Incident> getMissedIncidentByMtd() {
		
		return incidentRepo.getMissedIncidentByMtd();
	}

	
	
	

}
