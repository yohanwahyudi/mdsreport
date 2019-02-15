package com.vdi.batch.mds;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateIncident;
import com.vdi.batch.mds.helper.PopulateServiceDesk;
import com.vdi.batch.mds.helper.PopulateUserRequest;
import com.vdi.batch.mds.helper.weekly.PopulatePerformance;
import com.vdi.batch.mds.helper.weekly.PopulateSDPerformance;
import com.vdi.batch.mds.helper.weekly.PopulateURPerformance;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.PropertyNames;
import com.vdi.model.Incident;
import com.vdi.model.Period;
import com.vdi.model.performance.PerformanceTeam;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.SummaryReport;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.tools.TimeTools;
import com.zaxxer.hikari.HikariDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class BatchItopMDSWeeklyReport extends QuartzJobBean {

	private final String period = PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY;
	private final Logger logger = LogManager.getLogger(BatchItopMDSWeeklyReport.class);

	private AnnotationConfigApplicationContext ctx;

	private Integer currentYearInt;
	private Integer prevWeekMonth;
	private String currentMonthStr;
	private Integer currentWeekMonth;

	// private final String test = appConfig.getMdsReportPath();

	public BatchItopMDSWeeklyReport() {

	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Batch itop mds weekly report started.......");
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		TimeTools timeTools = ctx.getBean(TimeTools.class);
		this.currentWeekMonth = timeTools.getCurrentWeekMonth();
		this.prevWeekMonth = timeTools.getCurrentWeekMonth() - 1;
		this.currentMonthStr = timeTools.getCurrentMonthString();
		this.currentYearInt = timeTools.getCurrentYear();

		AppConfig appConfig = ctx.getBean(AppConfig.class);
		String path = appConfig.getMdsReportPath();

		if (currentWeekMonth == 1) {
			logger.info("First day of Week, weekly report is in the monthly batch");
		} else {
			String fileName = getFileName(prevWeekMonth, currentMonthStr);

			ReportService rpt = ctx.getBean("itopPerformanceReport", ReportService.class);

			populatePerformance(ctx);
			createReport(rpt, path, fileName);
			sendEmail(rpt, path, fileName);
		}
		
		HikariDataSource hds = ctx.getBean("dataSource", HikariDataSource.class);
		logger.info("close datasource");
		logger.info(hds.getPoolName()+"-"+hds.getJdbcUrl());
		try {
			hds.close();
		} catch (Exception e) {
			logger.info("Error closing datasource ");
			e.printStackTrace();
		}

		logger.info("Batch itop mds weekly report finished......");

	}

	private void populatePerformance(AnnotationConfigApplicationContext ctx) {
		// populate Data
//		logger.info("start populate incident, ur, sd");
//		PopulateIncident populateIncident = ctx.getBean(PopulateIncident.class);
//		populateIncident.populate();
//		PopulateServiceDesk populateServiceDesk = ctx.getBean(PopulateServiceDesk.class);
//		populateServiceDesk.populate();
//		PopulateUserRequest populateUserRequest = ctx.getBean(PopulateUserRequest.class);
//		populateUserRequest.populate();
//		logger.info("end populate incident, ur, sd");

		// populate performance
		logger.info("start calculate weekly performance");
		PopulatePerformance weekly = ctx.getBean("populatePerformanceWeekly", PopulatePerformance.class);
		weekly.populatePerformance();
		PopulateSDPerformance weeklySD = ctx.getBean("populateSDPerformanceWeekly", PopulateSDPerformance.class);
		weeklySD.populatePerformance();
		PopulateURPerformance weeklyUR = ctx.getBean("populateURPerformanceWeekly", PopulateURPerformance.class);
		weeklyUR.populatePerformance();
		logger.info("end calculate weekly performance");
	}

	private void createReport(ReportService rpt, String path, String fileName) {
		try {
			JasperPrint jp = rpt.getReport(period);
			ReportExporter.exportReport(jp, path + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendEmail(ReportService rpt, String path, String fileName) {
		// send mail
		try {

			Map<String, Object> mapObject = getMapObject(rpt.getPerformanceReport(period));

			File file = new File(path + fileName);
			FileSystemResource fileResource = new FileSystemResource(file);

			String subject = "Performance VDI For MDS Based on ITOP ";
			String subjectPeriod = "Week " + prevWeekMonth + " " + currentMonthStr + " " + currentYearInt;

			MailService mailService = ctx.getBean("mailService", MailService.class);
			mailService.sendEmail(mapObject, "fm_mailItopReportMDS.txt", fileResource, subject + subjectPeriod);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Map<String, Object> getMapObject(List<MasterReport> masterReport) {
		MasterReport report = masterReport.get(0);

		List<SummaryReport> overallList = report.getOverallAchievementList();
		List<SummaryReport> sdAchievementList = report.getServiceDeskAchievementList();
		List<PerformanceTeam> performanceTeamList = report.getPerformanceTeamList();
		List<Incident> missed = report.getSupportAgentMissedList();
		List<Incident> pending = report.getSupportAgentPendingList();
		List<Incident> assigned = report.getSupportAgentAssignList();

		Period periodObj = new Period();
		periodObj.setPrevWeekMonth(prevWeekMonth);
		periodObj.setCurrMonthStr(currentMonthStr);
		periodObj.setCurrYearStr(currentYearInt.toString());

		SummaryReport sla = overallList.get(3);

		Map<String, Object> mapObject = new HashMap<String, Object>();
		mapObject.put("period", periodObj);
		mapObject.put("sla", sla);
		mapObject.put("overall", overallList);
		mapObject.put("sdAchievement", sdAchievementList);
		mapObject.put("perfTeam", performanceTeamList);
		mapObject.put("missed", missed);
		mapObject.put("pending", pending);
		mapObject.put("assigned", assigned);

		return mapObject;
	}

	private String getFileName(int week, String month) {

		String fileName = "VDI_ITOP_Performance_Week" + week + "_" + month + ".pdf";

		return fileName;
	}

}
