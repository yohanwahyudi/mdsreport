package batch.daily;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.batch.mds.helper.monthly.PopulatePerformance;
import com.vdi.batch.mds.helper.monthly.PopulateSDPerformance;
import com.vdi.configuration.AppConfig;

public class PerfTestMonthly {
	
	private final static Logger logger = LogManager.getLogger(PerfTestMonthly.class.getName());
	private static AnnotationConfigApplicationContext ctx;
	
	public static void main (String args[]) {
		
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//		PopulatePerformance populatePerf = ctx.getBean("populatePerformanceMonthly",PopulatePerformance.class);
		logger.debug("begin");
//		populatePerf.populatePerformance();
		logger.debug("finished");
		
		logger.debug("start sd");
		PopulateSDPerformance populateSdPerf = ctx.getBean("populateSDPerformanceMonthly", PopulateSDPerformance.class);
		populateSdPerf.populatePerformance();
		logger.debug("finished sd");
	}

}
