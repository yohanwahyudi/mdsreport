package com.vdi.reports.djasper.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.IncidentReportDAOService;
import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.batch.mds.repository.dao.PerfTeamDAOService;
import com.vdi.model.Incident;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.performance.PerformanceTeam;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.SummaryReport;
import com.vdi.tools.TimeTools;

@Component("supportAgentReportHelper")
public class SupportAgentReportHelper {

	@Autowired
	@Qualifier("weeklyPerfAllDAO")
	private PerfAllDAOService allWeeklyPerformance;
	@Autowired
	@Qualifier("weeklyPerfTeamDAO")
	private PerfTeamDAOService teamWeeklyPerformance;
	@Autowired
	@Qualifier("weeklyPerfAgentDAO")
	private PerfAgentDAOService agentWeeklyPerformance;

	@Autowired
	@Qualifier("monthlyPerfAllDAO")
	private PerfAllDAOService allMonthlyPerformance;
	@Autowired
	@Qualifier("monthlyPerfTeamDAO")
	private PerfTeamDAOService teamMonthlyPerformance;
	@Autowired
	@Qualifier("monthlyPerfAgentDAO")
	private PerfAgentDAOService agentMonthlyPerformance;

	@Autowired
	@Qualifier("incidentReportDAO")
	private IncidentReportDAOService saIncidentReport;

	@Autowired
	private TimeTools timeTools;

	private MasterReport weeklyReport;
	private MasterReport monthlyReport;

	private int currentMonth;
	private int currentWeek;
	private int prevMonth;
	private int prevWeek;

	private final Logger logger = LogManager.getLogger(SupportAgentReportHelper.class);

	public SupportAgentReportHelper() {

	}

	private void setWeeklyReport() {

		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeek = timeTools.getCurrentWeekYear();
		this.prevMonth = currentMonth - 1;
		this.prevWeek = currentWeek - 1;

		PerformanceOverall perfAll = allWeeklyPerformance.getPerformance(currentWeek, currentMonth);
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();

		List<SummaryReport> summaryList = new ArrayList<SummaryReport>();
		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString() + "%"));

		List<PerformanceTeam> performanceTeamList = teamWeeklyPerformance.getPerformance(currentWeek, currentMonth);
		List<PerformanceAgent> performanceAgentList = agentWeeklyPerformance.getPerformance(currentWeek, currentMonth);

		List<Incident> supportAgentPendingList = saIncidentReport.getPendingIncidentByWeek(currentMonth, prevWeek);
		List<Incident> supportAgentAssignList = saIncidentReport.getAssignedIncidentByWeek(currentMonth, prevWeek);
		List<Incident> supportAgentMissedList = saIncidentReport.getMissedIncidentByWeek(currentMonth, prevWeek);
		List<Incident> supportAgentIncidentList = saIncidentReport.getAllIncidentByWeek(currentMonth, prevWeek);

		MasterReport report = new MasterReport();
		report.setOverallAchievementList(summaryList);
		report.setPerformanceTeamList(performanceTeamList);
		report.setPerformanceSuportAgentList(performanceAgentList);
		report.setSupportAgentPendingList(supportAgentPendingList);
		report.setSupportAgentAssignList(supportAgentAssignList);
		report.setSupportAgentMissedList(supportAgentMissedList);
		report.setSupportAgentIncidentList(supportAgentIncidentList);

		this.weeklyReport = report;

	}

	private void setWeeklyReport(int month) {

		this.currentMonth = month;
		this.currentWeek = timeTools.getCurrentWeekYear();
		this.prevMonth = currentMonth - 1;
		this.prevWeek = currentWeek - 1;

		PerformanceOverall perfAll = allWeeklyPerformance.getPerformance(currentWeek, currentMonth);
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();

		List<SummaryReport> summaryList = new ArrayList<SummaryReport>();
		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString() + "%"));

		List<PerformanceTeam> performanceTeamList = teamWeeklyPerformance.getPerformance(currentWeek, currentMonth);
		List<PerformanceAgent> performanceAgentList = agentWeeklyPerformance.getPerformance(currentWeek, currentMonth);

		List<Incident> supportAgentPendingList = saIncidentReport.getPendingIncidentByWeek(currentMonth, prevWeek);
		List<Incident> supportAgentAssignList = saIncidentReport.getAssignedIncidentByWeek(currentMonth, prevWeek);
		List<Incident> supportAgentMissedList = saIncidentReport.getMissedIncidentByWeek(currentMonth, prevWeek);
		List<Incident> supportAgentIncidentList = saIncidentReport.getAllIncidentByWeek(currentMonth, prevWeek);

		MasterReport report = new MasterReport();
		report.setOverallAchievementList(summaryList);
		report.setPerformanceTeamList(performanceTeamList);
		report.setPerformanceSuportAgentList(performanceAgentList);
		report.setSupportAgentPendingList(supportAgentPendingList);
		report.setSupportAgentAssignList(supportAgentAssignList);
		report.setSupportAgentMissedList(supportAgentMissedList);
		report.setSupportAgentIncidentList(supportAgentIncidentList);

		this.weeklyReport = report;

	}

	public MasterReport getWeeklyReport() {

		setWeeklyReport();

		return this.weeklyReport;
	}

	public MasterReport getWeeklyReport(int month) {

		setWeeklyReport(month);

		return this.weeklyReport;
	}

	private void setMonthlyReport() {

		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeek = timeTools.getCurrentWeekYear();
		this.prevMonth = currentMonth - 1;
		this.prevWeek = currentWeek - 1;

		List<SummaryReport> summaryList = new ArrayList<SummaryReport>();
		PerformanceOverall perfAll = allMonthlyPerformance.getPerformance();
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();

		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString() + "%"));

		List<PerformanceTeam> performanceTeamList = teamMonthlyPerformance.getPerformance();
		List<PerformanceAgent> performanceAgentList = agentMonthlyPerformance.getPerformance();

		// incident
		List<Incident> supportAgentMissedList = saIncidentReport.getMissedIncidentByMonth(prevMonth);
		List<Incident> supportAgentPendingList = saIncidentReport.getPendingIncidentByMonth(prevMonth);
		List<Incident> supportAgentAssignedList = saIncidentReport.getAssignedIncidentByMonth(prevMonth);
		List<Incident> supportAgentIncidentList = saIncidentReport.getAllIncidentByMonth(prevMonth);

		MasterReport report = new MasterReport();
		report.setOverallAchievementList(summaryList);
		report.setPerformanceTeamList(performanceTeamList);
		report.setPerformanceSuportAgentList(performanceAgentList);
		report.setSupportAgentPendingList(supportAgentPendingList);
		report.setSupportAgentAssignList(supportAgentAssignedList);
		report.setSupportAgentMissedList(supportAgentMissedList);
		report.setSupportAgentIncidentList(supportAgentIncidentList);

		logger.debug("totalTicket: " + totalTicket);

		this.monthlyReport = report;
	}

	public MasterReport getMonthlyReport() {

		setMonthlyReport();

		return this.monthlyReport;
	}

}
