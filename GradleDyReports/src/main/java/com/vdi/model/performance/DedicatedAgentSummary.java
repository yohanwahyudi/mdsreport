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
@Table(name="dedicated_summary")
public class DedicatedAgentSummary {
	
	@Id
	@SequenceGenerator(name="seq11", sequenceName="seq11", allocationSize=50)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq11")
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	private int totalTicket;
	private int totalAchieved;
	private int totalMissed;
	private int totalOpen;
	private int totalClosedResolved;
	private int totalRejected;
	private float achievement;
	private float contribution;
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public int getVersion() {
		return version;
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

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getReport_dt() {
		return report_dt;
	}

	public void setReport_dt(LocalDate report_dt) {
		this.report_dt = report_dt;
	}

	public int getTotalOpen() {
		return totalOpen;
	}

	public int getTotalClosedResolved() {
		return totalClosedResolved;
	}

	public int getTotalRejected() {
		return totalRejected;
	}

	public void setTotalOpen(int totalOpen) {
		this.totalOpen = totalOpen;
	}

	public void setTotalClosedResolved(int totalClosedResolved) {
		this.totalClosedResolved = totalClosedResolved;
	}

	public void setTotalRejected(int totalRejected) {
		this.totalRejected = totalRejected;
	}

	
	

}
