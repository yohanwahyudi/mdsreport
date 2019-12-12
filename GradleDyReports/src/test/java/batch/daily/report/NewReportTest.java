package batch.daily.report;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.configuration.AppConfig;
import com.vdi.reports.djasper.service.helper.SupportAgentReportHelper;

import batch.daily.ReportTest;

public class NewReportTest {

	public static final Logger logger = LogManager.getLogger(ReportTest.class);
	
	public static void main(String args[]) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		SupportAgentReportHelper saHelper = ctx.getBean("supportAgentReportHelper",SupportAgentReportHelper.class);
		saHelper.getWeeklyReport();
		saHelper.getMonthlyReport();
		
		
		
	}

}
