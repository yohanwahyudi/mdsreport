package batch.daily;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.batch.mds.helper.weekly.PopulatePerformance;
import com.vdi.batch.mds.helper.weekly.PopulateSDPerformance;
import com.vdi.configuration.AppConfig;

public class PerfTest2 {
	
	private final static Logger logger = LogManager.getLogger(PerfTest2.class.getName());
	
	public static void main (String args[]) {
		
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		PopulatePerformance populatePerfWeekly = ctx.getBean("populatePerformanceWeekly",PopulatePerformance.class);
//		com.vdi.batch.mds.helper.monthly.PopulatePerformance saMonthly = ctx.getBean("populatePerformanceMonthly",com.vdi.batch.mds.helper.monthly.PopulatePerformance.class);
//		
		PopulateSDPerformance sdWeekly = ctx.getBean("populateSDPerformanceWeekly",PopulateSDPerformance.class);
//		com.vdi.batch.mds.helper.monthly.PopulateSDPerformance sdMonthly = ctx.getBean("populateSDPerformanceMonthly",com.vdi.batch.mds.helper.monthly.PopulateSDPerformance.class);
		
		logger.debug("begin");
		populatePerfWeekly.populatePerformance();
//		saMonthly.populatePerformance();
		sdWeekly.populatePerformance();
		
//		sdMonthly.populatePerformance();
		
		logger.debug("finished");
		
	}

}
