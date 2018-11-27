package com.vdi.batch.mds.helper.monthly;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.DedicatedAgentDAOService;
import com.vdi.batch.mds.repository.dao.MonthlyProblemChangeDAOService;
import com.vdi.model.performance.DedicatedAgentSummary;
import com.vdi.model.performance.DedicatedAgentTeam;
import com.vdi.tools.TimeTools;

@Component("populateDedicatedAgent")
public class PopulateDedicatedAgent {

	private final Logger logger = LogManager.getLogger(PopulateDedicatedAgent.class);
	private final String CATEGORY_PROBLEM = "problem";
	private final String CATEGORY_CHANGE = "change";

	@Autowired
	@Qualifier("dedicatedDAO")
	private DedicatedAgentDAOService dedicatedDao;

	@Autowired
	@Qualifier("monthlyProblemChangeDAO")
	private MonthlyProblemChangeDAOService problemChangeDao;

	@Autowired
	private TimeTools timeTools;
	
	public void insertData() throws Exception {
		
		dedicatedDao.addAllCollections(getProblemSummary());
		dedicatedDao.addAllCollections(getProblemAgentTeam());
		
		dedicatedDao.addAllCollections(getChangeSummary());
		dedicatedDao.addAllCollections(getChangeAgentTeam());
		
	}

	private List<DedicatedAgentSummary> getProblemSummary() throws Exception {

		logger.info("Start populate problem summary ");	
		
		List<DedicatedAgentSummary> reportList = new ArrayList<DedicatedAgentSummary>();
		List<Object[]> problemList = problemChangeDao.getProblemSummary();
		
		Object[] problemObj = problemList.get(0);

		logger.info("size: "+problemList.size());	
		
		if (problemObj != null) {
			
			DedicatedAgentSummary summary = new DedicatedAgentSummary();
			summary.setTotalTicket(((BigInteger) problemObj[0]).intValue());
			summary.setTotalOpen(((BigInteger) problemObj[1]).intValue());
			summary.setTotalClosedResolved(((BigInteger) problemObj[2]).intValue());
			summary.setCategory(CATEGORY_PROBLEM);
			summary.setReport_dt(timeTools.getPrevMonthLocalDate());

			DedicatedAgentSummary existingReport = dedicatedDao.getPreviousMonthSummary(CATEGORY_PROBLEM);
			if (existingReport != null) {
				
				logger.info("updating existing problem summary ");	

				existingReport.setTotalTicket(summary.getTotalTicket());
				existingReport.setTotalOpen(summary.getTotalOpen());
				existingReport.setTotalClosedResolved(summary.getTotalClosedResolved());
				
				reportList.add(existingReport);
				
			} else {
				
				reportList.add(summary);

			}
			
			logger.info("end populate problem summary ");

		} else {
			logger.info("ERROR in populate problem summary ");
			throw new Exception();
		}
		
			
		
		return reportList;

	}
	
	private List<DedicatedAgentTeam> getProblemAgentTeam() throws Exception{
		
		logger.info("Start populate problem team ");
		
		List<DedicatedAgentTeam> reportList = new ArrayList<DedicatedAgentTeam>();
		
		List<Object[]> newProblemObj = problemChangeDao.getProblemAchievementByAgent();
		if(newProblemObj!=null && newProblemObj.size()>0) {
			
			List<DedicatedAgentTeam> newList = new ArrayList<DedicatedAgentTeam>();
			Map<String, DedicatedAgentTeam> newMap = new HashMap<String, DedicatedAgentTeam>();
			
			for(Object[] obj : newProblemObj ) {
				
				DedicatedAgentTeam newReport = new DedicatedAgentTeam();
				newReport.setTeamName((String) obj[0]);
				newReport.setAgentName((String) obj[1]);
				newReport.setTotalTicket(((BigInteger) obj[2]).intValue());
				newReport.setContribution(((BigDecimal) obj[3]).floatValue());
				newReport.setTeamContribution(((BigDecimal) obj[4]).floatValue());
				newReport.setCategory(CATEGORY_PROBLEM);
				newReport.setReport_dt(timeTools.getPrevMonthLocalDate());
				
				newList.add(newReport);
				newMap.put(newReport.getAgentName(), newReport);
			}
			
			// compare with existing ticket			
			List<DedicatedAgentTeam> existingReportList = new ArrayList<DedicatedAgentTeam>();
			existingReportList = dedicatedDao.getPreviousMonthAgentTeam(CATEGORY_PROBLEM); 
			if(existingReportList!=null && existingReportList.size()>0) {
				
				logger.info("updating existing problem team ");
				
				for(DedicatedAgentTeam existing : existingReportList) {
					
					DedicatedAgentTeam updated = newMap.get(existing.getAgentName());
					existing.setTeamName(updated.getTeamName());
//					existing.setAgentName(updated.getAgentName());					
					existing.setTotalTicket(updated.getTotalTicket());
					existing.setContribution(updated.getContribution());
					existing.setTeamContribution(updated.getTeamContribution());
					
					reportList.add(existing);
				}
				
			} else {
				
				reportList = newList;
				
			}
			
			logger.info("end populate problem team ");
			
		} else {
			
			logger.info("ERROR in populate problem team ");
			throw new Exception();
			
		}
		
		return reportList;
		
	}
	
	private List<DedicatedAgentSummary> getChangeSummary() throws Exception {

		logger.info("Start populate change summary ");	
		
		List<DedicatedAgentSummary> reportList = new ArrayList<DedicatedAgentSummary>();
		List<Object[]> changeList = problemChangeDao.getChangeSummary();
		
		Object[] changeObj = changeList.get(0);

		logger.info("size: "+changeList.size());	
		
		if (changeObj != null) {
			
			DedicatedAgentSummary summary = new DedicatedAgentSummary();
			summary.setTotalTicket(((BigInteger) changeObj[0]).intValue());
			summary.setTotalOpen(((BigInteger) changeObj[1]).intValue());
			summary.setTotalClosedResolved(((BigInteger) changeObj[2]).intValue());
			summary.setTotalRejected(((BigInteger) changeObj[3]).intValue());
			summary.setCategory(CATEGORY_CHANGE);
			summary.setReport_dt(timeTools.getPrevMonthLocalDate());

			DedicatedAgentSummary existingReport = dedicatedDao.getPreviousMonthSummary(CATEGORY_CHANGE);
			if (existingReport != null) {
				
				logger.info("updating existing change summary ");	

				existingReport.setTotalTicket(summary.getTotalTicket());
				existingReport.setTotalOpen(summary.getTotalOpen());
				existingReport.setTotalClosedResolved(summary.getTotalClosedResolved());
				existingReport.setTotalRejected(summary.getTotalRejected());
				
				reportList.add(existingReport);
				
			} else {
				
				reportList.add(summary);

			}
			
			logger.info("end populate change summary ");

		} else {
			logger.info("ERROR in populate change summary ");
			throw new Exception();
		}		
		
		return reportList;

	}
	
private List<DedicatedAgentTeam> getChangeAgentTeam() throws Exception{
		
		logger.info("Start populate change team ");
		
		List<DedicatedAgentTeam> reportList = new ArrayList<DedicatedAgentTeam>();
		
		List<Object[]> newProblemObj = problemChangeDao.getChangeAchievementByAgent();
		if(newProblemObj!=null && newProblemObj.size()>0) {
			
			List<DedicatedAgentTeam> newList = new ArrayList<DedicatedAgentTeam>();
			Map<String, DedicatedAgentTeam> newMap = new HashMap<String, DedicatedAgentTeam>();
			
			for(Object[] obj : newProblemObj ) {
				
				DedicatedAgentTeam newReport = new DedicatedAgentTeam();
				newReport.setTeamName((String) obj[0]);
				newReport.setAgentName((String) obj[1]);
				newReport.setTotalTicket(((BigInteger) obj[2]).intValue());
				newReport.setContribution(((BigDecimal) obj[3]).floatValue());
				newReport.setTeamContribution(((BigDecimal) obj[4]).floatValue());
				newReport.setCategory(CATEGORY_CHANGE);
				newReport.setReport_dt(timeTools.getPrevMonthLocalDate());
				
				newList.add(newReport);
				newMap.put(newReport.getAgentName(), newReport);
			}
			
			// compare with existing ticket			
			List<DedicatedAgentTeam> existingReportList = new ArrayList<DedicatedAgentTeam>();
			existingReportList = dedicatedDao.getPreviousMonthAgentTeam(CATEGORY_CHANGE); 
			if(existingReportList!=null && existingReportList.size()>0) {
				
				logger.info("updating existing change team ");
				
				for(DedicatedAgentTeam existing : existingReportList) {
					
					DedicatedAgentTeam updated = newMap.get(existing.getAgentName());
					existing.setTeamName(updated.getTeamName());
//					existing.setAgentName(updated.getAgentName());					
					existing.setTotalTicket(updated.getTotalTicket());
					existing.setContribution(updated.getContribution());
					existing.setTeamContribution(updated.getTeamContribution());
					
					reportList.add(existing);
				}
				
			} else {
				
				reportList = newList;
				
			}
			
			logger.info("end populate change team ");
			
		} else {
			
			logger.info("ERROR in populate change team ");
			throw new Exception();
			
		}
		
		return reportList;
		
	}

}
