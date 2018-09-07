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

import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.AppContext;
import com.vdi.model.Incident;
//import com.vdi.reports.dyreports.templates.service.ReportGeneratorService;

public class BatchMDSDailyTest {
	
	private static final Logger logger = LogManager.getLogger(BatchMDSDailyTest.class);
	private static AbstractApplicationContext annotationCtx;
	
	public static void main (String args[]) {
		
		logger.debug("execute batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		JsoupParseService jsoupParse = annotationCtx.getBean("jsoupParseServiceDailyMDS", JsoupParseService.class);
		
		List<Incident> allDailyList = (List<Incident>) jsoupParse.getIncidentAllByURL();
		List<Incident> deadlineList = (List<Incident>) jsoupParse.getIncidentDeadline();
		List<Incident> assignedList = (List<Incident>) jsoupParse.getIncidentAssign();
		List<Incident> pendingList = (List<Incident>) jsoupParse.getIncidentPending();
		
		System.out.println("allDailyList: "+allDailyList.size());
	}

}
