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
			//"and status in ('closed','resolved') "+
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
			//"and status in ('closed','resolved') "+
			"and ttr_passed='no' "+
			";", nativeQuery=true)
	public List<Incident> getAchievedIncidentByWeek(@Param("month") int month, @Param("week") int week);
	
	@Query(value=" select      " + 
			"			 	*      " + 
			"			 from incident i      " + 
			"			  " + 
			"             left join       " + 
			"				(      " + 
			"					SELECT       " + 
			"						ref      " + 
			"					FROM       " + 
			"						mds_itop.exception_ticket e      " + 
			"					inner join       " + 
			"						exception_header h      " + 
			"						on e.exception_header_id = h.id      " + 
			"					inner join exception_approval apprv      " + 
			"						on h.approval_id = apprv.id      " + 
			"					where      " + 
			"						h.type_id=1      " + 
			"						and h.category_id=1    " + 
			"						and apprv.status_id=4      " + 
			"				) e      " + 
			"			  on e.ref = i.ref " + 
			"              " + 
			"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
			"			 and agent_fullname like 'EXT%'    " + 
			"			 and e.ref is null    " + 
			"			 order by ttr_passed desc   " + 
			";", nativeQuery=true)
	public List<Incident> getAllIncidentByMtd();
	
	@Query(value=
			" select      " + 
					"			 	*      " + 
					"			 from incident i      " + 
					"			  " + 
					"             left join       " + 
					"				(      " + 
					"					SELECT       " + 
					"						ref      " + 
					"					FROM       " + 
					"						mds_itop.exception_ticket e      " + 
					"					inner join       " + 
					"						exception_header h      " + 
					"						on e.exception_header_id = h.id      " + 
					"					inner join exception_approval apprv      " + 
					"						on h.approval_id = apprv.id      " + 
					"					where      " + 
					"						h.type_id=1      " + 
					"						and h.category_id=1    " + 
					"						and apprv.status_id=4      " + 
					"				) e      " + 
					"			  on e.ref = i.ref " + 
					"              " + 
					"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and e.ref is null    " + 
					"			 and status in ('assigned') "+
			";", nativeQuery=true)
	public List<Incident> getAssignedIncidentByMtd();
	
	@Query(value=
			" select      " + 
					"			 	*      " + 
					"			 from incident i      " + 
					"			  " + 
					"             left join       " + 
					"				(      " + 
					"					SELECT       " + 
					"						ref      " + 
					"					FROM       " + 
					"						mds_itop.exception_ticket e      " + 
					"					inner join       " + 
					"						exception_header h      " + 
					"						on e.exception_header_id = h.id      " + 
					"					inner join exception_approval apprv      " + 
					"						on h.approval_id = apprv.id      " + 
					"					where      " + 
					"						h.type_id=1      " + 
					"						and h.category_id=1    " + 
					"						and apprv.status_id=4      " + 
					"				) e      " + 
					"			  on e.ref = i.ref " + 
					"              " + 
					"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and e.ref is null    " + 
					"			 and ttr_passed='yes' "+
					"			 order by ttr_passed desc   " +
			";", nativeQuery=true)
	public List<Incident> getMissedIncidentByMtd();
	
	@Query(value=
			" select      " + 
					"			 	*      " + 
					"			 from incident i      " + 
					"			  " + 
					"             left join       " + 
					"				(      " + 
					"					SELECT       " + 
					"						ref      " + 
					"					FROM       " + 
					"						mds_itop.exception_ticket e      " + 
					"					inner join       " + 
					"						exception_header h      " + 
					"						on e.exception_header_id = h.id      " + 
					"					inner join exception_approval apprv      " + 
					"						on h.approval_id = apprv.id      " + 
					"					where      " + 
					"						h.type_id=1      " + 
					"						and h.category_id=1    " + 
					"						and apprv.status_id=4      " + 
					"				) e      " + 
					"			  on e.ref = i.ref " + 
					"              " + 
					"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01')        " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and e.ref is null    " + 
					"			 and ttr_passed='no' "+
					"			 order by ttr_passed desc   " +
			";", nativeQuery=true)
	public List<Incident> getAchievedIncidentByMtd();
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and agent_fullname like 'EXT%' "+
			"and status in ('pending') "+
			";", nativeQuery=true)
	public List<Incident> getPendingIncidentByMtd();
	
	@Query(value=" select      " + 
			"			 	*      " + 
			"			 from incident i       " + 
			"			 left join       " + 
			"				(      " + 
			"					SELECT       " + 
			"						ref      " + 
			"					FROM       " + 
			"						mds_itop.exception_ticket e      " + 
			"					inner join       " + 
			"						exception_header h      " + 
			"						on e.exception_header_id = h.id      " + 
			"					inner join exception_approval apprv      " + 
			"						on h.approval_id = apprv.id      " + 
			"					where      " + 
			"						h.type_id=1      " + 
			"						and h.category_id=1    " + 
			"						and apprv.status_id=4      " + 
			"				) e      " + 
			"			  on e.ref = i.ref   " + 
			"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  and       " + 
			"			 DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
			"			 and agent_fullname like 'EXT%'    " + 
			"			 and e.ref is null    " + 
			"			 order by ttr_passed desc   " + 
			"			 ; ", nativeQuery=true)
	public List<Incident> getAllIncidentByMonth(@Param("month") int month);
	
	@Query(value=
			" select      " + 
					"			 	*      " + 
					"			 from incident i       " + 
					"			 left join       " + 
					"				(      " + 
					"					SELECT       " + 
					"						ref      " + 
					"					FROM       " + 
					"						mds_itop.exception_ticket e      " + 
					"					inner join       " + 
					"						exception_header h      " + 
					"						on e.exception_header_id = h.id      " + 
					"					inner join exception_approval apprv      " + 
					"						on h.approval_id = apprv.id      " + 
					"					where      " + 
					"						h.type_id=1      " + 
					"						and h.category_id=1    " + 
					"						and apprv.status_id=4      " + 
					"				) e      " + 
					"			  on e.ref = i.ref   " + 
					"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  and       " + 
					"			 DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and e.ref is null    " + 
					"			 and status in ('assigned') "+
			";", nativeQuery=true)
	public List<Incident> getAssignedIncidentByMonth(@Param("month") int month);
	
	@Query(value=
			" select      " + 
					"			 	*      " + 
					"			 from incident i       " + 
					"			 left join       " + 
					"				(      " + 
					"					SELECT       " + 
					"						ref      " + 
					"					FROM       " + 
					"						mds_itop.exception_ticket e      " + 
					"					inner join       " + 
					"						exception_header h      " + 
					"						on e.exception_header_id = h.id      " + 
					"					inner join exception_approval apprv      " + 
					"						on h.approval_id = apprv.id      " + 
					"					where      " + 
					"						h.type_id=1      " + 
					"						and h.category_id=1    " + 
					"						and apprv.status_id=4      " + 
					"				) e      " + 
					"			  on e.ref = i.ref   " + 
					"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  and       " + 
					"			 DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and e.ref is null    " + 
					"			 and ttr_passed='yes' "+
			";", nativeQuery=true)
	public List<Incident> getMissedIncidentByMonth(@Param("month") int month);
	
	@Query(value=
			" select      " + 
					"			 	*      " + 
					"			 from incident i       " + 
					"			 left join       " + 
					"				(      " + 
					"					SELECT       " + 
					"						ref      " + 
					"					FROM       " + 
					"						mds_itop.exception_ticket e      " + 
					"					inner join       " + 
					"						exception_header h      " + 
					"						on e.exception_header_id = h.id      " + 
					"					inner join exception_approval apprv      " + 
					"						on h.approval_id = apprv.id      " + 
					"					where      " + 
					"						h.type_id=1      " + 
					"						and h.category_id=1    " + 
					"						and apprv.status_id=4      " + 
					"				) e      " + 
					"			  on e.ref = i.ref   " + 
					"			 where DATE_FORMAT(i.start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01')  and       " + 
					"			 DATE_FORMAT(i.start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01')    " + 
					"			 and agent_fullname like 'EXT%'    " + 
					"			 and e.ref is null    " + 
					"			 and ttr_passed='no' "+
			";", nativeQuery=true)
	public List<Incident> getAchievedIncidentByMonth(@Param("month") int month);
	
	@Query(value="select " + 
			"	* " + 
			"from incident " + 
			"where DATE_FORMAT(start_date,'%Y-%m-01') < DATE_FORMAT(NOW(),'%Y-%m-01') "+   
			"and DATE_FORMAT(start_date,'%Y-%m-01') >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH),'%Y-%m-01') "+
			"and agent_fullname like 'EXT%' "+
			"and status in ('pending') "+
			//"and team_name not like '%SUPPLIER PORTAL%' "+
			";", nativeQuery=true)
	public List<Incident> getPendingIncidentByMonth(@Param("month") int month);

}
