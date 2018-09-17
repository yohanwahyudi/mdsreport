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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

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
import com.vdi.tools.TimeStatic;

import net.sf.jasperreports.engine.JRException;

public class BatchItopMDSMonthlyReport extends QuartzJobBean {

	private final String periodMonthly = PropertyNames.CONSTANT_REPORT_PERIOD_MONTHLY;
	private final String periodWeekly = PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY;
	
	private final Logger logger = LogManager.getLogger(BatchItopMDSMonthlyReport.class);

	private AnnotationConfigApplicationContext ctx;

	@Autowired
	private AppConfig appConfig;

	private Integer currentWeekYear = TimeStatic.currentWeekYear;
	private Integer currentWeekMonth = TimeStatic.currentWeekMonth;
	private Integer previousMonth = TimeStatic.currentMonth-1;
	private Integer currentYearInt = TimeStatic.currentYear;
	
	private final String currentDateStr = TimeStatic.currentDateStr;
	private final String currentMonthStr = TimeStatic.currentMonthStr;
	private final String prevMonthStr = TimeStatic.prevMonthStr;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Batch itop mds monthly report started.......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		String path = appConfig.getMdsReportPath();
		String fileNameMonthly = getFileNameMonthly();

		ReportService rpt = ctx.getBean("itopPerformanceReport", ReportService.class);

		//populate performance monthly
		populatePerformance(ctx);
		createReport(rpt, path, fileNameMonthly, periodMonthly);
		
		//populate performance weekly
		String filenameWeekly;
		Integer weeklyWeekMonth;
		Integer weeklyWeekYear;
		if(currentDateStr.equalsIgnoreCase(PropertyNames.DAY_STR_MONDAY)) {
			weeklyWeekYear = currentWeekYear-1;
			weeklyWeekMonth = currentWeekMonth-1;
			filenameWeekly = getFileNameWeekly(weeklyWeekMonth);
		} else {
			weeklyWeekYear = currentWeekYear;
			weeklyWeekMonth = currentWeekMonth;
			filenameWeekly = getFileNameWeekly(weeklyWeekMonth);
		}		
		populateWeeklyPerformance(ctx, weeklyWeekYear, previousMonth);
		createReport(rpt, path, filenameWeekly, periodWeekly);		
		
		//send email
		String[] fileNameArray= {fileNameMonthly, filenameWeekly};
		sendEmail(rpt, path, fileNameArray, periodMonthly);

		logger.info("Batch itop mds monthly report finished.......");
	}

	private void populatePerformance(AnnotationConfigApplicationContext ctx) {
		// populate performance
		PopulatePerformance monthlyInc = ctx.getBean("populatePerformanceMonthly", PopulatePerformance.class);
		monthlyInc.populatePerformance();
		PopulateSDPerformance monthlySD = ctx.getBean("populateSDPerformanceMonthly", PopulateSDPerformance.class);
		monthlySD.populatePerformance();
		PopulateURPerformance monthlyUR = ctx.getBean("populateURPerformanceMonthly", PopulateURPerformance.class);
		monthlyUR.populatePerformance();
	}

	private void populateWeeklyPerformance(AnnotationConfigApplicationContext ctx, int week, int month) {
		// populate weekly performance
		PopulatePerformance weekly = ctx.getBean("populatePerformanceWeekly", PopulatePerformance.class);
		weekly.populatePerformance(week, month);
		PopulateSDPerformance weeklySD = ctx.getBean("populateSDPerformanceWeekly", PopulateSDPerformance.class);
		weeklySD.populatePerformance(week, month);
		PopulateURPerformance weeklyUR = ctx.getBean("populateURPerformanceWeekly", PopulateURPerformance.class);
		weeklyUR.populatePerformance(week, month);
	}

	private void createReport(ReportService rpt, String path, String fileName, String period) {

		try {
			ReportExporter.exportReport(rpt.getReport(period), path + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendEmail(ReportService rpt, String path, String[] fileNameArray, String period) {
		try {

			Map<String, Object> mapObject = getMapObject(rpt.getPerformanceReport(period));
			
			List<FileSystemResource> fsrList = new ArrayList<FileSystemResource>();
			for(String fileName:fileNameArray) {
				File file = new File(path + fileName);
				FileSystemResource fileResource = new FileSystemResource(file);
				
				fsrList.add(fileResource);
			}			

			String subject = "Performance VDI For MDS Based on ITOP ";
			String subjectPeriod = prevMonthStr + " " + currentYearInt;

			MailService mailService = ctx.getBean("mailService", MailService.class);
			mailService.sendEmail(mapObject, "fm_mailItopReportMDS.txt", fsrList, subject + subjectPeriod);

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
		periodObj.setPrevMonthStr(prevMonthStr);
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
	
	private String getFileNameWeekly(int week) {
		return "VDI_ITOP_Performance_Week" + week + "_" + prevMonthStr + ".pdf";
	}
	
	private String getFileNameMonthly() {
		return "VDI_ITOP_Performance_" + prevMonthStr + "_" + currentYearInt + ".pdf";
	}

}
