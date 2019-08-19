package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateIncident;
import com.vdi.batch.mds.helper.mtd.MtdPerformanceIncident;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BatchMDSMtdPerformance extends QuartzJobBean {

	private final Logger logger = LogManager.getLogger(BatchMDSMtdPerformance.class);
	private AnnotationConfigApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Batch itop mds performance started.......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		populateData(ctx);
		populatePerformance(ctx);
		
		closeDataSource();
		
		logger.info("Batch itop mds performance ended.......");

	}

	private void populatePerformance(AnnotationConfigApplicationContext ctx) {

		// populate performance
		logger.info("start calculate mtd performance");
		
		MtdPerformanceIncident incident = ctx.getBean(MtdPerformanceIncident.class);
		incident.populate();
		
		logger.info("end calculate mtd performance");
	}
	
	private void populateData(AnnotationConfigApplicationContext ctx) {
		
		logger.info("start populate....");
		PopulateIncident populateIncident = ctx.getBean(PopulateIncident.class);
//		PopulateServiceDesk populateSD = ctx.getBean(PopulateServiceDesk.class);
//		PopulateUserRequest populateUR = ctx.getBean(PopulateUserRequest.class);
//		
//		PopulateProblemChange problemChange = ctx.getBean(PopulateProblemChange.class);
//		PopulateUserRequestYTD urYtd = ctx.getBean(PopulateUserRequestYTD.class);
		
		populateIncident.populate();
//		populateSD.populate();
//
//		problemChange.populate();
//		populateUR.populate();
//		urYtd.populate();
		
		logger.info("End populate....");
		
	}
	
	private void closeDataSource() {
		
		HikariDataSource hds = ctx.getBean("dataSource", HikariDataSource.class);
		logger.info("close datasource");
		logger.info(hds.getPoolName()+"-"+hds.getJdbcUrl());
		try {
			hds.close();
		} catch (Exception e) {
			logger.info("Error closing datasource ");
			e.printStackTrace();
		}
		
	}
	
	

}
