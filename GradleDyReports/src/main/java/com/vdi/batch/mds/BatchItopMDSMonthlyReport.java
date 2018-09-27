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
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.monthly.PopulatePerformance;
import com.vdi.batch.mds.helper.monthly.PopulateSDPerformance;
import com.vdi.batch.mds.helper.monthly.PopulateURPerformance;
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

import net.sf.jasperreports.engine.JRException;

public class BatchItopMDSMonthlyReport extends QuartzJobBean {

	private final String periodMonthly = PropertyNames.CONSTANT_REPORT_PERIOD_MONTHLY;
	private final String periodWeekly = PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY;
	
	private final Logger logger = LogManager.getLogger(BatchItopMDSMonthlyReport.class);

	private AnnotationConfigApplicationContext ctx;

	private Integer currentWeekYear;
	private Integer currentWeekMonth;
	private Integer currentMonth;
	private Integer previousMonth;
	private Integer currentYearInt;
	
	private String currentDateStr;
	private String currentMonthStr;
	private String prevMonthStr;
	
	public BatchItopMDSMonthlyReport() {
		
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Batch itop mds monthly report started.......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		TimeTools timeTools = ctx.getBean(TimeTools.class);		
		this.currentWeekYear = timeTools.getCurrentWeekYear();
		this.currentWeekMonth = timeTools.getCurrentWeekMonth();
		this.currentMonth = timeTools.getCurrentMonth();
		this.previousMonth = currentMonth-1;
		this.currentYearInt = timeTools.getCurrentYear();
		this.currentDateStr = timeTools.getCurrentDateStr();
		this.currentMonthStr = timeTools.getCurrentMonthString();
		this.prevMonthStr = timeTools.getPrevMonthString();

		AppConfig appConfig = ctx.getBean(AppConfig.class);
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
			logger.info("MONDAY.. ");
			
			weeklyWeekYear = currentWeekYear-1;
			weeklyWeekMonth = timeTools.getEndWeekOfMonth(currentYearInt, previousMonth);
			filenameWeekly = getFileNameWeekly(weeklyWeekMonth);
		} else {
			logger.info("Else day.. ");
			
			weeklyWeekYear = currentWeekYear;
			weeklyWeekMonth = currentWeekMonth;
			filenameWeekly = getFileNameWeekly(weeklyWeekMonth);
		}		
		
		ctx.close();		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		rpt = ctx.getBean("itopPerformanceReport", ReportService.class);
		
		populateWeeklyPerformance(ctx, weeklyWeekYear, previousMonth);
		createReport(rpt, path, filenameWeekly, periodWeekly, previousMonth, weeklyWeekMonth);		
		
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
		logger.info("batch week: "+week+"month: "+month);
		
		// populate weekly performance
		com.vdi.batch.mds.helper.weekly.PopulatePerformance weekly = ctx.getBean("populatePerformanceWeekly", com.vdi.batch.mds.helper.weekly.PopulatePerformance.class);
		weekly.populatePerformance(week, month);
		com.vdi.batch.mds.helper.weekly.PopulateSDPerformance weeklySD = ctx.getBean("populateSDPerformanceWeekly", com.vdi.batch.mds.helper.weekly.PopulateSDPerformance.class);
		weeklySD.populatePerformance(week, month);
		com.vdi.batch.mds.helper.weekly.PopulateURPerformance weeklyUR = ctx.getBean("populateURPerformanceWeekly", com.vdi.batch.mds.helper.weekly.PopulateURPerformance.class);
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
	
	private void createReport(ReportService rpt, String path, String fileName, String period, int month, int week) {

		try {
			ReportExporter.exportReport(rpt.getReport(period, month, week), path + fileName);
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
			mailService.sendEmail(mapObject, "fm_mailItopReportMDSMonthly.txt", fsrList, subject + subjectPeriod);

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
