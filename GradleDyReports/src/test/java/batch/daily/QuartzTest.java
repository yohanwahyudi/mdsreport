package batch.daily;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuartzTest {
	
	private static final Logger logger = LogManager.getLogger(QuartzTest.class);
	
	public static void main(String args[]) {
		
		
logger.debug("Batch started...");		
		
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("quartz1.xml");
		
//		classPathXmlApplicationContext.close();
		
	}

}
