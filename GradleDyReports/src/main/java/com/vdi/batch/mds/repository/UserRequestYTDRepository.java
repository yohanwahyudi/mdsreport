package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vdi.model.UserRequestYTD;

public interface UserRequestYTDRepository extends CrudRepository<UserRequestYTD, Long> {
	
	
	@Query(value="update userrequest_ytd as ur " + 
			"	inner join staging_userrequest_ytd as staging " + 
			"	on ur.ref=staging.ref " + 
			"	set ur.status=staging.status, ur.resolutionDate=staging.resolutionDate,    " + 
			"			ur.resolutionDelay=staging.resolutionDelay, ur.resolutionTime=staging.resolutionTime,    " + 
			"			ur.lastpendingDate=staging.lastpendingDate, ur.lastpendingTime=staging.lastpendingTime,    " + 
			"			ur.lastupdateDate=staging.lastupdateDate,ur.lastupdateTime=staging.lastupdateTime,    " + 
			"			ur.cumulatedPending=staging.cumulatedPending, ur.assignmentDate=staging.assignmentDate, ur.assignmentTime=staging.assignmentTime,    " + 
			"			ur.agent=staging.agent,    " + 
			"			ur.slaTtoOver=staging.slaTtoOver, ur.slaTtoPassed=staging.slaTtoPassed, ur.slaTtrOver=staging.slaTtrOver,    " + 
			"			ur.slaTtrPassed=staging.slaTtrPassed, ur.solution=staging.solution, ur.ttrDeadline=staging.ttrDeadline,  " + 
			"            ur.pendingReason=staging.pendingReason, ur.pendingReason2=staging.pendingReason2, ur.ttoDeadline=staging.ttoDeadline, " + 
			"            ur.ttrDeadline=staging.ttrDeadline;", nativeQuery=true)
	public void updateUserRequestYTDTable();
	
	@Query(value="insert into userrequest_ytd    " + 
			"			select    " + 
			"				staging.*    " + 
			"			from staging_userrequest_ytd as staging    " + 
			"				left outer join userrequest_ytd as ur    " + 
			"				on staging.ref=ur.ref    " + 
			"			where (staging.personOrganizationName='DCU' OR staging.personOrganizationName='VISIONET DATA INTERNATIONAL, PT') AND staging.agent like 'EXT%' AND ur.ref is null; " + 
			"	", nativeQuery=true)
	public void insertToTable();
	
	@Query(value="delete from userrequest_ytd where ref not in  " + 
			"( " + 
			"	select    " + 
			"				staging.ref   " + 
			"			from staging_userrequest_ytd staging " + 
			"			where (staging.personOrganizationName='DCU' OR staging.personOrganizationName='VISIONET DATA INTERNATIONAL, PT') AND staging.agent like 'EXT%'	 " + 
			");", nativeQuery=true)
	public void syncDelete();

}
