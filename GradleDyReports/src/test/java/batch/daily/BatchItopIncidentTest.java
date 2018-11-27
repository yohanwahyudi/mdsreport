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

import com.vdi.batch.mds.helper.PopulateProblemChange;
import com.vdi.batch.mds.helper.monthly.PopulateDedicatedAgent;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.AppContext;
import com.vdi.model.Change;
import com.vdi.model.Incident;
import com.vdi.model.Problem;
import com.vdi.model.staging.Staging;
import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.service.helper.ChangeReportHelper;
//import com.vdi.reports.dyreports.templates.service.ReportGeneratorService;
import com.vdi.reports.djasper.service.helper.ProblemReportHelper;

public class BatchItopIncidentTest {
	
	private static final Logger logger = LogManager.getLogger(BatchItopIncidentTest.class);
	private static AbstractApplicationContext annotationCtx;
	
	public static void main (String args[]) throws Exception {
		
		logger.debug("execute batch mds daily...");
		annotationCtx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ItopMDSDataLoaderService itopMDSLoadData = annotationCtx.getBean("problemDataLoaderService", ItopMDSDataLoaderService.class);

		logger.debug("test");
		System.out.println("list: "+itopMDSLoadData.getStagingAllByURL().size());
		
		List<?> allList = (List<?>) itopMDSLoadData.getStagingAllByURL();
		
		
		
		if (allList != null) {
			int size = allList.size();
			logger.debug("MDS daily list size: " + size);
			
			for(Problem c: (List<Problem>)allList) {
				logger.debug(c.getRef());
			}
			
//		PopulateProblemChange populate = annotationCtx.getBean("populateProblemChange", PopulateProblemChange.class);
//		populate.populate();
		
//		PopulateDedicatedAgent populateDedicated = annotationCtx.getBean("populateDedicatedAgent", PopulateDedicatedAgent.class);
//		populateDedicated.insertData();
		
		ProblemReportHelper problemHelper = annotationCtx.getBean("problemReportHelper", ProblemReportHelper.class);
		MasterReport masterReport = problemHelper.getProblemReport();
		
		logger.info(masterReport.getOverallAchievementList().getClass());
		logger.info(masterReport.getOverallAchievementList().get(0).getValue());
		logger.info(masterReport.getDedicatedAgentTeamList().get(0).getAgentName());
		
		ChangeReportHelper changeHelper = annotationCtx.getBean("changeReportHelper", ChangeReportHelper.class);
		masterReport = new MasterReport();
		masterReport = changeHelper.getProblemReport();
		
		logger.info(masterReport.getOverallAchievementList().getClass().getName());
		logger.info(masterReport.getOverallAchievementList().get(0).getValue());
		logger.info(masterReport.getDedicatedAgentTeamList().get(0).getAgentName());
		
			
		} else {
			logger.debug("no incident ticket...");
		}
		logger.debug("finish batch mds daily...");
		
	}


}
