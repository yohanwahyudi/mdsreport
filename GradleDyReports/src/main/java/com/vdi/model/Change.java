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
@Table(name="staging_change")
public class Change {
	
	@Id
	@SequenceGenerator(name="seq8", sequenceName="seq8", allocationSize=50)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq8")
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	private String ref;
	private String title;
	private String status;
	private String org_name;
	private String caller_name;
	private String requestor_id_friendlyname;
	private String agent_id_friendlyname;
	private String team_id_friendlyname;
	private String supervisor_id_friendlyname;
	private String supervisor_group_id_friendlyname;
	private String manager_id_friendlyname;
	private String manager_group_id_friendlyname;
	private String start_date;
	private String start_time;
	private String end_date;
	private String end_time;
	private String last_update;
	private String close_date;
	private String related_incident_list;
	private String related_problems_list;
	private String related_request_list;
	private String creation_date;
	private String creation_time;
	private String userinfo;
	
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
	public String getOrg_name() {
		return org_name;
	}
	public String getCaller_name() {
		return caller_name;
	}
	public String getRequestor_id_friendlyname() {
		return requestor_id_friendlyname;
	}
	public String getAgent_id_friendlyname() {
		return agent_id_friendlyname;
	}
	public String getTeam_id_friendlyname() {
		return team_id_friendlyname;
	}
	public String getSupervisor_id_friendlyname() {
		return supervisor_id_friendlyname;
	}
	public String getSupervisor_group_id_friendlyname() {
		return supervisor_group_id_friendlyname;
	}
	public String getManager_id_friendlyname() {
		return manager_id_friendlyname;
	}
	public String getManager_group_id_friendlyname() {
		return manager_group_id_friendlyname;
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
	public String getRelated_incident_list() {
		return related_incident_list;
	}
	public String getRelated_problems_list() {
		return related_problems_list;
	}
	public String getRelated_request_list() {
		return related_request_list;
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
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public void setCaller_name(String caller_name) {
		this.caller_name = caller_name;
	}
	public void setRequestor_id_friendlyname(String requestor_id_friendlyname) {
		this.requestor_id_friendlyname = requestor_id_friendlyname;
	}
	public void setAgent_id_friendlyname(String agent_id_friendlyname) {
		this.agent_id_friendlyname = agent_id_friendlyname;
	}
	public void setTeam_id_friendlyname(String team_id_friendlyname) {
		this.team_id_friendlyname = team_id_friendlyname;
	}
	public void setSupervisor_id_friendlyname(String supervisor_id_friendlyname) {
		this.supervisor_id_friendlyname = supervisor_id_friendlyname;
	}
	public void setSupervisor_group_id_friendlyname(String supervisor_group_id_friendlyname) {
		this.supervisor_group_id_friendlyname = supervisor_group_id_friendlyname;
	}
	public void setManager_id_friendlyname(String manager_id_friendlyname) {
		this.manager_id_friendlyname = manager_id_friendlyname;
	}
	public void setManager_group_id_friendlyname(String manager_group_id_friendlyname) {
		this.manager_group_id_friendlyname = manager_group_id_friendlyname;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}
	public void setRelated_incident_list(String related_incident_list) {
		this.related_incident_list = related_incident_list;
	}
	public void setRelated_problems_list(String related_problems_list) {
		this.related_problems_list = related_problems_list;
	}
	public void setRelated_request_list(String related_request_list) {
		this.related_request_list = related_request_list;
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
	public String getStart_time() {
		return start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCreation_date() {
		return creation_date;
	}
	public String getCreation_time() {
		return creation_time;
	}
	public String getUserinfo() {
		return userinfo;
	}
	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}
	public void setCreation_time(String creation_time) {
		this.creation_time = creation_time;
	}
	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}

}
