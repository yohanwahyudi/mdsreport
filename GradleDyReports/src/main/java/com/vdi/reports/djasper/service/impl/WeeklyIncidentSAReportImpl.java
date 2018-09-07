package com.vdi.reports.djasper.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.repository.dao.IncidentReportDAOService;
import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.batch.mds.repository.dao.PerfTeamDAOService;
import com.vdi.model.Incident;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.performance.PerformanceTeam;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.PerformanceReport;
import com.vdi.reports.djasper.model.SummaryReport;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.reports.djasper.templates.TemplateBuildersReport;
import com.vdi.tools.TimeStatic;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service("weeklyIncidentSAReport")
public class WeeklyIncidentSAReportImpl implements ReportService {

	@Autowired
	@Qualifier("weeklyPerfAllDAO")
	private PerfAllDAOService all;

	@Autowired
	@Qualifier("weeklyPerfTeamDAO")
	private PerfTeamDAOService team;

	@Autowired
	@Qualifier("weeklyPerfAgentDAO")
	private PerfAgentDAOService agent;

	@Autowired
	@Qualifier("incidentReportDAO")
	private IncidentReportDAOService incident;

	@Autowired
	private TemplateBuildersReport templateBuilders;

	protected final Map<String, Object> params = new HashMap<String, Object>();

	private List<PerformanceReport> reportList;

	private List<SummaryReport> summaryList;
	private List<PerformanceTeam> performanceTeamList;
	private List<PerformanceAgent> performanceAgentList;
	private List<Incident> supportAgentPendingList;
	private List<Incident> supportAgentAssignList;
	private List<Incident> supportAgentMissedList;
	private List<Incident> supportAgentIncidentList;

	private PerformanceReport report;
	private int currentYear;
	private String currentMonthStr;
	private int currentMonth;
	private int currentWeek;
	private int prevWeek;
	private int prevWeekMonth;

	public WeeklyIncidentSAReportImpl() {

		this.currentYear = TimeStatic.currentYear;
		this.currentMonthStr = TimeStatic.currentMonthStr;
		this.currentMonth = TimeStatic.currentMonth;
		this.currentWeek = TimeStatic.currentWeekYear;
		this.prevWeek = currentWeek - 1;
		this.prevWeekMonth = TimeStatic.currentWeekMonth - 1;
	}

	@Override
	public DynamicReport buildReport() {

		// get master
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("VDI SUPPORT AGENT PERFORMANCE BASED ON iTop");
		master.setSubtitle("WEEK " + prevWeekMonth + " - " + currentMonthStr.toUpperCase() + " "
				+ currentYear);

		// add params
		params.put("summaryReport", reportList.get(0).getSummaryReport());
		params.put("performanceTeamList", reportList.get(0).getPerformanceTeamList());
		params.put("performanceAgentList", reportList.get(0).getPerformanceAgentList());
		params.put("supportAgentPendingList", reportList.get(0).getSupportAgentPendingList());
		params.put("supportAgentAssignList", reportList.get(0).getSupportAgentAssignList());
		params.put("supportAgentMissedList", reportList.get(0).getSupportAgentMissedList());
		params.put("supportAgentIncidentList", reportList.get(0).getSupportAgentIncidentList());

		// add subreport
		DynamicReport subReportAll = templateBuilders.getSummarySub2();
		DynamicReport subReportTeam = templateBuilders.getTeamSub();
		DynamicReport subReportAgent = templateBuilders.getAgentSub();
		DynamicReport subReportMissed = templateBuilders.getMissedSub();
		DynamicReport subReportPending = templateBuilders.getPendingSub();
		DynamicReport subReportAssigned = templateBuilders.getAssignedSub();
		DynamicReport subReportIncident = templateBuilders.getIncidentListSub();

		master.addConcatenatedReport(subReportAll, new ClassicLayoutManager(), "summaryReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false)
				.addConcatenatedReport(subReportTeam, new ClassicLayoutManager(), "performanceTeamList",
						DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false)
				.addConcatenatedReport(subReportAgent, new ClassicLayoutManager(), "performanceAgentList",
						DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true)
				.addConcatenatedReport(subReportMissed, new ClassicLayoutManager(), "supportAgentMissedList",
						DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true)
				.addConcatenatedReport(subReportPending, new ClassicLayoutManager(), "supportAgentPendingList",
						DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false)
				.addConcatenatedReport(subReportAssigned, new ClassicLayoutManager(), "supportAgentAssignList",
						DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false)
				.addConcatenatedReport(subReportIncident, new ClassicLayoutManager(), "supportAgentIncidentList",
						DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);

		return master.build();
	}

	@Override
	public JasperPrint getReport() throws JRException, Exception {

		JRDataSource ds = getDataSource();
		JasperReport jr = DynamicJasperHelper.generateJasperReport(buildReport(), new ClassicLayoutManager(), params);
		JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);

		return jp;
	}

	@Override
	public JRDataSource getDataSource() {

		setPerformanceReport();

		return new JRBeanCollectionDataSource(getPerformanceReport());

	}

	public List<PerformanceReport> getPerformanceReport() {

		return reportList;
	}

	private void setPerformanceReport() {

		report = new PerformanceReport();

		summaryList = new ArrayList<SummaryReport>();
		PerformanceOverall perfAll = all.getPerformance(currentWeek, currentMonth);
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString()+"%"));

		performanceTeamList = team.getPerformance(currentWeek, currentMonth);
		performanceAgentList = agent.getPerformance(currentWeek, currentMonth);

		supportAgentPendingList = incident.getPendingIncidentByWeek(currentMonth, prevWeek);
		supportAgentAssignList = incident.getAssignedIncidentByWeek(currentMonth, prevWeek);
		supportAgentMissedList = incident.getMissedIncidentByWeek(currentMonth, prevWeek);
		supportAgentIncidentList = incident.getAllIncidentByWeek(currentMonth, prevWeek);

		report.setTotalTicket(totalTicket);
		report.setTotalAchieved(totalAchieved);
		report.setTotalMissed(totalMissed);
		report.setAchievement(achievement);
		report.setSummaryReport(summaryList);
		report.setPerformanceTeamList(performanceTeamList);
		report.setPerformanceAgentList(performanceAgentList);
		report.setSupportAgentPendingList(supportAgentPendingList);
		report.setSupportAgentAssignList(supportAgentAssignList);
		report.setSupportAgentMissedList(supportAgentMissedList);
		report.setSupportAgentIncidentList(supportAgentIncidentList);

		List<PerformanceReport> list = new ArrayList<PerformanceReport>();
		list.add(report);

		this.reportList = list;
	}

	@Override
	public JasperPrint getReport(String period) throws JRException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MasterReport> getPerformanceReport(String period) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
