package batch.daily;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.vdi.batch.mds.repository.dao.StagingIncidentDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.staging.Staging;


public class BatchTest {
	
	private static final Logger logger = LogManager.getLogger(Batch.class);
	private static AbstractApplicationContext annotationCtx;
	
	public static void main (String args[]) {
		annotationCtx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ItopMDSDataLoaderService itopMDSLoadData = annotationCtx.getBean("itopMDSDataLoaderService", ItopMDSDataLoaderService.class);
		List<Staging> allStagingList = new ArrayList<Staging>();
		allStagingList = itopMDSLoadData.getStagingAllByURL();
		
		logger.debug("my list size: "+allStagingList.size());
		
		StagingIncidentDAOService stagingQuery = annotationCtx.getBean("stagingDAORepo", StagingIncidentDAOService.class);
		
		logger.debug("start time: "+new java.util.Date());
		
		
		stagingQuery.deleteEntity();
//		stagingQuery.add(allStagingList.get(0));
		stagingQuery.addAll(allStagingList);
		
//		BaseQueryService baseQuery = annotationCtx.getBean("baseQueryService", BaseQueryService.class);
//		baseQuery.deleteAllStaging(Staging.class);
//		baseQuery.addAll(allStagingList);
//		baseQuery.add(allStagingList.get(1));
		
		logger.debug("end time: "+new java.util.Date());
		annotationCtx.close();
	}

}
