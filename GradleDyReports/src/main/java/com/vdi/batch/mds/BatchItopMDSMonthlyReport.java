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

	private final String period = PropertyNames.CONSTANT_REPORT_PERIOD_MONTHLY;
	private final Logger logger = LogManager.getLogger(BatchItopMDSMonthlyReport.class);
	private AnnotationConfigApplicationContext ctx;
	
	private Integer currentYearInt = TimeStatic.currentYear;
	private final String currentMonthStr = TimeStatic.currentMonthStr;
	private final String prevMonthStr = TimeStatic.prevMonthStr;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Batch itop mds monthly report started.......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		String path = File.separator + "data" + File.separator + "mdsitop" + File.separator + "target" + File.separator
				+ "reports" + File.separator;
		String fileName = "VDI_ITOP_Performance_" + TimeStatic.prevMonthStr + "_" + TimeStatic.currentYear + ".pdf";
		ReportService rpt = ctx.getBean("itopPerformanceReport", ReportService.class);
		
		populatePerformance(ctx);
		createReport(rpt, path, fileName);
		sendEmail(rpt, path, fileName);

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

	private void createReport(ReportService rpt, String path, String fileName) {

		
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

	private void sendEmail(ReportService rpt, String path, String fileName) {
		try {

			Map<String, Object> mapObject = getMapObject(rpt.getPerformanceReport(period));

			File file = new File(path + fileName);
			FileSystemResource fileResource = new FileSystemResource(file);

			String subject = "Performance VDI For MDS Based on ITOP ";
			String subjectPeriod = prevMonthStr + " " + currentYearInt;

			MailService mailService = ctx.getBean("mailService", MailService.class);
			mailService.sendEmail(mapObject, "fm_mailItopReportMDS.txt", fileResource, subject+subjectPeriod);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, Object> getMapObject(List<MasterReport> masterReport){
		MasterReport report = masterReport.get(0);
		
		List<SummaryReport> overallList= report.getOverallAchievementList();
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

}
