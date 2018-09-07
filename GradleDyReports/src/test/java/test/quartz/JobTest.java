package test.quartz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobTest {
	
	public static void main(String args[]) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
	}

}
