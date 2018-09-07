package com.vdi.batch.mds;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.monthly.PopulatePerformance;
import com.vdi.configuration.AppConfig;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.tools.TimeStatic;

import net.sf.jasperreports.engine.JRException;

//@Component
public class BatchMDSReportSAMonthly extends QuartzJobBean{

	private Logger logger = LogManager.getLogger(BatchMDSReportSAWeekly.class);
	private AnnotationConfigApplicationContext ctx;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("Execute BatchMDSReportSAMonthly......");
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		PopulatePerformance monthly = ctx.getBean("populatePerformanceMonthly", PopulatePerformance.class);
		monthly.populatePerformance();
		
		String fileName = "VDI_SupportAgent_Month_"+TimeStatic.prevMonthStr+"_"+TimeStatic.currentYear+".pdf";
		ReportService rpt = ctx.getBean("monthlyIncidentSAReport", ReportService.class);
		
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
		
		logger.debug("Execute BatchMDSReportSAMonthly finished......");
		
	}
	
	
	
}
