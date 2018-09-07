package com.vdi.batch.mds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.Incident;

public interface IncidentRepository extends CrudRepository<Incident, Long>{
	
//	public List<Incident> getIncidentByWeek();
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('closed','resolved') "+
			"order by ttr_passed desc"+
			";", nativeQuery=true)
	public List<Incident> getAllIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('assigned') "+
			";", nativeQuery=true)
	public List<Incident> getAssignedIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('pending') "+
			";", nativeQuery=true)
	public List<Incident> getPendingIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
//			"and status in ('closed','resolved') "+
			"and ttr_passed='yes' "+
			";", nativeQuery=true)
	public List<Incident> getMissedIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and week(start_date,3)= :week "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('closed','resolved') "+
			"and ttr_passed='no' "+
			";", nativeQuery=true)
	public List<Incident> getAchievedIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('closed','resolved') "+
			"order by ttr_passed desc"+
			";", nativeQuery=true)
	public List<Incident> getAllIncidentByMonth(@Param("month") int month);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('assigned') "+
			";", nativeQuery=true)
	public List<Incident> getAssignedIncidentByMonth(@Param("month") int month);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and agent_fullname like 'EXT%' "+
			"and ttr_passed='yes' "+
			";", nativeQuery=true)
	public List<Incident> getMissedIncidentByMonth(@Param("month") int month);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and agent_fullname like 'EXT%' "+
			"and ttr_passed='no' "+
			";", nativeQuery=true)
	public List<Incident> getAchievedIncidentByMonth(@Param("month") int month);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where year(start_date)=year(curdate()) "+   
			"and month(start_date)= :month "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('pending') "+
			";", nativeQuery=true)
	public List<Incident> getPendingIncidentByMonth(@Param("month") int month);

}
