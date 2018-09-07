package com.vdi.batch.mds;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.weekly.PopulatePerformance;
import com.vdi.configuration.AppConfig;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.tools.TimeStatic;

import net.sf.jasperreports.engine.JRException;

//@Component
public class BatchMDSReportSAWeekly extends QuartzJobBean{

	private Logger logger = LogManager.getLogger(BatchMDSReportSAWeekly.class);
	private AnnotationConfigApplicationContext ctx;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("Execute BatchMDSReportSAWeekly......");
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		PopulatePerformance weekly = ctx.getBean("populatePerformanceWeekly", PopulatePerformance.class);
		weekly.populatePerformance();
		
		String fileName = "VDI_SupportAgent_Week"+(TimeStatic.currentWeekMonth-1)+"_"+TimeStatic.currentMonthStr+".pdf";
		ReportService rpt = ctx.getBean("weeklyIncidentSAReport", ReportService.class);
		try {
			ReportExporter.exportReport(rpt.getReport(), System.getProperty("user.dir") + "/target/reports/" 
					+ fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		logger.debug("Execute BatchMDSReportSAWeekly finished......");
		
	}

}
