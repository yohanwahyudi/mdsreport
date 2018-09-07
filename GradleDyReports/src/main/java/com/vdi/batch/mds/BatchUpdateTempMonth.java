package com.vdi.batch.mds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vdi.batch.mds.repository.dao.TempValueService;
import com.vdi.configuration.AppConfig;
import com.vdi.tools.TimeStatic;

//@Component
public class BatchUpdateTempMonth extends QuartzJobBean{

	private Logger logger = LogManager.getLogger(BatchUpdateTempMonth.class);
	AnnotationConfigApplicationContext ctx;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		logger.debug("Execute batch BatchUpdateTempMonth...");
		
		String currentMonth = TimeStatic.currentMonthStr;
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		TempValueService tempValueService = ctx.getBean("tempValueDAO", TempValueService.class);
		tempValueService.updateTempValue("LAST_MONTH", currentMonth);
		
		logger.debug("Execute batch BatchUpdateTempMonth finished...");
		
	}

	
	
}
