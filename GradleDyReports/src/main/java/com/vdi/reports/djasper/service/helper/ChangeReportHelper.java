package com.vdi.reports.djasper.service.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.DedicatedAgentDAOService;
import com.vdi.batch.mds.repository.dao.MonthlyProblemChangeDAOService;
import com.vdi.model.Change;
import com.vdi.model.performance.DedicatedAgentSummary;
import com.vdi.model.performance.DedicatedAgentTeam;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.SummaryReport;

@Component("changeReportHelper")
public class ChangeReportHelper {

	private final Logger logger = LogManager.getLogger(ChangeReportHelper.class);
	
	@Autowired
	@Qualifier("dedicatedDAO")
	private DedicatedAgentDAOService dedicatedDAO;

	@Autowired
	private MonthlyProblemChangeDAOService changeDAO;

	private final String CATEGORY_CHANGE = "change";

	private MasterReport masterReport;

	public ChangeReportHelper() {

		masterReport = new MasterReport();

	}

	private void setChangeSummary() {

		List<String> fieldList = new ArrayList<String>();
		fieldList.add("Ticket Total");
		fieldList.add("Open");
		fieldList.add("Closed");
		fieldList.add("Rejected");

		DedicatedAgentSummary dedicatedSummary = dedicatedDAO.getPreviousMonthSummary(CATEGORY_CHANGE);
		Integer totalTicket = dedicatedSummary.getTotalTicket();
		Integer openTicket = dedicatedSummary.getTotalOpen();
		Integer resolvedClosed = dedicatedSummary.getTotalClosedResolved();
		Integer rejected = dedicatedSummary.getTotalRejected();

		List<SummaryReport> summaryReportList = new ArrayList<SummaryReport>();
		summaryReportList.add(new SummaryReport(fieldList.get(0), totalTicket.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(1), openTicket.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(2), resolvedClosed.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(3), rejected.toString()));

		this.masterReport.setOverallAchievementList(summaryReportList);
	}

	private void setChangeDedicatedTeam() {

		List<Object[]> team = dedicatedDAO.getPreviousMonthTeam(CATEGORY_CHANGE);

		List<DedicatedAgentTeam> teamList = new ArrayList<DedicatedAgentTeam>();
		for (Object[] objectArr : team) {

			DedicatedAgentTeam dedicatedTeam = new DedicatedAgentTeam();
			for (int i = 0; i < objectArr.length; i++) {

				if (i == 0) {
					String name = (String) objectArr[i];
					dedicatedTeam.setTeamName(name);
				} else if (i == 1) {
					Integer ticketTotal = ((BigDecimal) objectArr[i]).intValue();
					dedicatedTeam.setTotalTicket(ticketTotal);
				} else {
					Float contribution = ((BigDecimal) objectArr[i]).floatValue();
					dedicatedTeam.setContribution(contribution);
				}

			}
			teamList.add(dedicatedTeam);
		}

		this.masterReport.setDedicatedTeamList(teamList);

	}

	private void setChangeDedicatedAgent() {

		List<DedicatedAgentTeam> dedicatedAgentTeam = dedicatedDAO.getPreviousMonthAgentTeam(CATEGORY_CHANGE);
		this.masterReport.setDedicatedAgentTeamList(dedicatedAgentTeam);

	}

	private void setAllChangeSummary() {

		List<String> fieldList = new ArrayList<String>();
		fieldList.add("Ticket Total");
		fieldList.add("Open");
		fieldList.add("Closed");
		fieldList.add("Rejected");

		Object[] allChangeSummary = changeDAO.getAllChangeSummary().get(0);
		Integer totalTicket = ((BigInteger) allChangeSummary[0]).intValue();
		Integer openTicket = ((BigInteger) allChangeSummary[1]).intValue();
		Integer resolvedClosed = ((BigInteger) allChangeSummary[2]).intValue();
		Integer rejected = ((BigInteger) allChangeSummary[3]).intValue();

		List<SummaryReport> summaryReportList = new ArrayList<SummaryReport>();
		summaryReportList.add(new SummaryReport(fieldList.get(0), totalTicket.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(1), openTicket.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(2), resolvedClosed.toString()));
		summaryReportList.add(new SummaryReport(fieldList.get(3), rejected.toString()));

		this.masterReport.setSupportAgentSummaryList(summaryReportList);
	}

	private void setAllOpenedChangeByTeamAgent() {

		List<Object[]> team = changeDAO.getAllOpenedChangeByTeamAgent();
		
		List<DedicatedAgentTeam> teamList = new ArrayList<DedicatedAgentTeam>();
		for (Object[] objectArr : team) {

			DedicatedAgentTeam dedicatedTeam = new DedicatedAgentTeam();
			for (int i = 0; i < objectArr.length; i++) {

				if (i == 0) {
					String name = (String) objectArr[i];
					dedicatedTeam.setTeamName(name);
				} else if(i == 1){
					String agent= ((String) objectArr[i]);
					dedicatedTeam.setAgentName(agent);
				} else {
					Integer ticketTotal = ((BigInteger) objectArr[i]).intValue();
					dedicatedTeam.setTotalTicket(ticketTotal);
				}

			}
			teamList.add(dedicatedTeam);
		}

		this.masterReport.setDedicatedTeamList1(teamList);

	}

	private void setAllOpenedChangeList() {

		List<Change> allOpenedChangeList = changeDAO.getAllOpenedChangeList();
		this.masterReport.setChangeList(allOpenedChangeList);

	}

	public MasterReport getChangeReport() {

		setChangeSummary();
		setChangeDedicatedTeam();
		setChangeDedicatedAgent();
		setAllChangeSummary();
		setAllOpenedChangeByTeamAgent();
//		setAllOpenedChangeList();

		return masterReport;

	}

	public List<MasterReport> getChangeReportList() {

		List<MasterReport> changeReportList = new ArrayList<MasterReport>();
		changeReportList.add(masterReport);

		return changeReportList;

	}

}
