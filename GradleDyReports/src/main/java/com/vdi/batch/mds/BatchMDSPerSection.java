package com.vdi.batch.mds;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.configuration.AppConfig;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.service.ReportService;

public class BatchMDSPerSection extends QuartzJobBean{
	
	private final Logger logger = LogManager.getLogger(BatchMDSPerSection.class);
	
	private AnnotationConfigApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Batch mds per section monthly report started.......");
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ReportService rpt = ctx.getBean("appTeamPerformanceReportService", ReportService.class);
//		ctx.close();
		ReportService rpt1 = ctx.getBean("problemDedicatedReportService", ReportService.class);
		try {
			
//			ReportExporter.exportReport(rpt.getReport(), "D:"+File.separator+"report_section.pdf");
			ReportExporter.exportReport(rpt1.getReport(), "D:"+File.separator+"report_problem.pdf");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
