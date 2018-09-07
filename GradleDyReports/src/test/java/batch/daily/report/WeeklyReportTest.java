package batch.daily.report;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.configuration.AppConfig;
import com.vdi.configuration.PropertyNames;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.service.impl.ItopPerformanceReport;

import net.sf.jasperreports.engine.JRException;

public class WeeklyReportTest {
	
private static final Logger logger = LogManager.getLogger(WeeklyReportTest.class);
	
	public static void main (String args[]) throws FileNotFoundException, JRException, Exception {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ItopPerformanceReport monthlyReport = ctx.getBean("itopPerformanceReport", ItopPerformanceReport.class);
		
		ReportExporter.exportReport(monthlyReport.getReport(PropertyNames.CONSTANT_REPORT_PERIOD_WEEKLY), 
				System.getProperty("user.dir") + "/target/reports/" + "weekly_incident" + ".pdf");
		
		logger.debug("finish...........");
		ctx.close();
		
	}


}
