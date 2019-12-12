package http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class RunMainClass {
	
	private static final Logger logger = LogManager.getLogger(RunMainClass.class);
	//private static ClassPathXmlApplicationContext classPathXmlApplicationContext;
	
	public static void main(String args[]) {
		
		logger.info("RunMain Batch started...");			
		SpringApplication.run(RunMainClass.class, args);
		
			
		//classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
		

	}

}
