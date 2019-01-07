package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.helper.PopulateUserRequest;
import com.vdi.configuration.AppConfig;

//@Component
public class BatchMDSPopulateUserRequest extends QuartzJobBean{

	private final Logger logger = LogManager.getLogger(BatchMDSPopulateUserRequest.class);
	private AnnotationConfigApplicationContext ctx;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Execute BatchMDSPopulateUserRequest......");
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		PopulateUserRequest populateUserRequest = ctx.getBean(PopulateUserRequest.class);
		populateUserRequest.populate();
		
		logger.info("Execute BatchMDSPopulateUserRequest finished......");

	}

	
	
}
