package com.vdi.reports.djasper.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.batch.mds.repository.dao.ServiceDeskReportDAO;
import com.vdi.batch.mds.repository.dao.StagingUserRequestDAOService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.staging.StagingServiceDesk;
import com.vdi.model.staging.StagingUserRequest;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.PerformanceReport;
import com.vdi.reports.djasper.model.SummaryReport;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.reports.djasper.templates.TemplateBuildersReport;
import com.vdi.reports.djasper.templates.TemplateStylesReport;
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

@Configuration("monthlyIncidentSDReport")
public class MonthlyIncidentSDReportImpl implements ReportService {

	@Autowired
	@Qualifier("monthlySDPerfAllDAO")
	private PerfAllDAOService all;

	@Autowired
	@Qualifier("monthlySDPerfAgentDAO")
	private PerfAgentDAOService agent;

	@Autowired
	@Qualifier("serviceDeskReportDAO")
	private ServiceDeskReportDAO sdReport;

	@Autowired
	@Qualifier("monthlyURPerfAllDAO")
	private PerfAllDAOService allUR;

	@Autowired
	@Qualifier("monthlyURPerfAgentDAO")
	private PerfAgentDAOService agentUR;

	@Autowired
	@Qualifier("stagingUserRequestDAO")
	private StagingUserRequestDAOService urReport;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private TemplateBuildersReport templateBuilders;

	@Autowired
	private TemplateStylesReport templateStyles;

	private int currentYear;
	private int currentMonth;
	private int previousMonth;
	private String prevMonthStr;
	private String otherDcuName;
	private String[] personArray;

	protected final Map<String, Object> params = new HashMap<String, Object>();

	private List<PerformanceReport> reportList;
	private List<PerformanceReport> urReportList;
	private List<PerformanceReport> perfAgentList;
	private List<SummaryReport> summaryList;

	private final Logger logger = LogManager.getLogger(MonthlyIncidentSDReportImpl.class);
	
	public MonthlyIncidentSDReportImpl() {

		this.currentYear = TimeStatic.currentYear;
		this.currentMonth = TimeStatic.currentMonth;
		this.previousMonth = currentMonth - 1;
		this.prevMonthStr = TimeStatic.prevMonthStr;

	}

	@Override
	public DynamicReport buildReport() {
		// get serviceDeskPersons
		List<PerformanceReport> serviceDeskPersonsList = new ArrayList<PerformanceReport>();
		serviceDeskPersonsList = getCombinedPersons();

		// get master report
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("VDI SERVICE DESK PERFORMANCE BASED ON iTop");
		master.setSubtitle(prevMonthStr.toUpperCase() + " " + currentYear);

		// set params
		PerformanceReport combinedReport = new PerformanceReport();
		combinedReport = getCombinedReport().get(0);
		
		params.put("summaryReport", combinedReport.getSummaryReport());
		params.put("servicedeskIncidentList", combinedReport.getServiceDeskIncidentList());
		params.put("userrequestIncidentList", combinedReport.getUserRequestIncidentList());
//		params.put("performanceAgentList", perfAgentList.get(0).getPerformanceAgentList());
//		params.put("performanceURAgentList", combinedReport.getPerformanceURAgentList());

		// add subreports
		DynamicReport subReportAll = templateBuilders.getSummarySub2();
		DynamicReport subReportPerson = templateBuilders.getServiceDeskPersonSub();
					  subReportPerson.getColumns().get(4).setStyle(templateStyles.getArialDetailGreenAgentStyle());
//		DynamicReport subReportAgent = templateBuilders.getServiceDeskAgentSub();
//		DynamicReport subReportURAgent = templateBuilders.getUserRequestAgentSub();
		DynamicReport subReportIncident = templateBuilders.getServiceDeskIncidentSub();
		DynamicReport subReportURIncident = templateBuilders.getUserRequestIncidentSub();

		master.addConcatenatedReport(subReportAll, new ClassicLayoutManager(), "summaryReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);

		for (int i = 0; i < serviceDeskPersonsList.size(); i++) {

			List<PerformanceAgent> perfAgent = new ArrayList<PerformanceAgent>();
			perfAgent = serviceDeskPersonsList.get(i).getPerformanceAgentList();
			
			params.put("personSub" + i, perfAgent);
			master.addConcatenatedReport(subReportPerson, new ClassicLayoutManager(), "personSub" + i,
					DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		}

//disable
//		master.addConcatenatedReport(subReportAgent, new ClassicLayoutManager(), "performanceAgentList",
//				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
//disable
//		master.addConcatenatedReport(subReportURAgent, new ClassicLayoutManager(), "performanceURAgentList",
//				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(subReportIncident, new ClassicLayoutManager(), "servicedeskIncidentList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(subReportURIncident, new ClassicLayoutManager(), "userrequestIncidentList",
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
		setURPerformanceReport();

		return new JRBeanCollectionDataSource(getPerformanceReport());
	}

	private void setPerformanceReport() {

		PerformanceReport report = new PerformanceReport();

		summaryList = new ArrayList<SummaryReport>();
		PerformanceOverall perfAll = all.getPerformance();
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString()));

		List<PerformanceAgent> performanceAgentList = new ArrayList<PerformanceAgent>();
		performanceAgentList = agent.getPerformance();

		List<StagingServiceDesk> serviceDeskIncidentList = new ArrayList<StagingServiceDesk>();
		serviceDeskIncidentList = sdReport.getAllIncidentByMonth(previousMonth);

		report.setTotalTicket(totalTicket);
		report.setTotalAchieved(totalAchieved);
		report.setTotalMissed(totalMissed);
		report.setAchievement(achievement);
		report.setSummaryReport(summaryList);
		report.setPerformanceAgentList(performanceAgentList);
		report.setServiceDeskIncidentList(serviceDeskIncidentList);
		
		for(PerformanceAgent a: performanceAgentList) {
			logger.debug("in "+a.getAgentName()+":"+a.getTotalTicket());
		}

		List<PerformanceReport> list = new ArrayList<PerformanceReport>();
		list.add(report);
		
//		List<PerformanceReport> listPerfAgent = new ArrayList<PerformanceReport>();
//		PerformanceReport incidentAgentPerf = new PerformanceReport();
//		incidentAgentPerf.setPerformanceAgentList(performanceAgentList);
//		listPerfAgent.add(incidentAgentPerf);

		this.reportList = list;
//		this.perfAgentList = listPerfAgent;
	}

	private void setURPerformanceReport() {

		PerformanceReport report = new PerformanceReport();

		
		summaryList = new ArrayList<SummaryReport>();
		PerformanceOverall perfAll = allUR.getPerformance(0, currentMonth);

		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();
		summaryList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryList.add(new SummaryReport("Achievement", achievement.toString()));

		List<PerformanceAgent> performanceAgentList = new ArrayList<PerformanceAgent>();
		performanceAgentList = agentUR.getPerformance(0, currentMonth);
		
		List<StagingUserRequest> userRequestIncidentList = new ArrayList<StagingUserRequest>();
		userRequestIncidentList = urReport.getAllIncidentByMonth(previousMonth);

		report.setTotalTicket(totalTicket);
		report.setTotalAchieved(totalAchieved);
		report.setTotalMissed(totalMissed);
		report.setAchievement(achievement);
		report.setSummaryReport(summaryList);
		report.setPerformanceAgentList(performanceAgentList);
		report.setUserRequestIncidentList(userRequestIncidentList);

		List<PerformanceReport> list = new ArrayList<PerformanceReport>();
		list.add(report);

		logger.debug("userrequest");
		for(PerformanceAgent a: performanceAgentList) {
			logger.debug("ur "+a.getAgentName()+":"+a.getTotalTicket());
		}
		
		this.urReportList = list;

	}

	@Override
	public List<PerformanceReport> getPerformanceReport() {

//		return reportList;
		return getCombinedReport();
	}

	private List<PerformanceReport> getServiceDeskPersons() {

		List<PerformanceAgent> performanceReport = new ArrayList<PerformanceAgent>(); 
		performanceReport = reportList.get(0).getPerformanceAgentList();

		List<PerformanceReport> personReport = new ArrayList<PerformanceReport>();

		int tempTotal = 0;
		int tempMissed = 0;
		int tempAchieved = 0;
		for (String person : personArray) {
			for (PerformanceAgent agent : performanceReport) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {
					PerformanceReport pr = new PerformanceReport();

					List<PerformanceAgent> temp = new ArrayList<PerformanceAgent>();
					temp.add(agent);

					tempTotal = tempTotal + agent.getTotalTicket();
					tempMissed = tempMissed + agent.getTotalMissed();
					tempAchieved = tempAchieved + agent.getTotalAchieved();

					pr.setPerformanceAgentList(temp);

					personReport.add(pr);

					break;
				}
			}
		}

		// get dcu ticket count
		int otherDCUTotalTicket = reportList.get(0).getTotalTicket() - tempTotal;
		int otherDCUTotalAchieved = reportList.get(0).getTotalAchieved() - tempAchieved;
		int otherDCUTotalMissed = reportList.get(0).getTotalMissed() - tempMissed;

		BigDecimal achievement = calculateAchievement(otherDCUTotalAchieved, otherDCUTotalTicket);

		PerformanceAgent otherOrDCU = new PerformanceAgent();
		otherOrDCU.setAgentName(otherDcuName);
		otherOrDCU.setTotalTicket(otherDCUTotalTicket);
		otherOrDCU.setTotalAchieved(otherDCUTotalAchieved);
		otherOrDCU.setTotalMissed(otherDCUTotalMissed);
		otherOrDCU.setAchievement(achievement.floatValue());

		List<PerformanceAgent> temp = new ArrayList<PerformanceAgent>();
		temp.add(otherOrDCU);

		PerformanceReport pr = new PerformanceReport();
		pr.setPerformanceAgentList(temp);

		personReport.add(pr);

		return personReport;
	}

	private BigDecimal calculateAchievement(int achieve, int total) {

		BigDecimal achievement = new BigDecimal(0);
		achievement = (new BigDecimal(achieve)).setScale(2, BigDecimal.ROUND_HALF_UP).
				divide((new BigDecimal(total)).setScale(2, BigDecimal.ROUND_HALF_UP), 4, BigDecimal.ROUND_HALF_EVEN);
		achievement = achievement.multiply(new BigDecimal(100));

		return achievement;
	}

	// incident + user request
	private List<SummaryReport> getTotalSummaryReportList() {
		List<PerformanceReport> incidentSummary = new ArrayList<PerformanceReport>();
		incidentSummary = this.reportList;
		
		List<PerformanceReport> userrequestSummary = new ArrayList<PerformanceReport>();
		userrequestSummary = this.urReportList;

		Integer totalTicket = incidentSummary.get(0).getTotalTicket() + userrequestSummary.get(0).getTotalTicket();
		Integer totalAchieved = incidentSummary.get(0).getTotalAchieved()
				+ userrequestSummary.get(0).getTotalAchieved();
		Integer totalMissed = incidentSummary.get(0).getTotalMissed() + userrequestSummary.get(0).getTotalMissed();

		BigDecimal achievement = new BigDecimal(0);
		achievement = calculateAchievement(totalAchieved, totalTicket);
		Float achievementFloat = achievement.floatValue();
		
		List<SummaryReport> totalSummary = new ArrayList<SummaryReport>();
		totalSummary.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		totalSummary.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		totalSummary.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		totalSummary.add(new SummaryReport("Achievement", achievementFloat.toString()+"%"));

		return totalSummary;
	}
	
	public List<PerformanceReport> getCombinedReport(){
		
		List<PerformanceReport> combinedList = new ArrayList<PerformanceReport>();
		
		PerformanceReport combined = new PerformanceReport();
		combined.setSummaryReport(getTotalSummaryReportList());
		combined.setPerformanceAgentList(reportList.get(0).getPerformanceAgentList());		
		combined.setServiceDeskIncidentList(reportList.get(0).getServiceDeskIncidentList());
		
		combined.setPerformanceURAgentList(urReportList.get(0).getPerformanceAgentList());
		combined.setUserRequestIncidentList(urReportList.get(0).getUserRequestIncidentList());
		
		combinedList.add(combined);
		
		return combinedList;
	}
	
	private List<PerformanceReport> getCombinedPersons() {

		String[] personArray = appConfig.getServicedeskPerson();
		String otherDcuName = appConfig.getServicedeskOtherTeam();
		
		List<PerformanceAgent> performanceReport = new ArrayList<PerformanceAgent>(); 
		performanceReport = reportList.get(0).getPerformanceAgentList();
		
		List<PerformanceAgent> performanceURReport = new ArrayList<PerformanceAgent>(); 
		performanceURReport = urReportList.get(0).getPerformanceAgentList();

		Map<String,PerformanceAgent> mapReport = new HashMap<String, PerformanceAgent>();

		int tempTotal = 0;
		int tempMissed = 0;
		int tempAchieved = 0;
		
		//loop through servicedesk 
		for (String person : personArray) {
			logger.debug("person: " +person);
			for (PerformanceAgent agent : performanceReport) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {

					tempTotal = tempTotal + agent.getTotalTicket();
					tempMissed = tempMissed + agent.getTotalMissed();
					tempAchieved = tempAchieved + agent.getTotalAchieved();
					
					mapReport.put(agent.getAgentName(), agent);

					break;
				}
			}
		}
		
		//loop through userrequest
		logger.debug("userrequest.....");
		
		List<PerformanceReport> perfSDAgent = new ArrayList<PerformanceReport>();
		
		for (String person : personArray) {
			logger.debug("personur: "+person);
			for (PerformanceAgent agent : performanceURReport) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {
					
					tempTotal = tempTotal + agent.getTotalTicket();
					tempMissed = tempMissed + agent.getTotalMissed();
					tempAchieved = tempAchieved + agent.getTotalAchieved();
					
					PerformanceAgent perfAgent = mapReport.get(agent.getAgentName());
					
					int totalTicket = perfAgent.getTotalTicket() + agent.getTotalTicket();
					int totalAchieved = perfAgent.getTotalAchieved() + agent.getTotalAchieved();
					int totalMissed = perfAgent.getTotalMissed() + agent.getTotalMissed();
					
					perfAgent.setTotalTicket(totalTicket);
					perfAgent.setTotalAchieved(totalAchieved);
					perfAgent.setTotalMissed(totalMissed);	
					perfAgent.setAchievement(calculateAchievement(totalAchieved, totalTicket).floatValue());
					
					List<PerformanceAgent> temp = new ArrayList<PerformanceAgent>();
					temp.add(perfAgent);
					
					PerformanceReport pr = new PerformanceReport();
					pr.setPerformanceAgentList(temp);					
					
					perfSDAgent.add(pr);
					
					break;
				}
			}
		}
		
		// get dcu ticket count
		int otherDCUTotalTicket = (reportList.get(0).getTotalTicket() + urReportList.get(0).getTotalTicket()) - tempTotal;
		int otherDCUTotalAchieved = (reportList.get(0).getTotalAchieved() + urReportList.get(0).getTotalAchieved()) - tempAchieved;
		int otherDCUTotalMissed = (reportList.get(0).getTotalMissed() + urReportList.get(0).getTotalMissed()) - tempMissed;

		BigDecimal achievement = calculateAchievement(otherDCUTotalAchieved, otherDCUTotalTicket);
		
		PerformanceAgent otherOrDCU = new PerformanceAgent();
		otherOrDCU.setAgentName(otherDcuName);
		otherOrDCU.setTotalTicket(otherDCUTotalTicket);
		otherOrDCU.setTotalAchieved(otherDCUTotalAchieved);
		otherOrDCU.setTotalMissed(otherDCUTotalMissed);
		otherOrDCU.setAchievement(achievement.floatValue());

		List<PerformanceAgent> temp = new ArrayList<PerformanceAgent>();
		temp.add(otherOrDCU);

		PerformanceReport prDCU = new PerformanceReport();
		prDCU.setPerformanceAgentList(temp);

		perfSDAgent.add(prDCU);

		return perfSDAgent;
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


