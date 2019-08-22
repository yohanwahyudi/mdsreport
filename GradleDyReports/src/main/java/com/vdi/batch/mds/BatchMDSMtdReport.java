package com.vdi.batch.mds;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

public class BatchMDSMtdReport extends QuartzJobBean {

	private final String period = PropertyNames.CONSTANT_REPORT_PERIOD_MTD;
	private final Logger logger = LogManager.getLogger(BatchMDSMtdReport.class);

	private AnnotationConfigApplicationContext ctx;

	private String currentMonthStr;
	private Integer currentYear;
	private Integer currentMonthInt;
	private Integer currentDate;

	public BatchMDSMtdReport() {

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		setDateTime();

	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Batch itop mds MTD report started.......");

		if (currentDate != 1) {

			// populate MTD performance
			try {
				populatePerformance();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// create report
			String fileName = getMtdFileName();
			String path = getMtdReportPath();
			String subject = getSubject();

			logger.info("creating report for period : " + period);
			ReportService rpt = ctx.getBean("itopPerformanceReport", ReportService.class);
			createReport(rpt, path, fileName, period);

			// send email
			sendEmail(rpt, path, fileName, period, subject);

			closeDataSource();

		} else {
			logger.info("skip MTD report for first day of month");
		}

		logger.info("Batch itop mds MTD report finished.......");

	}

	private void populatePerformance() throws InterruptedException {

		BatchMDSMtdPerformance batchMDSMtdPerformance = new BatchMDSMtdPerformance();
		batchMDSMtdPerformance.executeBatch();

	}

	private void createReport(ReportService rpt, String path, String fileName, String period) {

		try {

			logger.info("path: " + path);
			logger.info("fileName: " + fileName);

			ReportExporter.exportReport(rpt.getReport(period), path + fileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendEmail(ReportService rpt, String path, String fileName, String period, String subject) {
		try {

			Map<String, Object> mapObject = getMapObject(rpt.getPerformanceReport(period));

			File file = new File(path + fileName);
			FileSystemResource fileResource = new FileSystemResource(file);

			MailService mailService = ctx.getBean("mailService", MailService.class);
			mailService.sendEmail(mapObject, "fm_mailItopReportMDSMTD.txt", fileResource, subject);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDateTime() {

		TimeTools timeTools = new TimeTools();

		this.currentMonthInt = timeTools.getCurrentMonth();
		this.currentMonthStr = timeTools.getCurrentMonthString();
		this.currentYear = timeTools.getCurrentYear();
		this.currentDate = timeTools.getCurrentDate();

		logger.info("currentMonthInt: " + currentMonthInt);
		logger.info("currentMonthStr: " + currentMonthStr);
		logger.info("currentYear: " + currentYear);
		logger.info("currentDate: " + currentDate);
	}

	private void closeDataSource() {

		HikariDataSource hds = ctx.getBean("dataSource", HikariDataSource.class);
		logger.info("close datasource");
		logger.info(hds.getPoolName() + "-" + hds.getJdbcUrl());
		try {
			hds.close();
		} catch (Exception e) {
			logger.info("Error closing datasource ");
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
		periodObj.setCurrMonthStr(currentMonthStr);
		periodObj.setCurrYearStr(currentYear.toString());

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

	private String getSubject() {

		String subject = "MTD Performance VDI For MDS Based on ITOP ";
		String subjectPeriod = currentMonthStr + " " + currentYear;

		return subject + subjectPeriod;
	}

	private String getMtdFileName() {
		return "MTD_VDI_ITOP_Performance_" + currentMonthStr + ".pdf";
	}

	private String getMtdReportPath() {

		AppConfig appConfig = ctx.getBean(AppConfig.class);
		String path = appConfig.getMdsReportPath();

		return path;

	}

}
