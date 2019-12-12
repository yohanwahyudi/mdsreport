package com.vdi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "userrequest_ytd")
public class UserRequestYTD {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq13")
	@SequenceGenerator(name = "seq13", sequenceName = "seq13", allocationSize = 100)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private String ref;
	private String title;
	private String status;
	
	@Column(length=4000)
	private String pendingReason;
	
	private String startDate;
	private String startTime;
	private String assignmentDate;
	private String assignmentTime;
	private String endDate;
	private String endTime;
	private String lastUpdateDate;
	private String lastUpdateTime;
	private String closeDate;
	private String closeTime;
	private String resolutionDate;
	private String resolutionTime;
	private String agent;
	private String email;
	private String fullName;
	private String personOrganizationName;
	private String origin;
	private String lastPendingDate;
	private String lastPendingTime;
	private String cumulatedpending;
	private String pendingReason2;
	private String organizationName;
	private String priority;
	private String resolutionDelay;
	private String slaTtoOver;
	private String slaTtoPassed;
	private String ttoDeadline;
	private String slaTtrOver;
	private String slaTtrPassed;
	private String ttrDeadline;
	private String ticketSubClass;
	
	@Column(length=4000)
	private String solution;
	
	private String userSatisfaction;
	private String userComment;
	private String serviceName;
	
	@Column(name="created_dt")
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name="updated_dt")
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
	@Version
	private int version;
	
	public String getRef() {
		return ref;
	}
	public String getTitle() {
		return title;
	}
	public String getStatus() {
		return status;
	}
	public String getPendingReason() {
		return pendingReason;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getAssignmentDate() {
		return assignmentDate;
	}
	public String getAssignmentTime() {
		return assignmentTime;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public String getResolutionDate() {
		return resolutionDate;
	}
	public String getResolutionTime() {
		return resolutionTime;
	}
	public String getAgent() {
		return agent;
	}
	public String getEmail() {
		return email;
	}
	public String getFullName() {
		return fullName;
	}
	public String getPersonOrganizationName() {
		return personOrganizationName;
	}
	public String getOrigin() {
		return origin;
	}
	public String getLastPendingDate() {
		return lastPendingDate;
	}
	public String getLastPendingTime() {
		return lastPendingTime;
	}
	public String getCumulatedpending() {
		return cumulatedpending;
	}
	public String getPendingReason2() {
		return pendingReason2;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public String getPriority() {
		return priority;
	}
	public String getResolutionDelay() {
		return resolutionDelay;
	}
	public String getSlaTtoOver() {
		return slaTtoOver;
	}
	public String getSlaTtoPassed() {
		return slaTtoPassed;
	}
	public String getTtoDeadline() {
		return ttoDeadline;
	}
	public String getSlaTtrOver() {
		return slaTtrOver;
	}
	public String getSlaTtrPassed() {
		return slaTtrPassed;
	}
	public String getTtrDeadline() {
		return ttrDeadline;
	}
	public String getTicketSubClass() {
		return ticketSubClass;
	}
	public String getSolution() {
		return solution;
	}
	public String getUserSatisfaction() {
		return userSatisfaction;
	}
	public String getUserComment() {
		return userComment;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setAssignmentDate(String assignmentDate) {
		this.assignmentDate = assignmentDate;
	}
	public void setAssignmentTime(String assignmentTime) {
		this.assignmentTime = assignmentTime;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public void setResolutionDate(String resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
	public void setResolutionTime(String resolutionTime) {
		this.resolutionTime = resolutionTime;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setPersonOrganizationName(String personOrganizationName) {
		this.personOrganizationName = personOrganizationName;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setLastPendingDate(String lastPendingDate) {
		this.lastPendingDate = lastPendingDate;
	}
	public void setLastPendingTime(String lastPendingTime) {
		this.lastPendingTime = lastPendingTime;
	}
	public void setCumulatedpending(String cumulatedpending) {
		this.cumulatedpending = cumulatedpending;
	}
	public void setPendingReason2(String pendingReason2) {
		this.pendingReason2 = pendingReason2;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setResolutionDelay(String resolutionDelay) {
		this.resolutionDelay = resolutionDelay;
	}
	public void setSlaTtoOver(String slaTtoOver) {
		this.slaTtoOver = slaTtoOver;
	}
	public void setSlaTtoPassed(String slaTtoPassed) {
		this.slaTtoPassed = slaTtoPassed;
	}
	public void setTtoDeadline(String ttoDeadline) {
		this.ttoDeadline = ttoDeadline;
	}
	public void setSlaTtrOver(String slaTtrOver) {
		this.slaTtrOver = slaTtrOver;
	}
	public void setSlaTtrPassed(String slaTtrPassed) {
		this.slaTtrPassed = slaTtrPassed;
	}
	public void setTtrDeadline(String ttrDeadline) {
		this.ttrDeadline = ttrDeadline;
	}
	public void setTicketSubClass(String ticketSubClass) {
		this.ticketSubClass = ticketSubClass;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public void setUserSatisfaction(String userSatisfaction) {
		this.userSatisfaction = userSatisfaction;
	}
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	

}
