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

@Service("monthlyIncidentSAReport")
public class MonthlyIncidentSAReport implements ReportService {

	@Autowired
	@Qualifier("monthlyPerfAllDAO")
	private PerfAllDAOService all;

	@Autowired
	@Qualifier("monthlyPerfTeamDAO")
	private PerfTeamDAOService team;

	@Autowired
	@Qualifier("monthlyPerfAgentDAO")
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
	private List<Incident> supportAgentAssignedList;
	private List<Incident> supportAgentMissedList;
	private List<Incident> supportAgentIncidentList;

	private PerformanceReport report;
	private String prevMonthStr;
	private int currentMonth;
	private int prevMonth;

	public MonthlyIncidentSAReport() {
		this.prevMonthStr = TimeStatic.prevMonthStr;
		this.currentMonth = TimeStatic.currentMonth;
		this.prevMonth = currentMonth - 1;
	}

	@Override
	public DynamicReport buildReport() {

		//get master
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("VDI SUPPORT AGENT PERFORMANCE BASED ON iTop");
		master.setSubtitle(prevMonthStr.toUpperCase()+" "+TimeStatic.currentYear);
		
		// add params
		params.put("summaryReport", reportList.get(0).getSummaryReport());
		params.put("teamReport", reportList.get(0).getPerformanceTeamList());
		params.put("agentReport", reportList.get(0).getPerformanceAgentList());
		params.put("missedReport", reportList.get(0).getSupportAgentMissedList());
		params.put("pendingReport", reportList.get(0).getSupportAgentPendingList());
		params.put("assignedReport", reportList.get(0).getSupportAgentAssignList());
		params.put("incidentListReport", reportList.get(0).getSupportAgentIncidentList());
		
		// add subreport
		DynamicReport subReportAll = templateBuilders.getSummarySub2();
		DynamicReport subReportTeam = templateBuilders.getTeamSub();
		DynamicReport subReportAgent = templateBuilders.getAgentSub();
		DynamicReport subReportMissed = templateBuilders.getMissedSub();
		DynamicReport subReportPending = templateBuilders.getPendingSub();
		DynamicReport subReportAssigned = templateBuilders.getAssignedSub();
		DynamicReport subReportIncident = templateBuilders.getIncidentListSub();
		
		master.addConcatenatedReport(subReportAll, new ClassicLayoutManager(), "summaryReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(subReportTeam, new ClassicLayoutManager(), "teamReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(subReportAgent, new ClassicLayoutManager(), "agentReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);		
		master.addConcatenatedReport(subReportMissed, new ClassicLayoutManager(), "missedReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);	
		master.addConcatenatedReport(subReportPending, new ClassicLayoutManager(), "pendingReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);	
		master.addConcatenatedReport(subReportAssigned, new ClassicLayoutManager(), "assignedReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);	
		master.addConcatenatedReport(subReportIncident, new ClassicLayoutManager(), "incidentListReport",
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

	private void setPerformanceReport() {
		
		report = new PerformanceReport();
		
		//performance
		summaryList = new ArrayList<SummaryReport>();
		PerformanceOverall perfAll = all.getPerformance();
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();		
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString()+"%"));
		
		performanceTeamList = team.getPerformance();
		performanceAgentList = agent.getPerformance();
		
		//incident
		supportAgentMissedList = incident.getMissedIncidentByMonth(prevMonth);
		supportAgentPendingList = incident.getPendingIncidentByMonth(prevMonth);
		supportAgentAssignedList = incident.getAssignedIncidentByMonth(prevMonth);
		supportAgentIncidentList = incident.getAllIncidentByMonth(prevMonth);
		
		//setreport
		report.setSummaryReport(summaryList);
		report.setPerformanceTeamList(performanceTeamList);
		report.setPerformanceAgentList(performanceAgentList);
		
		report.setSupportAgentMissedList(supportAgentMissedList);
		report.setSupportAgentPendingList(supportAgentPendingList);
		report.setSupportAgentAssignList(supportAgentAssignedList);
		report.setSupportAgentIncidentList(supportAgentIncidentList);

		List<PerformanceReport> list = new ArrayList<PerformanceReport>();
		list.add(report);

		this.reportList = list;
	}

	@Override
	public List<PerformanceReport> getPerformanceReport() {
		return reportList;
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
