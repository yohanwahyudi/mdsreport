package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateIncident;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

//@Component
public class BatchMDSPopulateIncident extends QuartzJobBean{
	
	private final Logger logger = LogManager.getLogger(BatchMDSPopulateIncident.class);
	private AnnotationConfigApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Execute BatchMDSPopulateIncident......");
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		PopulateIncident populateIncident = ctx.getBean(PopulateIncident.class);
		try {
			populateIncident.populate();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}
		
		
		
		logger.info("Execute BatchMDSPopulateIncident finished......");
		
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
