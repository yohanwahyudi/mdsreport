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
@Table(name="staging_problem")
public class Problem {
	
	@Id
	@SequenceGenerator(name="seq9", sequenceName="seq9", allocationSize=50)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq9")
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	private String ref;
	private String title;
	private String status;
	private String priority;
	private String org_id;
	private String org_name;
	private String caller_name;
	private String team_id_friendlyname;
	private String agent_id_friendlyname;
	private String related_change_id_friendlyname;
	private String start_date;
	private String start_time;
	private String end_date;
	private String end_time;
	private String last_update;
	private String close_date;
	private String close_time;
	private String assignment_date;
	private String resolution_date;
	
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
	public String getPriority() {
		return priority;
	}
	public String getOrg_id() {
		return org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public String getCaller_name() {
		return caller_name;
	}
	public String getTeam_id_friendlyname() {
		return team_id_friendlyname;
	}
	public String getAgent_id_friendlyname() {
		return agent_id_friendlyname;
	}
	public String getRelated_change_id_friendlyname() {
		return related_change_id_friendlyname;
	}
	public String getStart_date() {
		return start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public String getLast_update() {
		return last_update;
	}
	public String getClose_date() {
		return close_date;
	}
	public String getAssignment_date() {
		return assignment_date;
	}
	public String getResolution_date() {
		return resolution_date;
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
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public void setCaller_name(String caller_name) {
		this.caller_name = caller_name;
	}
	public void setTeam_id_friendlyname(String team_id_friendlyname) {
		this.team_id_friendlyname = team_id_friendlyname;
	}
	public void setAgent_id_friendlyname(String agent_id_friendlyname) {
		this.agent_id_friendlyname = agent_id_friendlyname;
	}
	public void setRelated_change_id_friendlyname(String related_change_id_friendlyname) {
		this.related_change_id_friendlyname = related_change_id_friendlyname;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}
	public String getClose_time() {
		return close_time;
	}
	public void setClose_time(String close_time) {
		this.close_time = close_time;
	}
	public void setAssignment_date(String assignment_date) {
		this.assignment_date = assignment_date;
	}
	public void setResolution_date(String resolution_date) {
		this.resolution_date = resolution_date;
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
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
