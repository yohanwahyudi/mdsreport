package batch.daily;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.mchange.v2.reflect.ReflectUtils;
import com.vdi.batch.mds.helper.PopulateUserRequestYTD;
import com.vdi.batch.mds.repository.dao.StagingIncidentDAOService;
import com.vdi.batch.mds.repository.dao.StagingUReqYTDDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.impl.IncidentOpenDataLoader;
import com.vdi.batch.mds.service.impl.UserRequestDataLoaderYTDImpl;
import com.vdi.configuration.AppConfig;
import com.vdi.model.IncidentOpen;
import com.vdi.model.staging.Staging;
import com.vdi.model.staging.StagingUserRequestYTD;

public class iTopLoaderTest {

	private static final Logger logger = LogManager.getLogger(Batch.class);
	private static AbstractApplicationContext annotationCtx;

	public static void main(String args[]) throws IllegalArgumentException, IllegalAccessException {
		annotationCtx = new AnnotationConfigApplicationContext(AppConfig.class);

		ItopMDSDataLoaderService itopMDSLoadData = annotationCtx.getBean("incidentOpenDLService", IncidentOpenDataLoader.class);
		
		List<IncidentOpen> allStagingList = new ArrayList<IncidentOpen>();
		allStagingList = itopMDSLoadData.getStagingAllByURL();

		IncidentOpen staging = allStagingList.get(0);

		for(Field field : staging.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			
			System.out.println("name: " + field.getName() + " value: " + field.get(staging));
		}

		logger.info("my list size: " + allStagingList.size());
		
		
		
		logger.info("start time: " + new java.util.Date());

		// stagingQuery.addAll(allStagingList);


		logger.info("end time: " + new java.util.Date());
		annotationCtx.close();
	}

}
