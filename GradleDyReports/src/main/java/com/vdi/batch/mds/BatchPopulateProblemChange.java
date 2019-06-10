package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateProblemChange;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BatchPopulateProblemChange extends QuartzJobBean{
	
	private final Logger logger = LogManager.getLogger(BatchPopulateProblemChange.class);
	
	private AnnotationConfigApplicationContext ctx;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("Execute BatchPopulateProblemChange......");
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		PopulateProblemChange populate = ctx.getBean("populateProblemChange", PopulateProblemChange.class);
		try {
			populate.populate();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		HikariDataSource hds = ctx.getBean("dataSource", HikariDataSource.class);
		logger.info("close datasource");
		logger.info(hds.getPoolName()+"-"+hds.getJdbcUrl());
		try {
			hds.close();
		} catch (Exception e) {
			logger.info("Error closing datasource ");
			e.printStackTrace();
		}
		
		logger.info("Execute BatchPopulateProblemChange finished......");
		
	}
	
	

}
