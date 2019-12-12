package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateUserRequest;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

//@Component
public class BatchMDSPopulateUserRequest extends QuartzJobBean{

	private final Logger logger = LogManager.getLogger(BatchMDSPopulateUserRequest.class);
	private AnnotationConfigApplicationContext ctx;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Execute BatchMDSPopulateUserRequest......");
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		try {
			PopulateUserRequest populateUserRequest = ctx.getBean(PopulateUserRequest.class);
			populateUserRequest.populate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}		
		
		logger.info("Execute BatchMDSPopulateUserRequest finished......");

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
