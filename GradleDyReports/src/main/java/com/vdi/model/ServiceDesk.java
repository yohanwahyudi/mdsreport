package com.vdi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="servicedesk")
public class ServiceDesk {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="seq6")
	@SequenceGenerator(name="seq6", sequenceName="seq6", allocationSize=100)
	@Column(name="id", updatable=false, nullable=false)
	private Long id;
	private String scalar_date;
	private String scalar_time;
	private String scalar_user;
	private String scalar_objectclass;
	private String scalar_objectid;
	private String scalar_attribute;
	private String scalar_previousvalue;
	private String scalar_newvalue;
	private String scalar_type;
	private String incident_ref;
	private String incident_title;
	private String incident_startdate;
	private String incident_starttime;
	private String incident_enddate;
	private String incident_enddatetime;
	private String incident_assignmentdate;
	private String incident_assignmenttime;
	private String incident_lastupdatedate;
	private String incident_lastupdatetime;
	private String incident_closedate;
	private String incident_closetime;
	private String incident_caller;
	private String incident_agentName;
	private String incident_agent;
	private String incident_teamName;
	private String incident_team;
	private String incident_description;
	private String incident_status;
	private String incident_priority;
	private String incident_origin;
	private String incident_lastpendingdate;
	private String incident_lastpendingtime;
	private String incident_cumulatedpending;
	private String incident_pendingreason;
	private String incident_parentprivateincidentref;
	private String incident_ref2;
	private String incident_parentchangeref;
	private String incident_organization;
	private String incident_organizationName;
	private String incident_slattopassed;
	private String incident_slattoover;
	private String incident_ttoDeadline;
	private String incident_slattrpassed;
	private String incident_slattrover;
	private String incident_ttrDeadline;
	private String incident_resolutiondelay;
	private String incident_solution;
	private String incident_tto;
	private String incident_ttr;
	private String person_fullname;
	private String person_organizationname;
	private String person_organization;
	private String incident_usersatisfaction;
	private String incident_usercomment;
	
	
	public String getScalar_date() {
		return scalar_date;
	}
	public void setScalar_date(String scalar_date) {
		this.scalar_date = scalar_date;
	}
	public String getScalar_time() {
		return scalar_time;
	}
	public void setScalar_time(String scalar_time) {
		this.scalar_time = scalar_time;
	}
	public String getScalar_user() {
		return scalar_user;
	}
	public void setScalar_user(String scalar_user) {
		this.scalar_user = scalar_user;
	}
	public String getScalar_objectclass() {
		return scalar_objectclass;
	}
	public void setScalar_objectclass(String scalar_objectclass) {
		this.scalar_objectclass = scalar_objectclass;
	}
	public String getScalar_objectid() {
		return scalar_objectid;
	}
	public void setScalar_objectid(String scalar_objectid) {
		this.scalar_objectid = scalar_objectid;
	}
	public String getScalar_attribute() {
		return scalar_attribute;
	}
	public void setScalar_attribute(String scalar_attribute) {
		this.scalar_attribute = scalar_attribute;
	}
	public String getScalar_previousvalue() {
		return scalar_previousvalue;
	}
	public void setScalar_previousvalue(String scalar_previousvalue) {
		this.scalar_previousvalue = scalar_previousvalue;
	}
	public String getScalar_newvalue() {
		return scalar_newvalue;
	}
	public void setScalar_newvalue(String scalar_newvalue) {
		this.scalar_newvalue = scalar_newvalue;
	}
	public String getScalar_type() {
		return scalar_type;
	}
	public void setScalar_type(String scalar_type) {
		this.scalar_type = scalar_type;
	}
	public String getIncident_ref() {
		return incident_ref;
	}
	public void setIncident_ref(String incident_ref) {
		this.incident_ref = incident_ref;
	}
	public String getIncident_title() {
		return incident_title;
	}
	public void setIncident_title(String incident_title) {
		this.incident_title = incident_title;
	}
	public String getIncident_startdate() {
		return incident_startdate;
	}
	public void setIncident_startdate(String incident_startdate) {
		this.incident_startdate = incident_startdate;
	}
	public String getIncident_starttime() {
		return incident_starttime;
	}
	public void setIncident_starttime(String incident_starttime) {
		this.incident_starttime = incident_starttime;
	}
	public String getIncident_enddate() {
		return incident_enddate;
	}
	public void setIncident_enddate(String incident_enddate) {
		this.incident_enddate = incident_enddate;
	}
	public String getIncident_enddatetime() {
		return incident_enddatetime;
	}
	public void setIncident_enddatetime(String incident_enddatetime) {
		this.incident_enddatetime = incident_enddatetime;
	}
	public String getIncident_assignmentdate() {
		return incident_assignmentdate;
	}
	public void setIncident_assignmentdate(String incident_assignmentdate) {
		this.incident_assignmentdate = incident_assignmentdate;
	}
	public String getIncident_assignmenttime() {
		return incident_assignmenttime;
	}
	public void setIncident_assignmenttime(String incident_assignmenttime) {
		this.incident_assignmenttime = incident_assignmenttime;
	}
	public String getIncident_lastupdatedate() {
		return incident_lastupdatedate;
	}
	public void setIncident_lastupdatedate(String incident_lastupdatedate) {
		this.incident_lastupdatedate = incident_lastupdatedate;
	}
	public String getIncident_lastupdatetime() {
		return incident_lastupdatetime;
	}
	public void setIncident_lastupdatetime(String incident_lastupdatetime) {
		this.incident_lastupdatetime = incident_lastupdatetime;
	}
	public String getIncident_closedate() {
		return incident_closedate;
	}
	public void setIncident_closedate(String incident_closedate) {
		this.incident_closedate = incident_closedate;
	}
	public String getIncident_closetime() {
		return incident_closetime;
	}
	public void setIncident_closetime(String incident_closetime) {
		this.incident_closetime = incident_closetime;
	}
	public String getIncident_caller() {
		return incident_caller;
	}
	public void setIncident_caller(String incident_caller) {
		this.incident_caller = incident_caller;
	}
	public String getIncident_agentName() {
		return incident_agentName;
	}
	public void setIncident_agentName(String incident_agentName) {
		this.incident_agentName = incident_agentName;
	}
	public String getIncident_agent() {
		return incident_agent;
	}
	public void setIncident_agent(String incident_agent) {
		this.incident_agent = incident_agent;
	}
	public String getIncident_teamName() {
		return incident_teamName;
	}
	public void setIncident_teamName(String incident_teamName) {
		this.incident_teamName = incident_teamName;
	}
	public String getIncident_team() {
		return incident_team;
	}
	public void setIncident_team(String incident_team) {
		this.incident_team = incident_team;
	}
	public String getIncident_description() {
		return incident_description;
	}
	public void setIncident_description(String incident_description) {
		this.incident_description = incident_description;
	}
	public String getIncident_status() {
		return incident_status;
	}
	public void setIncident_status(String incident_status) {
		this.incident_status = incident_status;
	}
	public String getIncident_priority() {
		return incident_priority;
	}
	public void setIncident_priority(String incident_priority) {
		this.incident_priority = incident_priority;
	}
	public String getIncident_origin() {
		return incident_origin;
	}
	public void setIncident_origin(String incident_origin) {
		this.incident_origin = incident_origin;
	}
	public String getIncident_lastpendingdate() {
		return incident_lastpendingdate;
	}
	public void setIncident_lastpendingdate(String incident_lastpendingdate) {
		this.incident_lastpendingdate = incident_lastpendingdate;
	}
	public String getIncident_lastpendingtime() {
		return incident_lastpendingtime;
	}
	public void setIncident_lastpendingtime(String incident_lastpendingtime) {
		this.incident_lastpendingtime = incident_lastpendingtime;
	}
	public String getIncident_cumulatedpending() {
		return incident_cumulatedpending;
	}
	public void setIncident_cumulatedpending(String incident_cumulatedpending) {
		this.incident_cumulatedpending = incident_cumulatedpending;
	}
	public String getIncident_pendingreason() {
		return incident_pendingreason;
	}
	public void setIncident_pendingreason(String incident_pendingreason) {
		this.incident_pendingreason = incident_pendingreason;
	}
	public String getIncident_parentprivateincidentref() {
		return incident_parentprivateincidentref;
	}
	public void setIncident_parentprivateincidentref(String incident_parentprivateincidentref) {
		this.incident_parentprivateincidentref = incident_parentprivateincidentref;
	}
	public String getIncident_ref2() {
		return incident_ref2;
	}
	public void setIncident_ref2(String incident_ref2) {
		this.incident_ref2 = incident_ref2;
	}
	public String getIncident_parentchangeref() {
		return incident_parentchangeref;
	}
	public void setIncident_parentchangeref(String incident_parentchangeref) {
		this.incident_parentchangeref = incident_parentchangeref;
	}
	public String getIncident_organization() {
		return incident_organization;
	}
	public void setIncident_organization(String incident_organization) {
		this.incident_organization = incident_organization;
	}
	public String getIncident_organizationName() {
		return incident_organizationName;
	}
	public void setIncident_organizationName(String incident_organizationName) {
		this.incident_organizationName = incident_organizationName;
	}
	public String getIncident_slattopassed() {
		return incident_slattopassed;
	}
	public void setIncident_slattopassed(String incident_slattopassed) {
		this.incident_slattopassed = incident_slattopassed;
	}
	public String getIncident_slattoover() {
		return incident_slattoover;
	}
	public void setIncident_slattoover(String incident_slattoover) {
		this.incident_slattoover = incident_slattoover;
	}
	public String getIncident_ttoDeadline() {
		return incident_ttoDeadline;
	}
	public void setIncident_ttoDeadline(String incident_ttoDeadline) {
		this.incident_ttoDeadline = incident_ttoDeadline;
	}
	public String getIncident_slattrpassed() {
		return incident_slattrpassed;
	}
	public void setIncident_slattrpassed(String incident_slattrpassed) {
		this.incident_slattrpassed = incident_slattrpassed;
	}
	public String getIncident_slattrover() {
		return incident_slattrover;
	}
	public void setIncident_slattrover(String incident_slattrover) {
		this.incident_slattrover = incident_slattrover;
	}
	public String getIncident_ttrDeadline() {
		return incident_ttrDeadline;
	}
	public void setIncident_ttrDeadline(String incident_ttrDeadline) {
		this.incident_ttrDeadline = incident_ttrDeadline;
	}
	public String getIncident_resolutiondelay() {
		return incident_resolutiondelay;
	}
	public void setIncident_resolutiondelay(String incident_resolutiondelay) {
		this.incident_resolutiondelay = incident_resolutiondelay;
	}
	public String getIncident_solution() {
		return incident_solution;
	}
	public void setIncident_solution(String incident_solution) {
		this.incident_solution = incident_solution;
	}
	public String getIncident_tto() {
		return incident_tto;
	}
	public void setIncident_tto(String incident_tto) {
		this.incident_tto = incident_tto;
	}
	public String getIncident_ttr() {
		return incident_ttr;
	}
	public void setIncident_ttr(String incident_ttr) {
		this.incident_ttr = incident_ttr;
	}
	public String getPerson_fullname() {
		return person_fullname;
	}
	public void setPerson_fullname(String person_fullname) {
		this.person_fullname = person_fullname;
	}
	public String getPerson_organizationname() {
		return person_organizationname;
	}
	public void setPerson_organizationname(String person_organizationname) {
		this.person_organizationname = person_organizationname;
	}
	public String getPerson_organization() {
		return person_organization;
	}
	public void setPerson_organization(String person_organization) {
		this.person_organization = person_organization;
	}
	public String getIncident_usersatisfaction() {
		return incident_usersatisfaction;
	}
	public void setIncident_usersatisfaction(String incident_usersatisfaction) {
		this.incident_usersatisfaction = incident_usersatisfaction;
	}
	public String getIncident_usercomment() {
		return incident_usercomment;
	}
	public void setIncident_usercomment(String incident_usercomment) {
		this.incident_usercomment = incident_usercomment;
	}
	

}
