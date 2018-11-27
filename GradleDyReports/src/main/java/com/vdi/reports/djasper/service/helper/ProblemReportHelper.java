package com.vdi.reports.djasper.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.DedicatedAgentDAOService;
import com.vdi.model.performance.DedicatedAgentSummary;
import com.vdi.model.performance.DedicatedAgentTeam;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.SummaryReport;

@Component("problemReportHelper")
public class ProblemReportHelper {

	@Autowired
	@Qualifier("dedicatedDAO")
	private DedicatedAgentDAOService dedicatedDAO;

	private final String CATEGORY_PROBLEM = "problem";

	private MasterReport masterReport;

	public ProblemReportHelper() {
		
		masterReport = new MasterReport();
		
	}
	
	private void setProblemSummary() {

		List<String> fieldList = new ArrayList<String>();
		fieldList.add("Ticket Total");
		fieldList.add("Open");
		fieldList.add("Resolved/ Closed");

		DedicatedAgentSummary dedicatedSummary = dedicatedDAO.getPreviousMonthSummary(CATEGORY_PROBLEM);
		Integer totalTicket = dedicatedSummary.getTotalTicket();
		Integer openTicket = dedicatedSummary.getTotalOpen();
		Integer resolvedClosed = dedicatedSummary.getTotalClosedResolved();
		
		List<SummaryReport> summaryReportList = new ArrayList<SummaryReport>();
		summaryReportList.add(new SummaryReport(fieldList.get(0), totalTicket.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(1), openTicket.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(2), resolvedClosed.toString()));
		
		this.masterReport.setOverallAchievementList(summaryReportList);
	}
	
	private void setProblemDedicatedTeam() {
		
		List<Object[]> team = dedicatedDAO.getPreviousMonthTeam(CATEGORY_PROBLEM);
		
		List<DedicatedAgentTeam> teamList = new ArrayList<DedicatedAgentTeam>();
		for(Object[] objectArr : team) {
				
			DedicatedAgentTeam dedicatedTeam = new DedicatedAgentTeam();
			for (int i=0; i<objectArr.length; i++) {
				
				if(i==0) {
					String name =  (String)objectArr[i];
					dedicatedTeam.setTeamName(name);
				} else if (i==1) {
					Integer ticketTotal = ((BigDecimal)objectArr[i]).intValue();
					dedicatedTeam.setTotalTicket(ticketTotal);
				} else {
					Float contribution = ((BigDecimal)objectArr[i]).floatValue();
					dedicatedTeam.setContribution(contribution);
				}				
				
			}
			teamList.add(dedicatedTeam);			
		}
		
		this.masterReport.setDedicatedTeamList(teamList);
		
	 }

	private void setProblemDedicatedAgent() {

		List<DedicatedAgentTeam> dedicatedAgentTeam = dedicatedDAO.getPreviousMonthAgentTeam(CATEGORY_PROBLEM);
		this.masterReport.setDedicatedAgentTeamList(dedicatedAgentTeam);

	}

	public MasterReport getProblemReport() {

		setProblemSummary();
		setProblemDedicatedTeam();
		setProblemDedicatedAgent();

		return masterReport;

	}
	
	public List<MasterReport> getProblemReportList(){
		
		List<MasterReport> problemReportList = new ArrayList<MasterReport>();
		problemReportList.add(masterReport);
		
		return problemReportList;
		
	}

}
