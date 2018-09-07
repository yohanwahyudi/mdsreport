package batch.daily;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.AppContext;
import com.vdi.model.Incident;
import com.vdi.model.staging.Staging;
//import com.vdi.reports.dyreports.templates.service.ReportGeneratorService;

public class BatchItopIncidentTest {
	
	private static final Logger logger = LogManager.getLogger(BatchItopIncidentTest.class);
	private static AbstractApplicationContext annotationCtx;
	
	public static void main (String args[]) {
		
		logger.debug("execute batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ItopMDSDataLoaderService itopMDSLoadData = annotationCtx.getBean("itopMDSDataLoaderService", ItopMDSDataLoaderService.class);

		List<Staging> allList = (List<Staging>) itopMDSLoadData.getStagingAllByURL();
		
		if (allList != null) {
			int size = allList.size();
			logger.debug("MDS daily list size: " + size);

			
		} else {
			logger.debug("no incident ticket...");
		}
		logger.debug("finish batch mds daily...");
		
	}


}
