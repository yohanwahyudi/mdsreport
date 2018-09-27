package com.vdi.reports.djasper.service.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
import com.vdi.reports.djasper.model.SummaryReport;
import com.vdi.tools.TimeTools;

@Component("serviceDeskReportHelper")
public class ServiceDeskReportHelper {

	@Autowired
	@Qualifier("weeklySDPerfAllDAO")
	private PerfAllDAOService allWeeklyPerformance;
	@Autowired
	@Qualifier("weeklySDPerfAgentDAO")
	private PerfAgentDAOService agentWeeklyPerformance;
	@Autowired
	@Qualifier("weeklyURPerfAllDAO")
	private PerfAllDAOService allURWeeklyPerformance;
	@Autowired
	@Qualifier("weeklyURPerfAgentDAO")
	private PerfAgentDAOService agentURWeeklyPerformance;

	@Autowired
	@Qualifier("monthlySDPerfAllDAO")
	private PerfAllDAOService allMonthlyPerformance;
	@Autowired
	@Qualifier("monthlySDPerfAgentDAO")
	private PerfAgentDAOService agentMonthlyPerformance;
	@Autowired
	@Qualifier("monthlyURPerfAllDAO")
	private PerfAllDAOService allURMonthlyPerformance;
	@Autowired
	@Qualifier("monthlyURPerfAgentDAO")
	private PerfAgentDAOService agentURMonthlyPerformance;

	@Autowired
	@Qualifier("serviceDeskReportDAO")
	private ServiceDeskReportDAO sdReport;
	@Autowired
	@Qualifier("stagingUserRequestDAO")
	private StagingUserRequestDAOService urReport;
	
	@Autowired
	private TimeTools timeTools;

	@Autowired
	private AppConfig appConfig;

	private int currentMonth; 
	private int currentWeek; 
	private int prevMonth; 
	private int prevWeek; 

	private MasterReport weeklyReport;
	private MasterReport monthlyReport;

	// private List<MasterReport> sdMasterReportList;
	// private List<MasterReport> urMasterReportList;

	private final Logger logger = LogManager.getLogger(ServiceDeskReportHelper.class);

	public ServiceDeskReportHelper() {

	}

	private void setWeeklyReport() {
		
		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeek = timeTools.getCurrentWeekYear();
		this.prevMonth = currentMonth - 1;
		this.prevWeek = currentWeek - 1;
		
		weeklyReport = new MasterReport();
		weeklyReport.setOverallAchievementList(getOverallWeeklyAchievement());
		weeklyReport.setServiceDeskAchievementList(getServiceDeskWeeklyAchievement());
		weeklyReport.setPerformanceServiceDeskAgentList(getSdAgentWeeklyPerformance());
		weeklyReport.setServiceDeskIncidentList(getSdWeeklyReport().getServiceDeskIncidentList());
		weeklyReport.setUserRequestTicketList(getUrWeeklyReport().getUserRequestTicketList());
	}

	public MasterReport getWeeklyReport() {
		setWeeklyReport();

		return weeklyReport;
	}
	
private void setWeeklyReport(int month) {
		
		this.currentMonth = month;
		this.currentWeek = timeTools.getCurrentWeekYear();
		this.prevMonth = currentMonth - 1;
		this.prevWeek = currentWeek - 1;
		
		weeklyReport = new MasterReport();
		weeklyReport.setOverallAchievementList(getOverallWeeklyAchievement());
		weeklyReport.setServiceDeskAchievementList(getServiceDeskWeeklyAchievement());
		weeklyReport.setPerformanceServiceDeskAgentList(getSdAgentWeeklyPerformance());
		weeklyReport.setServiceDeskIncidentList(getSdWeeklyReport().getServiceDeskIncidentList());
		weeklyReport.setUserRequestTicketList(getUrWeeklyReport().getUserRequestTicketList());
	}

	public MasterReport getWeeklyReport(int month) {
		setWeeklyReport(month);

		return weeklyReport;
	}

	private void setMonthlyReport() {

		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeek = timeTools.getCurrentWeekYear();
		this.prevMonth = currentMonth - 1;
		this.prevWeek = currentWeek - 1;
		
		monthlyReport = new MasterReport();
		monthlyReport.setOverallAchievementList(getOverallMonthlyAchievement());
		monthlyReport.setServiceDeskAchievementList(getServiceDeskMonthlyAchievement());
		monthlyReport.setPerformanceServiceDeskAgentList(getSdAgentMonthlyPerformance());
		monthlyReport.setServiceDeskIncidentList(getSdMonthlyReport().getServiceDeskIncidentList());
		monthlyReport.setUserRequestTicketList(getUrMonthlyReport().getUserRequestTicketList());

	}

	public MasterReport getMonthlyReport() {
		setMonthlyReport();

		return monthlyReport;
	}

	private List<SummaryReport> getOverallWeeklyAchievement() {
		MasterReport sdWeekly = getSdWeeklyReport();
		MasterReport urWeekly = getUrWeeklyReport();

		Map<Object, Object> sdSummaryWeekly = sdWeekly.getMapValue();
		Integer sdTotalTicket = (Integer) sdSummaryWeekly.get("totalTicket");
		Integer sdAchievedTicket = (Integer) sdSummaryWeekly.get("totalAchieved");
		Integer sdMissedTicket = (Integer) sdSummaryWeekly.get("totalMissed");

		Map<Object, Object> urSummaryWeekly = urWeekly.getMapValue();
		Integer urTotalTicket = (Integer) urSummaryWeekly.get("totalTicket");
		Integer urAchievedTicket = (Integer) urSummaryWeekly.get("totalAchieved");
		Integer urMissedTicket = (Integer) urSummaryWeekly.get("totalMissed");

		List<SummaryReport> overallAchievementList = new ArrayList<SummaryReport>();
		Integer totalTicket = sdTotalTicket + urTotalTicket;
		Integer totalAchieved = sdAchievedTicket + urAchievedTicket;
		Integer totalMissed = sdMissedTicket + urMissedTicket;
		Float achievement = calculateAchievement(totalAchieved, totalTicket).floatValue();
		overallAchievementList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		overallAchievementList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		overallAchievementList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		overallAchievementList.add(new SummaryReport("Achievement", achievement.toString() + "%"));

		return overallAchievementList;
	}

	private List<SummaryReport> getServiceDeskWeeklyAchievement() {

		MasterReport sdWeekly = getSdWeeklyReport();
		MasterReport urWeekly = getUrWeeklyReport();

		Map<Object, Object> sdSummaryWeekly = sdWeekly.getMapValue();
		Integer sdTotalTicket = (Integer) sdSummaryWeekly.get("totalTicket");
		Integer sdAchievedTicket = (Integer) sdSummaryWeekly.get("totalAchieved");
		Integer sdMissedTicket = (Integer) sdSummaryWeekly.get("totalMissed");
		Float sdAchievement = (Float) sdSummaryWeekly.get("achievement");

		Map<Object, Object> urSummaryWeekly = urWeekly.getMapValue();
		Integer urTotalTicket = (Integer) urSummaryWeekly.get("totalTicket");
		Integer urAchievedTicket = (Integer) urSummaryWeekly.get("totalAchieved");
		Integer urMissedTicket = (Integer) urSummaryWeekly.get("totalMissed");
		Float urAchievement = (Float) urSummaryWeekly.get("achievement");

		List<SummaryReport> serviceDeskAchievementList = new ArrayList<SummaryReport>();
		Integer totalTicket = sdTotalTicket + urTotalTicket;
		Integer totalAchieved = sdAchievedTicket + urAchievedTicket;
		Integer totalMissed = sdMissedTicket + urMissedTicket;
		Float achievement = calculateAchievement(totalAchieved, totalTicket).floatValue();
		serviceDeskAchievementList.add(new SummaryReport("Ticket Total", sdTotalTicket.toString(),
				urTotalTicket.toString(), totalTicket.toString()));
		serviceDeskAchievementList.add(new SummaryReport("Ticket Achieved", sdAchievedTicket.toString(),
				urAchievedTicket.toString(), totalAchieved.toString()));
		serviceDeskAchievementList.add(new SummaryReport("Ticket Missed", sdMissedTicket.toString(),
				urMissedTicket.toString(), totalMissed.toString()));
		serviceDeskAchievementList.add(new SummaryReport("Achievement", sdAchievement.toString() + "%",
				urAchievement.toString() + "%", achievement.toString() + "%"));

		return serviceDeskAchievementList;
	}

	private List<PerformanceAgent> getSdAgentWeeklyPerformance() {

		String[] personArray = appConfig.getServicedeskPerson();
		String otherDcuName = appConfig.getServicedeskOtherTeam();

		List<PerformanceAgent> sdAgent = getSdWeeklyReport().getPerformanceServiceDeskAgentList();
		List<PerformanceAgent> urAgent = getUrWeeklyReport().getPerformanceUserRequestAgentList();

		// loop through servicedesk
		logger.info("Loop through SD");
		Map<String, PerformanceAgent> mapSdPersons = new HashMap<String, PerformanceAgent>();
		for (String person : personArray) {
			for (PerformanceAgent agent : sdAgent) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {
					mapSdPersons.put(agent.getAgentName(), agent);
				}
			}
		}
		Iterator<Map.Entry<String, PerformanceAgent>> sdIterator = mapSdPersons.entrySet().iterator();
		int totalSdPersonTicket = 0;
		int achievedSdPersonTicket = 0;
		int missedSdPersonTicket = 0;
		while (sdIterator.hasNext()) {
			Map.Entry<String, PerformanceAgent> entry = sdIterator.next();
			totalSdPersonTicket = totalSdPersonTicket + entry.getValue().getTotalTicket();
			achievedSdPersonTicket = achievedSdPersonTicket + entry.getValue().getTotalAchieved();
			missedSdPersonTicket = missedSdPersonTicket + entry.getValue().getTotalMissed();
		}

		// loop through userrequest
		logger.info("Loop through user request");
		Map<String, PerformanceAgent> mapUrPersons = new HashMap<String, PerformanceAgent>();
		for (String person : personArray) {
			for (PerformanceAgent agent : urAgent) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {
					mapUrPersons.put(agent.getAgentName(), agent);
				}
			}
		}
		Iterator<Map.Entry<String, PerformanceAgent>> urIterator = mapUrPersons.entrySet().iterator();
		int totalUrPersonTicket = 0;
		int achievedUrPersonTicket = 0;
		int missedUrPersonTicket = 0;
		while (urIterator.hasNext()) {
			Map.Entry<String, PerformanceAgent> entry = urIterator.next();
			totalUrPersonTicket = totalUrPersonTicket + entry.getValue().getTotalTicket();
			achievedUrPersonTicket = achievedUrPersonTicket + entry.getValue().getTotalAchieved();
			missedUrPersonTicket = missedUrPersonTicket + entry.getValue().getTotalMissed();
		}

		// calculate other team
		MasterReport sd = getSdWeeklyReport();
		MasterReport ur = getUrWeeklyReport();

		Map<Object, Object> sdSummary = sd.getMapValue();
		Integer sdTotalTicket = (Integer) sdSummary.get("totalTicket");
		Integer sdAchievedTicket = (Integer) sdSummary.get("totalAchieved");
		Integer sdMissedTicket = (Integer) sdSummary.get("totalMissed");

		Map<Object, Object> urSummary = ur.getMapValue();
		Integer urTotalTicket = (Integer) urSummary.get("totalTicket");
		Integer urAchievedTicket = (Integer) urSummary.get("totalAchieved");
		Integer urMissedTicket = (Integer) urSummary.get("totalMissed");

		Integer totalTicket = sdTotalTicket + urTotalTicket;
		Integer totalAchieved = sdAchievedTicket + urAchievedTicket;
		Integer totalMissed = sdMissedTicket + urMissedTicket;

		int totalDCUTicket = totalTicket - (totalSdPersonTicket + totalUrPersonTicket);
		int achievedDCUTicket = totalAchieved - (achievedSdPersonTicket + achievedUrPersonTicket);
		int missedDCUTicket = totalMissed - (missedSdPersonTicket + missedUrPersonTicket);
		float achievementDCU = calculateAchievement(achievedDCUTicket, totalDCUTicket).floatValue();

		// populate data
		List<PerformanceAgent> sdAgentPerformance = new ArrayList<PerformanceAgent>();
		for (String person : personArray) {

			if (mapSdPersons.get(person) != null) {

				int achieved = mapSdPersons.get(person).getTotalAchieved()
						+ mapUrPersons.get(person).getTotalAchieved();
				int missed = mapSdPersons.get(person).getTotalMissed() + mapUrPersons.get(person).getTotalMissed();
				int total = mapSdPersons.get(person).getTotalTicket() + mapUrPersons.get(person).getTotalTicket();
				float achievement = calculateAchievement(achieved, total).floatValue();

				sdAgentPerformance.add(new PerformanceAgent(person, achieved, missed, total, achievement));
			}
		}
		sdAgentPerformance.add(
				new PerformanceAgent(otherDcuName, achievedDCUTicket, missedDCUTicket, totalDCUTicket, achievementDCU));

		return sdAgentPerformance;

	}

	private List<SummaryReport> getOverallMonthlyAchievement() {
		MasterReport sdMonthly = getSdMonthlyReport();
		MasterReport urMonthly = getUrMonthlyReport();

		Map<Object, Object> sdSummary = sdMonthly.getMapValue();
		Integer sdTotalTicket = (Integer) sdSummary.get("totalTicket");
		Integer sdAchievedTicket = (Integer) sdSummary.get("totalAchieved");
		Integer sdMissedTicket = (Integer) sdSummary.get("totalMissed");

		Map<Object, Object> urSummary = urMonthly.getMapValue();
		Integer urTotalTicket = (Integer) urSummary.get("totalTicket");
		Integer urAchievedTicket = (Integer) urSummary.get("totalAchieved");
		Integer urMissedTicket = (Integer) urSummary.get("totalMissed");

		logger.debug("mapsd : " + sdSummary);
		logger.debug("mapur : " + urSummary);

		List<SummaryReport> overallAchievementList = new ArrayList<SummaryReport>();
		Integer totalTicket = sdTotalTicket + urTotalTicket;
		Integer totalAchieved = sdAchievedTicket + urAchievedTicket;
		Integer totalMissed = sdMissedTicket + urMissedTicket;
		Float achievement = calculateAchievement(totalAchieved, totalTicket).floatValue();
		overallAchievementList.add(new SummaryReport("Ticket Total", totalTicket.toString()));
		overallAchievementList.add(new SummaryReport("Ticket Achieved", totalAchieved.toString()));
		overallAchievementList.add(new SummaryReport("Ticket Missed", totalMissed.toString()));
		overallAchievementList.add(new SummaryReport("Achievement", achievement.toString() + "%"));

		return overallAchievementList;
	}

	private List<SummaryReport> getServiceDeskMonthlyAchievement() {

		MasterReport sdWeekly = getSdMonthlyReport();
		MasterReport urWeekly = getUrMonthlyReport();

		Map<Object, Object> sdSummary = sdWeekly.getMapValue();
		Integer sdTotalTicket = (Integer) sdSummary.get("totalTicket");
		Integer sdAchievedTicket = (Integer) sdSummary.get("totalAchieved");
		Integer sdMissedTicket = (Integer) sdSummary.get("totalMissed");
		Float sdAchievement = (Float) sdSummary.get("achievement");

		Map<Object, Object> urSummary = urWeekly.getMapValue();
		Integer urTotalTicket = (Integer) urSummary.get("totalTicket");
		Integer urAchievedTicket = (Integer) urSummary.get("totalAchieved");
		Integer urMissedTicket = (Integer) urSummary.get("totalMissed");
		Float urAchievement = (Float) urSummary.get("achievement");

		List<SummaryReport> serviceDeskAchievementList = new ArrayList<SummaryReport>();
		Integer totalTicket = sdTotalTicket + urTotalTicket;
		Integer totalAchieved = sdAchievedTicket + urAchievedTicket;
		Integer totalMissed = sdMissedTicket + urMissedTicket;
		Float achievement = calculateAchievement(totalAchieved, totalTicket).floatValue();
		serviceDeskAchievementList.add(new SummaryReport("Ticket Total", sdTotalTicket.toString(),
				urTotalTicket.toString(), totalTicket.toString()));
		serviceDeskAchievementList.add(new SummaryReport("Ticket Achieved", sdAchievedTicket.toString(),
				urAchievedTicket.toString(), totalAchieved.toString()));
		serviceDeskAchievementList.add(new SummaryReport("Ticket Missed", sdMissedTicket.toString(),
				urMissedTicket.toString(), totalMissed.toString()));
		serviceDeskAchievementList.add(new SummaryReport("Achievement", sdAchievement.toString() + "%",
				urAchievement.toString() + "%", achievement.toString() + "%"));

		return serviceDeskAchievementList;
	}

	private List<PerformanceAgent> getSdAgentMonthlyPerformance() {

		String[] personArray = appConfig.getServicedeskPerson();
		String otherDcuName = appConfig.getServicedeskOtherTeam();

		List<PerformanceAgent> sdAgent = getSdMonthlyReport().getPerformanceServiceDeskAgentList();
		List<PerformanceAgent> urAgent = getUrMonthlyReport().getPerformanceUserRequestAgentList();

		for (PerformanceAgent a : sdAgent) {
			logger.debug("sdlist agent debug " + a.getAgentName());
		}

		// loop through servicedesk
		logger.debug("Loop through SD");
		Map<String, PerformanceAgent> mapSdPersons = new HashMap<String, PerformanceAgent>();
		for (String person : personArray) {
			logger.debug("person sd: " + person);
			for (PerformanceAgent agent : sdAgent) {
				logger.debug("personsd " + agent.getAgentName());
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {
					mapSdPersons.put(agent.getAgentName(), agent);
				}
				// break;
			}
		}
		Iterator<Map.Entry<String, PerformanceAgent>> sdIterator = mapSdPersons.entrySet().iterator();
		int totalSdPersonTicket = 0;
		int achievedSdPersonTicket = 0;
		int missedSdPersonTicket = 0;
		while (sdIterator.hasNext()) {
			Map.Entry<String, PerformanceAgent> entry = sdIterator.next();
			totalSdPersonTicket = totalSdPersonTicket + entry.getValue().getTotalTicket();
			achievedSdPersonTicket = achievedSdPersonTicket + entry.getValue().getTotalAchieved();
			missedSdPersonTicket = missedSdPersonTicket + entry.getValue().getTotalMissed();
		}
		logger.debug("mapsdpersons: " + mapSdPersons);

		// loop through userrequest
		logger.debug("Loop through user request");
		Map<String, PerformanceAgent> mapUrPersons = new HashMap<String, PerformanceAgent>();
		for (String person : personArray) {
			logger.debug("person ur: " + person);
			for (PerformanceAgent agent : urAgent) {
				if (person.trim().equalsIgnoreCase(agent.getAgentName().trim())) {
					mapUrPersons.put(agent.getAgentName(), agent);
				}
				// break;
			}
		}
		Iterator<Map.Entry<String, PerformanceAgent>> urIterator = mapUrPersons.entrySet().iterator();
		int totalUrPersonTicket = 0;
		int achievedUrPersonTicket = 0;
		int missedUrPersonTicket = 0;
		while (urIterator.hasNext()) {
			Map.Entry<String, PerformanceAgent> entry = urIterator.next();
			totalUrPersonTicket = totalUrPersonTicket + entry.getValue().getTotalTicket();
			achievedUrPersonTicket = achievedUrPersonTicket + entry.getValue().getTotalAchieved();
			missedUrPersonTicket = missedUrPersonTicket + entry.getValue().getTotalMissed();
		}
		logger.debug("mapurpersons: " + mapUrPersons);

		// calculate other team
		MasterReport sd = getSdMonthlyReport();
		MasterReport ur = getUrMonthlyReport();

		Map<Object, Object> sdSummary = sd.getMapValue();
		Integer sdTotalTicket = (Integer) sdSummary.get("totalTicket");
		Integer sdAchievedTicket = (Integer) sdSummary.get("totalAchieved");
		Integer sdMissedTicket = (Integer) sdSummary.get("totalMissed");

		Map<Object, Object> urSummary = ur.getMapValue();
		Integer urTotalTicket = (Integer) urSummary.get("totalTicket");
		Integer urAchievedTicket = (Integer) urSummary.get("totalAchieved");
		Integer urMissedTicket = (Integer) urSummary.get("totalMissed");

		Integer totalTicket = sdTotalTicket + urTotalTicket;
		Integer totalAchieved = sdAchievedTicket + urAchievedTicket;
		Integer totalMissed = sdMissedTicket + urMissedTicket;

		int totalDCUTicket = totalTicket - (totalSdPersonTicket + totalUrPersonTicket);
		int achievedDCUTicket = totalAchieved - (achievedSdPersonTicket + achievedUrPersonTicket);
		int missedDCUTicket = totalMissed - (missedSdPersonTicket + missedUrPersonTicket);
		float achievementDCU = calculateAchievement(achievedDCUTicket, totalDCUTicket).floatValue();

		// populate data
		List<PerformanceAgent> sdAgentPerformance = new ArrayList<PerformanceAgent>();
		for (String person : personArray) {

			if (mapSdPersons.get(person) != null) {

				int achieved = mapSdPersons.get(person).getTotalAchieved()
						+ mapUrPersons.get(person).getTotalAchieved();
				int missed = mapSdPersons.get(person).getTotalMissed() + mapUrPersons.get(person).getTotalMissed();
				int total = mapSdPersons.get(person).getTotalTicket() + mapUrPersons.get(person).getTotalTicket();
				float achievement = calculateAchievement(achieved, total).floatValue();

				sdAgentPerformance.add(new PerformanceAgent(person, achieved, missed, total, achievement));
			}
		}
		sdAgentPerformance.add(
				new PerformanceAgent(otherDcuName, achievedDCUTicket, missedDCUTicket, totalDCUTicket, achievementDCU));

		return sdAgentPerformance;

	}

	private MasterReport getSdWeeklyReport() {
		logger.info("getsdweeklyreport..");
		logger.info("currentWeek:"+currentWeek+" currentMonth:"+currentMonth);
		
		PerformanceOverall perfAll = allWeeklyPerformance.getPerformance(currentWeek, currentMonth);
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("totalTicket", totalTicket);
		map.put("totalAchieved", totalAchieved);
		map.put("totalMissed", totalMissed);
		map.put("achievement", achievement);

		List<PerformanceAgent> performanceAgentSDList = agentWeeklyPerformance.getPerformance();
		List<StagingServiceDesk> serviceDeskIncidentList = sdReport.getAllIncidentByWeek(currentMonth, prevWeek);

		MasterReport reportSD = new MasterReport();
		reportSD.setPerformanceServiceDeskAgentList(performanceAgentSDList);
		reportSD.setServiceDeskIncidentList(serviceDeskIncidentList);
		reportSD.setMapValue(map);

		return reportSD;

	}

	private MasterReport getUrWeeklyReport() {

		// get data from userrequest
		PerformanceOverall perfURAll = allURWeeklyPerformance.getPerformance(currentWeek, currentMonth);
		Integer totalTicketUR = perfURAll.getTotalTicket();
		Integer totalAchievedUR = perfURAll.getTotalAchieved();
		Integer totalMissedUR = perfURAll.getTotalMissed();
		Float achievementUR = perfURAll.getAchievement();

		logger.debug("urWeekly total ticket: " + totalTicketUR);

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("totalTicket", totalTicketUR);
		map.put("totalAchieved", totalAchievedUR);
		map.put("totalMissed", totalMissedUR);
		map.put("achievement", achievementUR);

		List<PerformanceAgent> performanceAgentURList = new ArrayList<PerformanceAgent>();
		performanceAgentURList = agentURWeeklyPerformance.getPerformance(currentWeek, currentMonth);

		List<StagingUserRequest> userRequestIncidentList = new ArrayList<StagingUserRequest>();
		userRequestIncidentList = urReport.getAllIncidentByWeek(currentMonth, prevWeek);

		MasterReport reportUR = new MasterReport();
		reportUR.setPerformanceUserRequestAgentList(performanceAgentURList);
		reportUR.setUserRequestTicketList(userRequestIncidentList);
		reportUR.setMapValue(map);

		return reportUR;

	}

	private MasterReport getSdMonthlyReport() {

		PerformanceOverall perfAll = allMonthlyPerformance.getPerformance();
		Integer totalTicket = perfAll.getTotalTicket();
		Integer totalAchieved = perfAll.getTotalAchieved();
		Integer totalMissed = perfAll.getTotalMissed();
		Float achievement = perfAll.getAchievement();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("totalTicket", totalTicket);
		map.put("totalAchieved", totalAchieved);
		map.put("totalMissed", totalMissed);
		map.put("achievement", achievement);

		List<PerformanceAgent> performanceAgentSDList = agentMonthlyPerformance.getPerformance();
		List<StagingServiceDesk> serviceDeskIncidentList = sdReport.getAllIncidentByMonth(prevMonth);

		MasterReport reportSD = new MasterReport();
		reportSD.setPerformanceServiceDeskAgentList(performanceAgentSDList);
		reportSD.setServiceDeskIncidentList(serviceDeskIncidentList);
		reportSD.setMapValue(map);

		return reportSD;

	}

	private MasterReport getUrMonthlyReport() {

		// get data from userrequest
		PerformanceOverall perfURAll = allURMonthlyPerformance.getPerformance(0, currentMonth);
		Integer totalTicketUR = perfURAll.getTotalTicket();
		Integer totalAchievedUR = perfURAll.getTotalAchieved();
		Integer totalMissedUR = perfURAll.getTotalMissed();
		Float achievementUR = perfURAll.getAchievement();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("totalTicket", totalTicketUR);
		map.put("totalAchieved", totalAchievedUR);
		map.put("totalMissed", totalMissedUR);
		map.put("achievement", achievementUR);

		logger.debug("map ur: " + map);

		List<PerformanceAgent> performanceAgentURList = new ArrayList<PerformanceAgent>();
		performanceAgentURList = agentURMonthlyPerformance.getPerformance(0, currentMonth);

		List<StagingUserRequest> userRequestIncidentList = new ArrayList<StagingUserRequest>();
		userRequestIncidentList = urReport.getAllIncidentByMonth(prevMonth);

		MasterReport reportUR = new MasterReport();
		reportUR.setPerformanceUserRequestAgentList(performanceAgentURList);
		reportUR.setUserRequestTicketList(userRequestIncidentList);
		reportUR.setMapValue(map);

		return reportUR;

	}

	private BigDecimal calculateAchievement(int achieve, int total) {

		BigDecimal achievement = new BigDecimal(0);
		achievement = (new BigDecimal(achieve)).setScale(2, BigDecimal.ROUND_HALF_UP)
				.divide((new BigDecimal(total)).setScale(2, BigDecimal.ROUND_HALF_UP), 4, BigDecimal.ROUND_HALF_EVEN);
		achievement = achievement.multiply(new BigDecimal(100));

		return achievement;
	}

}
