package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateIncident;
import com.vdi.batch.mds.helper.PopulateServiceDesk;
import com.vdi.batch.mds.helper.PopulateUserRequest;
import com.vdi.batch.mds.helper.mtd.MtdPerformanceIncident;
import com.vdi.batch.mds.helper.mtd.MtdPerformanceSD;
import com.vdi.batch.mds.helper.mtd.MtdPerformanceUR;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BatchMDSMtdPerformance extends QuartzJobBean {

	private final Logger logger = LogManager.getLogger(BatchMDSMtdPerformance.class);
	private AnnotationConfigApplicationContext ctx;
	
	public BatchMDSMtdPerformance() {
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Batch itop mds performance started.......");
		
		try {
			executeBatch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info("Batch itop mds performance ended.......");

	}
	
	public void executeBatch() throws InterruptedException {
		
		incidentProcess(ctx);
		sdProcess(ctx);
		urProcess(ctx);

		closeDataSource();
		
	}

	private void incidentProcess(AnnotationConfigApplicationContext ctx) {

		logger.info("start incident process");
		PopulateIncident populateIncident = ctx.getBean(PopulateIncident.class);
		populateIncident.populate();

		MtdPerformanceIncident incidentPerformance = ctx.getBean(MtdPerformanceIncident.class);
		incidentPerformance.populate();

		logger.info("finish incident process");

	}

	private void sdProcess(AnnotationConfigApplicationContext ctx) throws InterruptedException {

		logger.info("start sd process");

		PopulateServiceDesk populateSD = ctx.getBean(PopulateServiceDesk.class);
		populateSD.populate();
		
//		Thread.sleep(30000);

		MtdPerformanceSD sdPerformance = ctx.getBean(MtdPerformanceSD.class);
		sdPerformance.populate();

		logger.info("finish sd process");

	}

	private void urProcess(AnnotationConfigApplicationContext ctx) throws InterruptedException {

		logger.info("start ur process");
		
//		Thread.sleep(10000);

		PopulateUserRequest populateUR = ctx.getBean(PopulateUserRequest.class);
		populateUR.populate();
		
//		Thread.sleep(30000);

		MtdPerformanceUR urPerformance = ctx.getBean(MtdPerformanceUR.class);
		urPerformance.populate();

		logger.info("finish ur process");

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

}
