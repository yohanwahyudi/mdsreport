package batch.daily.ur;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.batch.mds.helper.monthly.PopulateURPerformance;
import com.vdi.configuration.AppConfig;

public class PerformanceURTest {
	
	public static void main(String args[]) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
//		PopulateURPerformance monthly = ctx.getBean("populateURPerformanceMonthly", PopulateURPerformance.class);
//		monthly.populatePerformance();
		
		com.vdi.batch.mds.helper.weekly.PopulateURPerformance weekly = ctx.getBean("populateURPerformanceWeekly", com.vdi.batch.mds.helper.weekly.PopulateURPerformance.class);
		weekly.populatePerformance();
		
		System.out.println("finished...");
	}

}
