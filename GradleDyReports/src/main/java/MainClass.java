import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
	private static final Logger logger = LogManager.getLogger(MainClass.class);
	private static ClassPathXmlApplicationContext classPathXmlApplicationContext;
	
	public static void main(String args[]) {
		
		logger.debug("Batch started...");		
		
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
		

	}

}
