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

@Configuration("weeklyIncidentSDReport")
public class WeeklyIncidentSDReportImpl implements ReportService {

	@Autowired
	@Qualifier("weeklySDPerfAllDAO")
	private PerfAllDAOService all;

	@Autowired
	@Qualifier("weeklySDPerfAgentDAO")
	private PerfAgentDAOService agent;

	@Autowired
	@Qualifier("weeklyURPerfAllDAO")
	private PerfAllDAOService allUR;

	@Autowired
	@Qualifier("weeklyURPerfAgentDAO")
	private PerfAgentDAOService agentUR;

	@Autowired
	@Qualifier("stagingUserRequestDAO")
	private StagingUserRequestDAOService urReport;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private TemplateBuildersReport templateBuilders;

	@Autowired
	@Qualifier("serviceDeskReportDAO")
	private ServiceDeskReportDAO sdReport;

	private int currentYear;
	private int currentMonth;
	private String currentMonthStr;
	private int currentWeek;
	private int previousWeek;
	private int currentWeekMonth;
	private int previousWeekMonth;

	private PerformanceReport report;

	protected final Map<String, Object> params = new HashMap<String, Object>();

	private List<PerformanceReport> reportSDList;
	private List<PerformanceReport> reportURList;
	private List<PerformanceReport> combinedReportList;
//	private List<SummaryReport> summaryList;
//	private List<PerformanceAgent> performanceAgentList;
//	private List<StagingServiceDesk> serviceDeskIncidentList;
	
	private final Logger logger = LogManager.getLogger(WeeklyIncidentSDReportImpl.class);

	public WeeklyIncidentSDReportImpl() {

		this.currentYear = TimeStatic.currentYear;
		this.currentMonth = TimeStatic.currentMonth;
		this.currentMonthStr = TimeStatic.currentMonthStr;
		this.currentWeek = TimeStatic.currentWeekYear;
		this.previousWeek = currentWeek - 1;
		this.currentWeekMonth = TimeStatic.currentWeekMonth;
		this.previousWeekMonth = currentWeekMonth - 1;

	}

	@Override
	public DynamicReport buildReport() {
		// get master report
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("VDI SERVICE DESK PERFORMANCE BASED ON iTop");
		master.setSubtitle("WEEK " + previousWeekMonth + " - " + currentMonthStr.toUpperCase() + " " + currentYear);

		// set params

		List<PerformanceReport> combinedReportList = getPerformanceReport();
		PerformanceReport combinedReport = new PerformanceReport();
		combinedReport = combinedReportList.get(0);

		params.put("summaryReport", combinedReport.getSummaryReport());
		params.put("servicedeskIncidentList", combinedReport.getServiceDeskIncidentList());
		params.put("userrequestIncidentList", combinedReport.getUserRequestIncidentList());
//		params.put("performanceAgentList", combinedReport.getPerformanceAgentList());
//		params.put("performanceURAgentList", combinedReport.getPerformanceURAgentList());

		// add subreports
		DynamicReport subReportAll = templateBuilders.getSummarySub2();
		DynamicReport subReportPerson = templateBuilders.getServiceDeskPersonSub();
//		DynamicReport subReportAgent = templateBuilders.getServiceDeskAgentSub();
		DynamicReport subReportIncident = templateBuilders.getServiceDeskIncidentSub();
		DynamicReport subReportURIncident = templateBuilders.getUserRequestIncidentSub();

		master.addConcatenatedReport(subReportAll, new ClassicLayoutManager(), "summaryReport",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);

		// get serviceDeskPersons
		List<PerformanceReport> serviceDeskPersonsList = new ArrayList<PerformanceReport>();
		serviceDeskPersonsList = combinedReport.getPerformanceActiveAgentList();
		for (int i = 0; i < serviceDeskPersonsList.size(); i++) {

			List<PerformanceAgent> perfAgent = new ArrayList<PerformanceAgent>();
			perfAgent = serviceDeskPersonsList.get(i).getPerformanceOnJobSDAgent();

			params.put("personSub" + i, perfAgent);
			master.addConcatenatedReport(subReportPerson, new ClassicLayoutManager(), "personSub" + i,
					DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		}
//
//		master.addConcatenatedReport(subReportAgent, new ClassicLayoutManager(), "performanceAgentList",
//				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
//		master.addConcatenatedReport(subReportAgent, new ClassicLayoutManager(), "performanceURAgentList",
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
		setCombinedReport();

		return new JRBeanCollectionDataSource(getPerformanceReport());
	}

	private void setPerformanceReport() {

		//get data from servicedesk
		PerformanceOverall perfAll = all.getPerformance(currentWeek, currentMonth);
		
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();
		
		List<SummaryReport> summarySDList = new ArrayList<SummaryReport>();
		summarySDList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summarySDList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summarySDList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summarySDList.add(new SummaryReport("Achievement", achievement.toString()));

		List<PerformanceAgent> performanceAgentSDList = agent.getPerformance();
		List<StagingServiceDesk> serviceDeskIncidentList = sdReport.getAllIncidentByWeek(currentMonth, previousWeek);

		PerformanceReport reportSD = new PerformanceReport();
		reportSD.setTotalTicket(totalTicket);
		reportSD.setTotalAchieved(totalAchieved);
		reportSD.setTotalMissed(totalMissed);
		reportSD.setAchievement(achievement);
		reportSD.setSummaryReport(summarySDList);
		reportSD.setPerformanceAgentList(performanceAgentSDList);
		reportSD.setServiceDeskIncidentList(serviceDeskIncidentList);

		List<PerformanceReport> listSD = new ArrayList<PerformanceReport>();
		listSD.add(reportSD);
		this.reportSDList = listSD;
		
		//get data from userrequest
		PerformanceOverall perfURAll = allUR.getPerformance(currentWeek, currentMonth);
		
		Integer totalTicketUR = perfURAll.getTotalTicket();
		Integer totalAchievedUR = perfURAll.getTotalAchieved();
		Integer totalMissedUR = perfURAll.getTotalMissed();
		Float achievementUR = perfURAll.getAchievement();
		
		List<SummaryReport> summaryURList = new ArrayList<SummaryReport>();
		summaryURList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		summaryURList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		summaryURList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		summaryURList.add(new SummaryReport("Achievement", achievement.toString()));
		
		List<PerformanceAgent> performanceAgentURList = new ArrayList<PerformanceAgent>();
		performanceAgentURList = agentUR.getPerformance(currentWeek, currentMonth);
		
		List<StagingUserRequest> userRequestIncidentList = new ArrayList<StagingUserRequest>();
		userRequestIncidentList = urReport.getAllIncidentByWeek(currentMonth, previousWeek);
		
		PerformanceReport reportUR = new PerformanceReport();
		reportUR.setTotalTicket(totalTicketUR);
		reportUR.setTotalAchieved(totalAchievedUR);
		reportUR.setTotalMissed(totalMissedUR);
		reportUR.setAchievement(achievementUR);
		reportUR.setSummaryReport(summaryURList);
		reportUR.setPerformanceAgentList(performanceAgentURList);
		reportUR.setUserRequestIncidentList(userRequestIncidentList);

		List<PerformanceReport> listUR = new ArrayList<PerformanceReport>();
		listUR.add(reportUR);
		this.reportURList = listUR;
	}

	@Override
	public List<PerformanceReport> getPerformanceReport() {

		// return reportSDList;
		return this.combinedReportList;
	}

	public List<PerformanceReport> getServiceDeskPersons() {

		List<PerformanceAgent> performanceReport = reportSDList.get(0).getPerformanceAgentList();
		String[] personArray = appConfig.getServicedeskPerson();
		String otherDCUName = appConfig.getServicedeskOtherTeam();

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
		int otherDCUTotalTicket = report.getTotalTicket() - tempTotal;
		int otherDCUTotalAchieved = report.getTotalAchieved() - tempAchieved;
		int otherDCUTotalMissed = report.getTotalMissed() - tempMissed;

		BigDecimal achievement = new BigDecimal(0);
		achievement = (new BigDecimal(otherDCUTotalAchieved)).divide((new BigDecimal(otherDCUTotalTicket)), 4,
				BigDecimal.ROUND_HALF_UP);
		achievement = achievement.multiply(new BigDecimal(100));

		PerformanceAgent otherOrDCU = new PerformanceAgent();
		otherOrDCU.setAgentName(otherDCUName);
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
		incidentSummary = this.reportSDList;

		List<PerformanceReport> userrequestSummary = new ArrayList<PerformanceReport>();
		userrequestSummary = this.reportURList;
		
		PerformanceReport serviceDeskPR = incidentSummary.get(0);
		PerformanceReport userRequestPR = userrequestSummary.get(0);

		Integer totalTicket = serviceDeskPR.getTotalTicket() + userRequestPR.getTotalTicket();
		Integer totalAchieved = serviceDeskPR.getTotalAchieved() + userRequestPR.getTotalAchieved();
		Integer totalMissed = serviceDeskPR.getTotalMissed() + userRequestPR.getTotalMissed();

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

	private void setCombinedReport() {

		List<PerformanceReport> combinedList = new ArrayList<PerformanceReport>();
		List<PerformanceReport> activeServiceDeskAndDCU = new ArrayList<PerformanceReport>();
		activeServiceDeskAndDCU = getActiveServiceDeskAndDCU();
		
		PerformanceReport serviceDeskPR = reportSDList.get(0);
		PerformanceReport serviceDeskUR = reportURList.get(0);

		PerformanceReport combined = new PerformanceReport();
		combined.setSummaryReport(getTotalSummaryReportList());
		combined.setPerformanceAgentList(serviceDeskPR.getPerformanceAgentList());
		combined.setServiceDeskIncidentList(serviceDeskPR.getServiceDeskIncidentList());

		combined.setPerformanceURAgentList(serviceDeskUR.getPerformanceAgentList());
		combined.setUserRequestIncidentList(serviceDeskUR.getUserRequestIncidentList());
		
		combined.setPerformanceActiveAgentList(activeServiceDeskAndDCU);

		combinedList.add(combined);

		this.combinedReportList = combinedList;
	}
	
	private List<PerformanceReport> getActiveServiceDeskAndDCU() {

		String[] personArray = appConfig.getServicedeskPerson();
		String otherDcuName = appConfig.getServicedeskOtherTeam();

		List<PerformanceAgent> performanceSDReport = new ArrayList<PerformanceAgent>();
		performanceSDReport = reportSDList.get(0).getPerformanceAgentList();

		List<PerformanceAgent> performanceURReport = new ArrayList<PerformanceAgent>();
		performanceURReport = reportURList.get(0).getPerformanceAgentList();

		Map<String, PerformanceAgent> mapReport = new HashMap<String, PerformanceAgent>();

		int tempTotal = 0;
		int tempMissed = 0;
		int tempAchieved = 0;

		// loop through servicedesk
		logger.debug("loop sd agent.....");
		for (String person : personArray) {
			for (PerformanceAgent agent : performanceSDReport) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {

					tempTotal = tempTotal + agent.getTotalTicket();
					tempMissed = tempMissed + agent.getTotalMissed();
					tempAchieved = tempAchieved + agent.getTotalAchieved();

					mapReport.put(agent.getAgentName(), agent);

					logger.debug("name "+agent.getAgentName());
					logger.debug("tempTotal "+tempTotal);
					
					break;
				}
			}
		}

		// loop through userrequest
		logger.debug("loop userrequest agent.....");
		List<PerformanceReport> perfSDAgent = new ArrayList<PerformanceReport>();	
		
		for (String person : personArray) {
			
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
					
					PerformanceReport activeAgent = new PerformanceReport();
					activeAgent.setPerformanceOnJobSDAgent(temp);		
					
					perfSDAgent.add(activeAgent);
					
					logger.debug("agentName "+perfAgent.getAgentName());
					logger.debug("perfsdticket "+perfAgent.getTotalTicket()+" perfurticket "+agent.getTotalTicket());
					
					break;
				}
			}
		}

		// get dcu ticket count
		PerformanceReport incidentReport = new PerformanceReport();
		incidentReport = reportSDList.get(0);
		
		PerformanceReport urReport = new PerformanceReport();
		urReport = reportURList.get(0);
		
		int otherDCUTotalTicket = (incidentReport.getTotalTicket() + urReport.getTotalTicket())
				- tempTotal;
		int otherDCUTotalAchieved = (incidentReport.getTotalAchieved() + urReport.getTotalAchieved())
				- tempAchieved;
		int otherDCUTotalMissed = (incidentReport.getTotalMissed() + urReport.getTotalMissed())
				- tempMissed;

		logger.debug("otherDCUTotalTicket "+otherDCUTotalTicket);
		logger.debug("tempTotal "+tempTotal);
		
		BigDecimal achievement = calculateAchievement(otherDCUTotalAchieved, otherDCUTotalTicket);
		
		PerformanceAgent otherOrDCU = new PerformanceAgent();
		otherOrDCU.setAgentName(otherDcuName);
		otherOrDCU.setTotalTicket(otherDCUTotalTicket);
		otherOrDCU.setTotalAchieved(otherDCUTotalAchieved);
		otherOrDCU.setTotalMissed(otherDCUTotalMissed);
		otherOrDCU.setAchievement(achievement.floatValue());
		
		List<PerformanceAgent> temp = new ArrayList<PerformanceAgent>();
		temp.add(otherOrDCU);
		
		PerformanceReport activeAgent = new PerformanceReport();
		activeAgent.setPerformanceOnJobSDAgent(temp);
		
		perfSDAgent.add(activeAgent);
		
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
