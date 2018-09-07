package com.vdi.model.staging;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "staging_userrequest")
public class StagingUserRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="seq7")
	@SequenceGenerator(name="seq7", sequenceName="seq7", allocationSize=100)
	@Column(name="id", updatable=false, nullable=false)
	private Long id;	
	private String scalar_objectid;
	private String scalar_urequestid;
	private String scalar_date;
	private String scalar_datetime;
	private String scalar_user;
	private String scalar_objectclass;
	private String scalar_objectid2;
	private String scalar_attribute;
	private String scalar_previousvalue;
	private String scalar_newvalue;
	private String scalar_type;
	private String scalar_urequestref;
	private String urequest_title;
	private String urequest_startdate;
	private String urequest_starttime;
	private String urequest_enddate;
	private String urequest_endtime;
	private String urequest_assignmentdate;
	private String urequest_assignmenttime;
	private String urequest_lastupdate;
	private String urequest_lastupdatetime;
	private String urequest_closedate;
	private String urequest_closedatetime;
	private String urequest_caller;
	private String urequest_agentname;
	private String urequest_agent;
	private String urequest_teamname;
	private String urequest_team;
	
	@Column(length=4000)
	private String urequest_description;
	
	private String urequest_status;
	private String urequest_priority;
	private String urequest_origin;
	private String urequest_lastpendingdate; 
	private String urequest_lastpendingtime;
	private String urequest_cumulatedpending;
	
	@Column(length=4000)
	private String urequest_pendingreason;
	
	private String urequest_refproblem;
	private String urequest_refchange;
	private String urequest_organization;
	private String urequest_organizationname;
	private String urequest_slattopassed;
	private String urequest_slattoover;
	private String urequest_ttodeadline;
	private String urequest_slattrpassed;
	private String urequest_slattrover;
	private String urequest_ttrdeadline;
	private String urequest_resolutiondelay;
	
	@Column(length=4000)
	private String urequest_solution;
	
	private String urequest_tto;
	private String urequest_ttr;
	private String person_fullname;
	private String person_organizationname;
	private String person_organization;
	private String urequest_usersatisfaction;
	
	@Column(length=4000)
	private String urequest_usercomment;
	
	private String urequest_servicename;
	
	public StagingUserRequest() {
		
	}
	
	public String getScalar_objectid() {
		return scalar_objectid;
	}
	public String getScalar_urequestid() {
		return scalar_urequestid;
	}
	public String getScalar_date() {
		return scalar_date;
	}
	public String getScalar_datetime() {
		return scalar_datetime;
	}
	public String getScalar_user() {
		return scalar_user;
	}
	public String getScalar_objectclass() {
		return scalar_objectclass;
	}
	public String getScalar_objectid2() {
		return scalar_objectid2;
	}
	public String getScalar_attribute() {
		return scalar_attribute;
	}
	public String getScalar_previousvalue() {
		return scalar_previousvalue;
	}
	public String getScalar_newvalue() {
		return scalar_newvalue;
	}
	public String getScalar_type() {
		return scalar_type;
	}
	public String getScalar_urequestref() {
		return scalar_urequestref;
	}
	public String getUrequest_title() {
		return urequest_title;
	}
	public String getUrequest_startdate() {
		return urequest_startdate;
	}
	public String getUrequest_starttime() {
		return urequest_starttime;
	}
	public String getUrequest_enddate() {
		return urequest_enddate;
	}
	public String getUrequest_endtime() {
		return urequest_endtime;
	}
	public String getUrequest_assignmentdate() {
		return urequest_assignmentdate;
	}
	public String getUrequest_assignmenttime() {
		return urequest_assignmenttime;
	}
	public String getUrequest_lastupdate() {
		return urequest_lastupdate;
	}
	public String getUrequest_lastupdatetime() {
		return urequest_lastupdatetime;
	}
	public String getUrequest_closedate() {
		return urequest_closedate;
	}
	public String getUrequest_closedatetime() {
		return urequest_closedatetime;
	}
	public String getUrequest_caller() {
		return urequest_caller;
	}
	public String getUrequest_agentname() {
		return urequest_agentname;
	}
	public String getUrequest_agent() {
		return urequest_agent;
	}
	public String getUrequest_teamname() {
		return urequest_teamname;
	}
	public String getUrequest_team() {
		return urequest_team;
	}
	public String getUrequest_description() {
		return urequest_description;
	}
	public String getUrequest_status() {
		return urequest_status;
	}
	public String getUrequest_priority() {
		return urequest_priority;
	}
	public String getUrequest_origin() {
		return urequest_origin;
	}
	public String getUrequest_lastpendingdate() {
		return urequest_lastpendingdate;
	}
	public String getUrequest_lastpendingtime() {
		return urequest_lastpendingtime;
	}
	public String getUrequest_cumulatedpending() {
		return urequest_cumulatedpending;
	}
	public String getUrequest_pendingreason() {
		return urequest_pendingreason;
	}
	public String getUrequest_refproblem() {
		return urequest_refproblem;
	}
	public String getUrequest_refchange() {
		return urequest_refchange;
	}
	public String getUrequest_organization() {
		return urequest_organization;
	}
	public String getUrequest_organizationname() {
		return urequest_organizationname;
	}
	public String getUrequest_slattopassed() {
		return urequest_slattopassed;
	}
	public String getUrequest_slattoover() {
		return urequest_slattoover;
	}
	public String getUrequest_ttodeadline() {
		return urequest_ttodeadline;
	}
	public String getUrequest_slattrpassed() {
		return urequest_slattrpassed;
	}
	public String getUrequest_slattrover() {
		return urequest_slattrover;
	}
	public String getUrequest_ttrdeadline() {
		return urequest_ttrdeadline;
	}
	public String getUrequest_resolutiondelay() {
		return urequest_resolutiondelay;
	}
	public String getUrequest_solution() {
		return urequest_solution;
	}
	public String getUrequest_tto() {
		return urequest_tto;
	}
	public String getUrequest_ttr() {
		return urequest_ttr;
	}
	public String getPerson_fullname() {
		return person_fullname;
	}
	public String getPerson_organizationname() {
		return person_organizationname;
	}
	public String getPerson_organization() {
		return person_organization;
	}
	public String getUrequest_usersatisfaction() {
		return urequest_usersatisfaction;
	}
	public String getUrequest_usercomment() {
		return urequest_usercomment;
	}
	public String getUrequest_servicename() {
		return urequest_servicename;
	}
	public void setScalar_objectid(String scalar_objectid) {
		this.scalar_objectid = scalar_objectid;
	}
	public void setScalar_urequestid(String scalar_urequestid) {
		this.scalar_urequestid = scalar_urequestid;
	}
	public void setScalar_date(String scalar_date) {
		this.scalar_date = scalar_date;
	}
	public void setScalar_datetime(String scalar_datetime) {
		this.scalar_datetime = scalar_datetime;
	}
	public void setScalar_user(String scalar_user) {
		this.scalar_user = scalar_user;
	}
	public void setScalar_objectclass(String scalar_objectclass) {
		this.scalar_objectclass = scalar_objectclass;
	}
	public void setScalar_objectid2(String scalar_objectid2) {
		this.scalar_objectid2 = scalar_objectid2;
	}
	public void setScalar_attribute(String scalar_attribute) {
		this.scalar_attribute = scalar_attribute;
	}
	public void setScalar_previousvalue(String scalar_previousvalue) {
		this.scalar_previousvalue = scalar_previousvalue;
	}
	public void setScalar_newvalue(String scalar_newvalue) {
		this.scalar_newvalue = scalar_newvalue;
	}
	public void setScalar_type(String scalar_type) {
		this.scalar_type = scalar_type;
	}
	public void setScalar_urequestref(String scalar_urequestref) {
		this.scalar_urequestref = scalar_urequestref;
	}
	public void setUrequest_title(String urequest_title) {
		this.urequest_title = urequest_title;
	}
	public void setUrequest_startdate(String urequest_startdate) {
		this.urequest_startdate = urequest_startdate;
	}
	public void setUrequest_starttime(String urequest_starttime) {
		this.urequest_starttime = urequest_starttime;
	}
	public void setUrequest_enddate(String urequest_enddate) {
		this.urequest_enddate = urequest_enddate;
	}
	public void setUrequest_endtime(String urequest_endtime) {
		this.urequest_endtime = urequest_endtime;
	}
	public void setUrequest_assignmentdate(String urequest_assignmentdate) {
		this.urequest_assignmentdate = urequest_assignmentdate;
	}
	public void setUrequest_assignmenttime(String urequest_assignmenttime) {
		this.urequest_assignmenttime = urequest_assignmenttime;
	}
	public void setUrequest_lastupdate(String urequest_lastupdate) {
		this.urequest_lastupdate = urequest_lastupdate;
	}
	public void setUrequest_lastupdatetime(String urequest_lastupdatetime) {
		this.urequest_lastupdatetime = urequest_lastupdatetime;
	}
	public void setUrequest_closedate(String urequest_closedate) {
		this.urequest_closedate = urequest_closedate;
	}
	public void setUrequest_closedatetime(String urequest_closedatetime) {
		this.urequest_closedatetime = urequest_closedatetime;
	}
	public void setUrequest_caller(String urequest_caller) {
		this.urequest_caller = urequest_caller;
	}
	public void setUrequest_agentname(String urequest_agentname) {
		this.urequest_agentname = urequest_agentname;
	}
	public void setUrequest_agent(String urequest_agent) {
		this.urequest_agent = urequest_agent;
	}
	public void setUrequest_teamname(String urequest_teamname) {
		this.urequest_teamname = urequest_teamname;
	}
	public void setUrequest_team(String urequest_team) {
		this.urequest_team = urequest_team;
	}
	public void setUrequest_description(String urequest_description) {
		this.urequest_description = urequest_description;
	}
	public void setUrequest_status(String urequest_status) {
		this.urequest_status = urequest_status;
	}
	public void setUrequest_priority(String urequest_priority) {
		this.urequest_priority = urequest_priority;
	}
	public void setUrequest_origin(String urequest_origin) {
		this.urequest_origin = urequest_origin;
	}
	public void setUrequest_lastpendingdate(String urequest_lastpendingdate) {
		this.urequest_lastpendingdate = urequest_lastpendingdate;
	}
	public void setUrequest_lastpendingtime(String urequest_lastpendingtime) {
		this.urequest_lastpendingtime = urequest_lastpendingtime;
	}
	public void setUrequest_cumulatedpending(String urequest_cumulatedpending) {
		this.urequest_cumulatedpending = urequest_cumulatedpending;
	}
	public void setUrequest_pendingreason(String urequest_pendingreason) {
		this.urequest_pendingreason = urequest_pendingreason;
	}
	public void setUrequest_refproblem(String urequest_refproblem) {
		this.urequest_refproblem = urequest_refproblem;
	}
	public void setUrequest_refchange(String urequest_refchange) {
		this.urequest_refchange = urequest_refchange;
	}
	public void setUrequest_organization(String urequest_organization) {
		this.urequest_organization = urequest_organization;
	}
	public void setUrequest_organizationname(String urequest_organizationname) {
		this.urequest_organizationname = urequest_organizationname;
	}
	public void setUrequest_slattopassed(String urequest_slattopassed) {
		this.urequest_slattopassed = urequest_slattopassed;
	}
	public void setUrequest_slattoover(String urequest_slattoover) {
		this.urequest_slattoover = urequest_slattoover;
	}
	public void setUrequest_ttodeadline(String urequest_ttodeadline) {
		this.urequest_ttodeadline = urequest_ttodeadline;
	}
	public void setUrequest_slattrpassed(String urequest_slattrpassed) {
		this.urequest_slattrpassed = urequest_slattrpassed;
	}
	public void setUrequest_slattrover(String urequest_slattrover) {
		this.urequest_slattrover = urequest_slattrover;
	}
	public void setUrequest_ttrdeadline(String urequest_ttrdeadline) {
		this.urequest_ttrdeadline = urequest_ttrdeadline;
	}
	public void setUrequest_resolutiondelay(String urequest_resolutiondelay) {
		this.urequest_resolutiondelay = urequest_resolutiondelay;
	}
	public void setUrequest_solution(String urequest_solution) {
		this.urequest_solution = urequest_solution;
	}
	public void setUrequest_tto(String urequest_tto) {
		this.urequest_tto = urequest_tto;
	}
	public void setUrequest_ttr(String urequest_ttr) {
		this.urequest_ttr = urequest_ttr;
	}
	public void setPerson_fullname(String person_fullname) {
		this.person_fullname = person_fullname;
	}
	public void setPerson_organizationname(String person_organizationname) {
		this.person_organizationname = person_organizationname;
	}
	public void setPerson_organization(String person_organization) {
		this.person_organization = person_organization;
	}
	public void setUrequest_usersatisfaction(String urequest_usersatisfaction) {
		this.urequest_usersatisfaction = urequest_usersatisfaction;
	}
	public void setUrequest_usercomment(String urequest_usercomment) {
		this.urequest_usercomment = urequest_usercomment;
	}
	public void setUrequest_servicename(String urequest_servicename) {
		this.urequest_servicename = urequest_servicename;
	}
	
}
