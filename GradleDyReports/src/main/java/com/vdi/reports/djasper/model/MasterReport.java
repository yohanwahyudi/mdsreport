package com.vdi.reports.djasper.model;

import java.util.List;
import java.util.Map;

import com.vdi.model.Incident;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.DedicatedAgentTeam;
import com.vdi.model.performance.PerformanceTeam;
import com.vdi.model.staging.StagingServiceDesk;
import com.vdi.model.staging.StagingUserRequest;

public class MasterReport {
	
	private List<SummaryReport> overallAchievementList;
	private List<SummaryReport> serviceDeskAchievementList;
	
	private List<SummaryReport> serviceDeskSummaryList;//can be deleted
	private List<SummaryReport> userRequestSummaryList;//can be deleted
	private List<SummaryReport> supportAgentSummaryList;//can be deleted
	private List<PerformanceTeam> performanceTeamList;
	
	private List<PerformanceAgent> performanceServiceDeskAgentList;
	private List<PerformanceAgent> performanceUserRequestAgentList;
	
	private List<PerformanceAgent> performanceSuportAgentList;
	private List<Incident> supportAgentPendingList;
	private List<Incident> supportAgentAssignList;
	private List<Incident> supportAgentMissedList;
	
	private List<Incident> supportAgentIncidentList;
	private List<StagingServiceDesk> serviceDeskIncidentList;
	private List<StagingUserRequest> userRequestTicketList;
	
	private List<DedicatedAgentTeam> performanceByTeam;
	private List<Map<String, DedicatedAgentTeam>> performanceByAgentTeam;
	
	private List<DedicatedAgentTeam> dedicatedTeamList;
	private List<DedicatedAgentTeam> dedicatedAgentTeamList;
	
	private Map<Object, Object> mapValue;
	
	public MasterReport() {
		
	}

	public List<Incident> getSupportAgentIncidentList() {
		return supportAgentIncidentList;
	}

	public List<Incident> getSupportAgentPendingList() {
		return supportAgentPendingList;
	}

	public List<Incident> getSupportAgentAssignList() {
		return supportAgentAssignList;
	}

	public List<Incident> getSupportAgentMissedList() {
		return supportAgentMissedList;
	}

	public List<StagingServiceDesk> getServiceDeskIncidentList() {
		return serviceDeskIncidentList;
	}

	public List<StagingUserRequest> getUserRequestTicketList() {
		return userRequestTicketList;
	}

	public void setSupportAgentIncidentList(List<Incident> supportAgentIncidentList) {
		this.supportAgentIncidentList = supportAgentIncidentList;
	}

	public void setSupportAgentPendingList(List<Incident> supportAgentPendingList) {
		this.supportAgentPendingList = supportAgentPendingList;
	}

	public void setSupportAgentAssignList(List<Incident> supportAgentAssignList) {
		this.supportAgentAssignList = supportAgentAssignList;
	}

	public void setSupportAgentMissedList(List<Incident> supportAgentMissedList) {
		this.supportAgentMissedList = supportAgentMissedList;
	}

	public void setServiceDeskIncidentList(List<StagingServiceDesk> serviceDeskIncidentList) {
		this.serviceDeskIncidentList = serviceDeskIncidentList;
	}

	public void setUserRequestTicketList(List<StagingUserRequest> userRequestTicketList) {
		this.userRequestTicketList = userRequestTicketList;
	}

	public List<SummaryReport> getOverallAchievementList() {
		return overallAchievementList;
	}

	public List<PerformanceTeam> getPerformanceTeamList() {
		return performanceTeamList;
	}

	public void setPerformanceTeamList(List<PerformanceTeam> performanceTeamList) {
		this.performanceTeamList = performanceTeamList;
	}

	public List<PerformanceAgent> getPerformanceServiceDeskAgentList() {
		return performanceServiceDeskAgentList;
	}

	public List<PerformanceAgent> getPerformanceSuportAgentList() {
		return performanceSuportAgentList;
	}

	public void setPerformanceServiceDeskAgentList(List<PerformanceAgent> performanceServiceDeskAgentList) {
		this.performanceServiceDeskAgentList = performanceServiceDeskAgentList;
	}

	public void setPerformanceSuportAgentList(List<PerformanceAgent> performanceSuportAgentList) {
		this.performanceSuportAgentList = performanceSuportAgentList;
	}

	public List<SummaryReport> getServiceDeskSummaryList() {
		return serviceDeskSummaryList;
	}

	public List<SummaryReport> getUserRequestSummaryList() {
		return userRequestSummaryList;
	}

	public List<SummaryReport> getSupportAgentSummaryList() {
		return supportAgentSummaryList;
	}

	public void setOverallAchievementList(List<SummaryReport> overallAchievementList) {
		this.overallAchievementList = overallAchievementList;
	}

	public void setServiceDeskSummaryList(List<SummaryReport> serviceDeskSummaryList) {
		this.serviceDeskSummaryList = serviceDeskSummaryList;
	}

	public void setUserRequestSummaryList(List<SummaryReport> userRequestSummaryList) {
		this.userRequestSummaryList = userRequestSummaryList;
	}

	public void setSupportAgentSummaryList(List<SummaryReport> supportAgentSummaryList) {
		this.supportAgentSummaryList = supportAgentSummaryList;
	}

	public List<PerformanceAgent> getPerformanceUserRequestAgentList() {
		return performanceUserRequestAgentList;
	}

	public void setPerformanceUserRequestAgentList(List<PerformanceAgent> performanceUserRequestAgentList) {
		this.performanceUserRequestAgentList = performanceUserRequestAgentList;
	}

	public List<SummaryReport> getServiceDeskAchievementList() {
		return serviceDeskAchievementList;
	}

	public void setServiceDeskAchievementList(List<SummaryReport> serviceDeskAchievementList) {
		this.serviceDeskAchievementList = serviceDeskAchievementList;
	}

	public Map<Object, Object> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<Object, Object> mapValue) {
		this.mapValue = mapValue;
	}

	public List<DedicatedAgentTeam> getPerformanceByTeam() {
		return performanceByTeam;
	}

	public List<Map<String, DedicatedAgentTeam>> getPerformanceByAgentTeam() {
		return performanceByAgentTeam;
	}

	public void setPerformanceByTeam(List<DedicatedAgentTeam> performanceByTeam) {
		this.performanceByTeam = performanceByTeam;
	}

	public void setPerformanceByAgentTeam(List<Map<String, DedicatedAgentTeam>> performanceByAgentTeam) {
		this.performanceByAgentTeam = performanceByAgentTeam;
	}

	public List<DedicatedAgentTeam> getDedicatedAgentTeamList() {
		return dedicatedAgentTeamList;
	}

	public void setDedicatedAgentTeamList(List<DedicatedAgentTeam> dedicatedAgentTeamList) {
		this.dedicatedAgentTeamList = dedicatedAgentTeamList;
	}

	public List<DedicatedAgentTeam> getDedicatedTeamList() {
		return dedicatedTeamList;
	}

	public void setDedicatedTeamList(List<DedicatedAgentTeam> dedicatedTeamList) {
		this.dedicatedTeamList = dedicatedTeamList;
	}

	
}
