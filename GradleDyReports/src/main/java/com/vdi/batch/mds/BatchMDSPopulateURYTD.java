package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateUserRequestYTD;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BatchMDSPopulateURYTD extends QuartzJobBean {

	Logger logger = LogManager.getLogger(BatchMDSPopulateURYTD.class);

	private AnnotationConfigApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		logger.info("Execute BatchMDSPopulateURYTD......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			PopulateUserRequestYTD populateUR = ctx.getBean(PopulateUserRequestYTD.class);
			populateUR.populate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}
		
		
		
		
		logger.info("Execute BatchMDSPopulateURYTD finished......");


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
