package com.vdi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "incident")
public class Incident {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="seq3")
	@SequenceGenerator(name="seq3", sequenceName="seq3", allocationSize=100)
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	private String ref;
	private String title;
	private String status;
	private String start_date;
	private String start_time;
	private String assignment_date;
	private String assignment_time;
	private String end_date;
	private String end_time;
	private String lastupdate_date;
	private String lastupdate_time;
	private String close_date;
	private String close_time;
	private String agent_lastname;
	private String agent_fullname;
	private String team;
	private String team_name;
	
	@Column(length=4000)
	private String description;
	
	private String origin;
	private String lastpending_date;
	private String lastpending_time;
	private String cumulated_pending;
	
	@Column(length=4000)
	private String pending_reason;
	
	private String parent_incident_ref;
	private String parent_problem_ref;
	private String parent_change_ref;
	private String incident_organization_short;
	private String incident_organization_name;
	private String agent;
	private String person_first_name;
	private String person_last_name;
	private String priority;
	private String resolution_delay;
	private String tto_over;
	private String tto_passed;
	private String tto_deadline;
	private String ttr_over;
	private String ttr_passed;
	private String ttr_deadline;
	private String status2;
	private String team_id;
	private String type;
	private String tto;
	private String ttr;
	
	@Column(length=4000)
	private String solution;
	
	private String person_full_name;
	private String person_org_short;
	private String person_org_name;
	private String user_satisfaction;
	private String user_comment;
	private String resolution_date;
	private String resolution_time;
	private String hotflag;
	private String hotflag_reason;
	private String impact;
	private String urgency;
	private String email;
	
	public String getRef() {
		return ref;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStart_date() {
		return start_date;
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
	public String getAssignment_date() {
		return assignment_date;
	}
	public void setAssignment_date(String assignment_date) {
		this.assignment_date = assignment_date;
	}
	public String getAssignment_time() {
		return assignment_time;
	}
	public void setAssignment_time(String assignment_time) {
		this.assignment_time = assignment_time;
	}
	public String getEnd_date() {
		return end_date;
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
	public String getLastupdate_date() {
		return lastupdate_date;
	}
	public void setLastupdate_date(String lastupdate_date) {
		this.lastupdate_date = lastupdate_date;
	}
	public String getLastupdate_time() {
		return lastupdate_time;
	}
	public void setLastupdate_time(String lastupdate_time) {
		this.lastupdate_time = lastupdate_time;
	}
	public String getClose_date() {
		return close_date;
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
	public String getAgent_lastname() {
		return agent_lastname;
	}
	public void setAgent_lastname(String agent_lastname) {
		this.agent_lastname = agent_lastname;
	}
	public String getAgent_fullname() {
		return agent_fullname;
	}
	public void setAgent_fullname(String agent_fullname) {
		this.agent_fullname = agent_fullname;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getLastpending_date() {
		return lastpending_date;
	}
	public void setLastpending_date(String lastpending_date) {
		this.lastpending_date = lastpending_date;
	}
	public String getLastpending_time() {
		return lastpending_time;
	}
	public void setLastpending_time(String lastpending_time) {
		this.lastpending_time = lastpending_time;
	}
	public String getCumulated_pending() {
		return cumulated_pending;
	}
	public void setCumulated_pending(String cumulated_pending) {
		this.cumulated_pending = cumulated_pending;
	}
	public String getPending_reason() {
		return pending_reason;
	}
	public void setPending_reason(String pending_reason) {
		this.pending_reason = pending_reason;
	}
	public String getParent_incident_ref() {
		return parent_incident_ref;
	}
	public void setParent_incident_ref(String parent_incident_ref) {
		this.parent_incident_ref = parent_incident_ref;
	}
	public String getParent_problem_ref() {
		return parent_problem_ref;
	}
	public void setParent_problem_ref(String parent_problem_ref) {
		this.parent_problem_ref = parent_problem_ref;
	}
	public String getParent_change_ref() {
		return parent_change_ref;
	}
	public void setParent_change_ref(String parent_change_ref) {
		this.parent_change_ref = parent_change_ref;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getResolution_delay() {
		return resolution_delay;
	}
	public void setResolution_delay(String resolution_delay) {
		this.resolution_delay = resolution_delay;
	}
	public String getTto_over() {
		return tto_over;
	}
	public void setTto_over(String tto_over) {
		this.tto_over = tto_over;
	}
	public String getTto_passed() {
		return tto_passed;
	}
	public void setTto_passed(String tto_passed) {
		this.tto_passed = tto_passed;
	}
	public String getTto_deadline() {
		return tto_deadline;
	}
	public void setTto_deadline(String tto_deadline) {
		this.tto_deadline = tto_deadline;
	}
	public String getTtr_over() {
		return ttr_over;
	}
	public void setTtr_over(String ttr_over) {
		this.ttr_over = ttr_over;
	}
	public String getTtr_passed() {
		return ttr_passed;
	}
	public void setTtr_passed(String ttr_passed) {
		this.ttr_passed = ttr_passed;
	}
	public String getTtr_deadline() {
		return ttr_deadline;
	}
	public void setTtr_deadline(String ttr_deadline) {
		this.ttr_deadline = ttr_deadline;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getTeam_id() {
		return team_id;
	}
	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTto() {
		return tto;
	}
	public void setTto(String tto) {
		this.tto = tto;
	}
	public String getTtr() {
		return ttr;
	}
	public void setTtr(String ttr) {
		this.ttr = ttr;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getIncident_organization_short() {
		return incident_organization_short;
	}
	public void setIncident_organization_short(String incident_organization_short) {
		this.incident_organization_short = incident_organization_short;
	}
	public String getIncident_organization_name() {
		return incident_organization_name;
	}
	public void setIncident_organization_name(String incident_organization_name) {
		this.incident_organization_name = incident_organization_name;
	}
	public String getPerson_first_name() {
		return person_first_name;
	}
	public void setPerson_first_name(String person_first_name) {
		this.person_first_name = person_first_name;
	}
	public String getPerson_last_name() {
		return person_last_name;
	}
	public void setPerson_last_name(String person_last_name) {
		this.person_last_name = person_last_name;
	}
	public String getPerson_full_name() {
		return person_full_name;
	}
	public void setPerson_full_name(String person_full_name) {
		this.person_full_name = person_full_name;
	}
	
	public String getPerson_org_short() {
		return person_org_short;
	}
	public void setPerson_org_short(String person_org_short) {
		this.person_org_short = person_org_short;
	}
	public String getPerson_org_name() {
		return person_org_name;
	}
	public void setPerson_org_name(String person_org_name) {
		this.person_org_name = person_org_name;
	}
	public String getUser_satisfaction() {
		return user_satisfaction;
	}
	public void setUser_satisfaction(String user_satisfaction) {
		this.user_satisfaction = user_satisfaction;
	}
	public String getUser_comment() {
		return user_comment;
	}
	public void setUser_comment(String user_comment) {
		this.user_comment = user_comment;
	}
	public String getResolution_date() {
		return resolution_date;
	}
	public void setResolution_date(String resolution_date) {
		this.resolution_date = resolution_date;
	}
	public String getResolution_time() {
		return resolution_time;
	}
	public void setResolution_time(String resolution_time) {
		this.resolution_time = resolution_time;
	}
	public String getHotflag() {
		return hotflag;
	}
	public void setHotflag(String hotflag) {
		this.hotflag = hotflag;
	}
	public String getHotflag_reason() {
		return hotflag_reason;
	}
	public void setHotflag_reason(String hotflag_reason) {
		this.hotflag_reason = hotflag_reason;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
