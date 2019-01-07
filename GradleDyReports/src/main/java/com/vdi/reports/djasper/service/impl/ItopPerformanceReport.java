package com.vdi.reports.djasper.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vdi.configuration.PropertyNames;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.PerformanceReport;
import com.vdi.reports.djasper.model.SummaryReport;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.reports.djasper.service.helper.ServiceDeskReportHelper;
import com.vdi.reports.djasper.service.helper.SupportAgentReportHelper;
import com.vdi.reports.djasper.templates.TemplateBuildersReport;
import com.vdi.tools.TimeTools;

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

@Service("itopPerformanceReport")
public class ItopPerformanceReport implements ReportService {

	@Autowired
	@Qualifier("supportAgentReportHelper")
	private SupportAgentReportHelper saReportHelper;
	@Autowired
	@Qualifier("serviceDeskReportHelper")
	private ServiceDeskReportHelper sdReportHelper;

	@Autowired
	private TemplateBuildersReport templateBuilders;
	
	@Autowired
	private TimeTools timeTools;
	
	private int currentYear;
	private String currentMonthStr;
	private String prevMonthStr;
	private int prevWeekMonth;
	
	private int weekInWeeklyMonthly;

	private MasterReport weeklyReport;
	private MasterReport monthlyReport;
	
	private Boolean isWeeklyinMonthlyReport;

	protected final Map<String, Object> params = new HashMap<String, Object>();

	private final Logger logger = LogManager.getLogger(ItopPerformanceReport.class);

	public ItopPerformanceReport() {		
		logger.info("enter cons ItopPerformanceReport");
	}

	public DynamicReport buildReport(String period) {
		
		this.currentYear = timeTools.getCurrentYear();
		this.currentMonthStr = timeTools.getCurrentMonthString();
		this.prevMonthStr = timeTools.getPrevMonthString();
		this.prevWeekMonth = timeTools.getCurrentWeekMonth()-1;
		
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("VDI PERFORMANCE BASED ON iTop");
		
		MasterReport masterReport = new MasterReport();

		if (period.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY)) {
			if(isWeeklyinMonthlyReport) {				
				master.setSubtitle("WEEK " + weekInWeeklyMonthly + " - " + prevMonthStr.toUpperCase() + " " + currentYear);
				masterReport = weeklyReport;				
			} else {				
				master.setSubtitle("WEEK " + prevWeekMonth + " - " + currentMonthStr.toUpperCase() + " " + currentYear);
				masterReport = weeklyReport;				
			}
			
		} else {
			
			if(timeTools.getCurrentMonth() == 1) {
				logger.info("desember");
				master.setSubtitle(prevMonthStr.toUpperCase() + " " + (currentYear-1));
			} else {
				master.setSubtitle(prevMonthStr.toUpperCase() + " " + currentYear);
			}			
//			master.setSubtitle(prevMonthStr.toUpperCase() + " " + currentYear);
			masterReport = monthlyReport;
		} 

		params.put("overallAchievement", masterReport.getOverallAchievementList());
		params.put("sdAchievement", masterReport.getServiceDeskAchievementList());
		params.put("saTeamAchievement", masterReport.getPerformanceTeamList());
		params.put("sdAgentPerformance", masterReport.getPerformanceServiceDeskAgentList());
		params.put("saAgentPerformance", masterReport.getPerformanceSuportAgentList());
		params.put("saMissedList", masterReport.getSupportAgentMissedList());
		params.put("saPendingList", masterReport.getSupportAgentPendingList());
		params.put("saAssignedList", masterReport.getSupportAgentAssignList());
		params.put("sdIncidentList", masterReport.getServiceDeskIncidentList());
		params.put("urTicketList", masterReport.getUserRequestTicketList());
		params.put("saIncidentList", masterReport.getSupportAgentIncidentList());
		
//		logger.debug("listsd: "+masterReport.getServiceDeskIncidentList().size());
//		for(StagingServiceDesk sd:masterReport.getServiceDeskIncidentList()) {
//			logger.debug(sd.getIncident_slattrpassed());
//		}
		
		DynamicReport overallAchievementSub = templateBuilders.getSummaryOverallSub();
		DynamicReport sdAchievementSub = templateBuilders.getSummarySDSub();
		DynamicReport saTeamAchievementSub = templateBuilders.getTeamSub();
		DynamicReport sdAgentPerformanceSub = templateBuilders.getServiceDeskAgentSub();
		DynamicReport saAgentPerformanceSub = templateBuilders.getAgentSub();
		DynamicReport saMissedSub = templateBuilders.getMissedSub();
		DynamicReport saPendingSub = templateBuilders.getPendingSub();
		DynamicReport saAssignSub = templateBuilders.getAssignedSub();
		DynamicReport sdIncidentSub = templateBuilders.getServiceDeskIncidentSub();
		DynamicReport urIncidentSub = templateBuilders.getUserRequestIncidentSub();
		DynamicReport saIncidentSub = templateBuilders.getIncidentListSub();
		
		master.addConcatenatedReport(overallAchievementSub, new ClassicLayoutManager(), "overallAchievement",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(sdAchievementSub, new ClassicLayoutManager(), "sdAchievement",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(saTeamAchievementSub, new ClassicLayoutManager(), "saTeamAchievement",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(sdAgentPerformanceSub, new ClassicLayoutManager(), "sdAgentPerformance",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(saAgentPerformanceSub, new ClassicLayoutManager(), "saAgentPerformance",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(saMissedSub, new ClassicLayoutManager(), "saMissedList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(saPendingSub, new ClassicLayoutManager(), "saPendingList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(saAssignSub, new ClassicLayoutManager(), "saAssignedList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(sdIncidentSub, new ClassicLayoutManager(), "sdIncidentList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(urIncidentSub, new ClassicLayoutManager(), "urTicketList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(saIncidentSub, new ClassicLayoutManager(), "saIncidentList",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		
		return master.build();
	}

	@Override
	public JasperPrint getReport(String period) throws JRException, Exception {

		this.isWeeklyinMonthlyReport = Boolean.FALSE;
		
		JRDataSource ds = getDataSource(period);
		JasperReport jr = DynamicJasperHelper.generateJasperReport(buildReport(period), new ClassicLayoutManager(),
				params);
		JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);

		return jp;

	}
	
	@Override
	public JasperPrint getReport(String period, int month, int week) throws JRException, Exception {

		this.isWeeklyinMonthlyReport = Boolean.TRUE;
		this.weekInWeeklyMonthly = week;
		
		JRDataSource ds = getDataSource(period, month);
		JasperReport jr = DynamicJasperHelper.generateJasperReport(buildReport(period), new ClassicLayoutManager(),
				params);
		JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);

		return jp;

	}

	public JRDataSource getDataSource(String period) throws Exception {		
		return new JRBeanCollectionDataSource(getPerformanceReport(period));
	}
	
	public JRDataSource getDataSource(String period, int month) throws Exception {
		return new JRBeanCollectionDataSource(getPerformanceReport(period, month));
	}

	private void setWeeklyReport() {

		// block1 combine sd+sa overall performance
		MasterReport sdReport = new MasterReport();
		sdReport = sdReportHelper.getWeeklyReport();
		MasterReport saReport = new MasterReport();
		saReport = saReportHelper.getWeeklyReport();

		List<SummaryReport> sdOverallReport = sdReport.getOverallAchievementList();
		List<SummaryReport> saOverallReport = saReport.getOverallAchievementList();
		List<SummaryReport> combineOverallReport = new ArrayList<SummaryReport>();
		
		for (int i = 0; i < sdOverallReport.size(); i++) {
			String name = sdOverallReport.get(i).getName();
			String value1 = sdOverallReport.get(i).getValue();
			String value2 = saOverallReport.get(i).getValue();
			combineOverallReport.add(new SummaryReport(name, value1, value2));
		}
//		weeklyReport.setOverallAchievementList(combineOverallReport);

		//set value
		MasterReport weeklyReport = new MasterReport();
		weeklyReport.setOverallAchievementList(combineOverallReport);
		weeklyReport.setServiceDeskAchievementList(sdReport.getServiceDeskAchievementList());
		weeklyReport.setPerformanceTeamList(saReport.getPerformanceTeamList());
		weeklyReport.setPerformanceServiceDeskAgentList(sdReport.getPerformanceServiceDeskAgentList());
		weeklyReport.setPerformanceSuportAgentList(saReport.getPerformanceSuportAgentList());
		weeklyReport.setSupportAgentMissedList(saReport.getSupportAgentMissedList());
		weeklyReport.setSupportAgentPendingList(saReport.getSupportAgentPendingList());
		weeklyReport.setSupportAgentAssignList(saReport.getSupportAgentAssignList());
		weeklyReport.setServiceDeskIncidentList(sdReport.getServiceDeskIncidentList());
		weeklyReport.setUserRequestTicketList(sdReport.getUserRequestTicketList());
		weeklyReport.setSupportAgentIncidentList(saReport.getSupportAgentIncidentList());
		
		this.weeklyReport = weeklyReport;
	}
	
	private void setWeeklyReport(int month) {

		// block1 combine sd+sa overall performance
		MasterReport sdReport = new MasterReport();
		sdReport = sdReportHelper.getWeeklyReport(month);
		MasterReport saReport = new MasterReport();
		saReport = saReportHelper.getWeeklyReport(month);

		List<SummaryReport> sdOverallReport = sdReport.getOverallAchievementList();
		List<SummaryReport> saOverallReport = saReport.getOverallAchievementList();
		List<SummaryReport> combineOverallReport = new ArrayList<SummaryReport>();
		
		for (int i = 0; i < sdOverallReport.size(); i++) {
			String name = sdOverallReport.get(i).getName();
			String value1 = sdOverallReport.get(i).getValue();
			String value2 = saOverallReport.get(i).getValue();
			combineOverallReport.add(new SummaryReport(name, value1, value2));
		}
//		weeklyReport.setOverallAchievementList(combineOverallReport);

		//set value
		MasterReport weeklyReport = new MasterReport();
		weeklyReport.setOverallAchievementList(combineOverallReport);
		weeklyReport.setServiceDeskAchievementList(sdReport.getServiceDeskAchievementList());
		weeklyReport.setPerformanceTeamList(saReport.getPerformanceTeamList());
		weeklyReport.setPerformanceServiceDeskAgentList(sdReport.getPerformanceServiceDeskAgentList());
		weeklyReport.setPerformanceSuportAgentList(saReport.getPerformanceSuportAgentList());
		weeklyReport.setSupportAgentMissedList(saReport.getSupportAgentMissedList());
		weeklyReport.setSupportAgentPendingList(saReport.getSupportAgentPendingList());
		weeklyReport.setSupportAgentAssignList(saReport.getSupportAgentAssignList());
		weeklyReport.setServiceDeskIncidentList(sdReport.getServiceDeskIncidentList());
		weeklyReport.setUserRequestTicketList(sdReport.getUserRequestTicketList());
		weeklyReport.setSupportAgentIncidentList(saReport.getSupportAgentIncidentList());
		
		this.weeklyReport = weeklyReport;
	}

	private void setMonthlyReport() {

		// block1 combine sd+sa overall performance
		MasterReport sdReport = new MasterReport();
		sdReport = sdReportHelper.getMonthlyReport();
		MasterReport saReport = new MasterReport();
		saReport = saReportHelper.getMonthlyReport();

		List<SummaryReport> sdOverallReport = sdReport.getOverallAchievementList();
		List<SummaryReport> saOverallReport = saReport.getOverallAchievementList();
		List<SummaryReport> combineOverallReport = new ArrayList<SummaryReport>();
		
		logger.debug("saOverallReport List: "+saOverallReport);
		for(SummaryReport a:saOverallReport) {
			logger.debug(a.getName());
		}
		
		for (int i = 0; i < sdOverallReport.size(); i++) {
			String name = sdOverallReport.get(i).getName();
			String value1 = sdOverallReport.get(i).getValue();
			String value2 = saOverallReport.get(i).getValue();
			
//			logger.debug("name: "+name+" value1: "+value1+" value2: "+value2);
			
			combineOverallReport.add(new SummaryReport(name, value1, value2));
		}

//		logger.debug("combine: "+combineOverallReport);
		
		// set value
		MasterReport monthlyReport = new MasterReport();
		monthlyReport.setOverallAchievementList(combineOverallReport);
		monthlyReport.setServiceDeskAchievementList(sdReport.getServiceDeskAchievementList());
		monthlyReport.setPerformanceTeamList(saReport.getPerformanceTeamList());
		monthlyReport.setPerformanceServiceDeskAgentList(sdReport.getPerformanceServiceDeskAgentList());
		monthlyReport.setPerformanceSuportAgentList(saReport.getPerformanceSuportAgentList());
		monthlyReport.setSupportAgentMissedList(saReport.getSupportAgentMissedList());
		monthlyReport.setSupportAgentPendingList(saReport.getSupportAgentPendingList());
		monthlyReport.setSupportAgentAssignList(saReport.getSupportAgentAssignList());
		monthlyReport.setServiceDeskIncidentList(sdReport.getServiceDeskIncidentList());
		monthlyReport.setUserRequestTicketList(sdReport.getUserRequestTicketList());
		monthlyReport.setSupportAgentIncidentList(saReport.getSupportAgentIncidentList());
		
		this.monthlyReport = monthlyReport;
	}

	@Override
	public List<MasterReport> getPerformanceReport(String period) throws Exception {

		if (period.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY)) {

			setWeeklyReport();
			List<MasterReport> weeklyReportList = new ArrayList<MasterReport>();
			weeklyReportList.add(weeklyReport);
			return weeklyReportList;

		} else if (period.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_MONTHLY)) {

			setMonthlyReport();
			List<MasterReport> monthlyReportList = new ArrayList<MasterReport>();
			monthlyReportList.add(monthlyReport);

			return monthlyReportList;

		} else {
			throw new Exception("option not valid");
		}

	}
	
	public List<MasterReport> getPerformanceReport(String period, int month) throws Exception {

		if (period.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY)) {

			setWeeklyReport(month);
			List<MasterReport> weeklyReportList = new ArrayList<MasterReport>();
			weeklyReportList.add(weeklyReport);
			return weeklyReportList;

		} else if (period.equalsIgnoreCase(PropertyNames.CONSTANT_REPORT_PERIOD_MONTHLY)) {

			setMonthlyReport();
			List<MasterReport> monthlyReportList = new ArrayList<MasterReport>();
			monthlyReportList.add(monthlyReport);

			return monthlyReportList;

		} else {
			throw new Exception("option not valid");
		}

	}

	@Override
	public DynamicReport buildReport() {
		return null;

	}

	@Override
	public JRDataSource getDataSource() {
		return new JRBeanCollectionDataSource(getPerformanceReport());
	}

	@Override
	public JasperPrint getReport() throws JRException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PerformanceReport> getPerformanceReport() {

		return null;
	}

}
