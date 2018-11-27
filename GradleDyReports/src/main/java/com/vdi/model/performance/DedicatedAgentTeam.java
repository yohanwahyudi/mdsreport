package com.vdi.model.performance;

import java.time.LocalDate;
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
@Table(name="dedicated_team")
public class DedicatedAgentTeam {
	
	@Id
	@SequenceGenerator(name="seq10", sequenceName="seq10", allocationSize=50)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq10")
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	private String teamName;
	private String agentName;
	private int totalAssigned;
	private int totalPending;
	private int totalTicket;
	private int totalAchieved;
	private int totalMissed;
	private float achievement;
	private float contribution;
	private float teamContribution;
	private String category;
	private LocalDate report_dt;
	
	@Column(name="created_dt")
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name="updated_dt")
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
	@Version
	private int version;
	
	public String getTeamName() {
		return teamName;
	}
	public String getAgentName() {
		return agentName;
	}
	public int getTotalAssigned() {
		return totalAssigned;
	}
	public int getTotalPending() {
		return totalPending;
	}
	public int getTotalTicket() {
		return totalTicket;
	}
	public int getTotalAchieved() {
		return totalAchieved;
	}
	public int getTotalMissed() {
		return totalMissed;
	}
	public float getAchievement() {
		return achievement;
	}
	public float getContribution() {
		return contribution;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public void setTotalAssigned(int totalAssigned) {
		this.totalAssigned = totalAssigned;
	}
	public void setTotalPending(int totalPending) {
		this.totalPending = totalPending;
	}
	public void setTotalTicket(int totalTicket) {
		this.totalTicket = totalTicket;
	}
	public void setTotalAchieved(int totalAchieved) {
		this.totalAchieved = totalAchieved;
	}
	public void setTotalMissed(int totalMissed) {
		this.totalMissed = totalMissed;
	}
	public void setAchievement(float achievement) {
		this.achievement = achievement;
	}
	public void setContribution(float contribution) {
		this.contribution = contribution;
	}
	public float getTeamContribution() {
		return teamContribution;
	}
	public void setTeamContribution(float teamContribution) {
		this.teamContribution = teamContribution;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getCategory() {
		return category;
	}
	public int getVersion() {
		return version;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public LocalDate getReport_dt() {
		return report_dt;
	}
	public void setReport_dt(LocalDate report_dt) {
		this.report_dt = report_dt;
	}

	
}
