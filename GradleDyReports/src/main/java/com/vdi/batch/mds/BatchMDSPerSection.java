package com.vdi.batch.mds;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateProblemChange;
import com.vdi.batch.mds.helper.monthly.PopulateDedicatedAgent;
import com.vdi.configuration.AppConfig;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.tools.TimeTools;

import net.sf.jasperreports.engine.JRException;

public class BatchMDSPerSection extends QuartzJobBean {

	private final Logger logger = LogManager.getLogger(BatchMDSPerSection.class);

	private AnnotationConfigApplicationContext ctx;	

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Batch mds per section monthly report started.......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		TimeTools timeTools = ctx.getBean(TimeTools.class);	
		AppConfig appConfig = ctx.getBean(AppConfig.class);
		String path = appConfig.getMdsReportPath();
		String fileSection = "/"+"VDI_ITOP_Section_Contribution_"+timeTools.getPrevMonthString()+"_"+timeTools.getCurrentYear()+".pdf";
		String fileProblem = "/"+"VDI_ITOP_Problem_Contribution_"+timeTools.getPrevMonthString()+"_"+timeTools.getCurrentYear()+".pdf";
		String fileChange = "/"+"VDI_ITOP_Change_Contribution_"+timeTools.getPrevMonthString()+"_"+timeTools.getCurrentYear()+".pdf";

		try {
			populateProblemChange(ctx);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

//		ReportService rpt = ctx.getBean("appTeamPerformanceReportService", ReportService.class);
//		createReport(rpt, path+fileSection);
//		ctx.close();
//
//		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//		rpt = ctx.getBean("problemDedicatedReportService", ReportService.class);
//		createReport(rpt, path+fileProblem);
//		ctx.close();
//		
//		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//		rpt = ctx.getBean("changeDedicatedReportService", ReportService.class);
//		createReport(rpt, path+fileChange);
//		ctx.close();
		
		logger.info("Batch mds per section monthly report finished.......");
		
	}

	private void populateProblemChange(AnnotationConfigApplicationContext ctx) throws Exception {

		PopulateProblemChange populate = ctx.getBean("populateProblemChange", PopulateProblemChange.class);
		populate.populate();

		PopulateDedicatedAgent populateDedicated = ctx.getBean("populateDedicatedAgent", PopulateDedicatedAgent.class);
		populateDedicated.insertData();

	}

	private void createReport(ReportService rpt, String fileName) {

		try {
			ReportExporter.exportReport(rpt.getReport(), fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
