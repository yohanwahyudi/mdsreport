package com.vdi.batch.mds;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.Incident;
import com.zaxxer.hikari.HikariDataSource;
//import com.vdi.reports.dyreports.templates.service.ReportGeneratorService;

//@Component
//@ComponentScan({ "com.vdi.batch.mds.service", "com.vdi.configuration" })
public class BatchMDSDaily extends QuartzJobBean {

	private static final Logger logger = LogManager.getLogger(BatchMDSDaily.class);
	private static AbstractApplicationContext annotationCtx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("execute batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			JsoupParseService jsoupParse = annotationCtx.getBean("jsoupParseServiceDailyMDS", JsoupParseService.class);
	
			List<Incident> allDailyList = (List<Incident>) jsoupParse.getIncidentAllByURL();
			List<Incident> deadlineList = (List<Incident>) jsoupParse.getIncidentDeadline();
			List<Incident> assignedList = (List<Incident>) jsoupParse.getIncidentAssign();
			List<Incident> pendingList = (List<Incident>) jsoupParse.getIncidentPending();
	
			// List<Incident> allDailyList = (List<Incident>)
			// annotationCtx.getBean("getIncidentAllByFileDaily", List.class);
			// List<Incident> deadlineList = (List<Incident>)
			// annotationCtx.getBean("getIncidentDeadline", List.class);
			// List<Incident> assignedPendingList = (List<Incident>)
			// annotationCtx.getBean("getIncidentAssignPending", List.class);
			
			if (allDailyList != null) {
				int size = allDailyList.size();
				logger.debug("MDS daily list size: " + size);
	
				if (size != 0) {				
					String prefix = "MDS_daily_";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String suffix = sdf.format(new java.util.Date());
					String filename = prefix + suffix + ".pdf";
	
	//				ReportGeneratorService reportService = annotationCtx.getBean("reportGeneratorService",
	//						ReportGeneratorService.class);
	//				reportService.buildDailyReport(allDailyList, filename);
	
					Map<String, Object> mapObject = new HashMap<String, Object>();
					mapObject.put("deadline", deadlineList);
					mapObject.put("assign", assignedList);
					mapObject.put("pending", pendingList);
	
					MailService mailService = annotationCtx.getBean("mailService", MailService.class);
					mailService.sendEmail(mapObject,"fm_mailTemplateDaily.txt");
				}
			} else {
				logger.info("no incident ticket...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HikariDataSource hds = annotationCtx.getBean("dataSource", HikariDataSource.class);
			logger.info("close datasource");
			logger.info(hds.getPoolName()+"-"+hds.getJdbcUrl());
			try {
				hds.close();
			} catch (Exception e) {
				logger.info("Error closing datasource ");
				e.printStackTrace();
			}
		}
		
		
		logger.info("finish batch mds daily...");

	}

}
