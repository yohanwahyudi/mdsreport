package test.quartz.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	private static ClassPathXmlApplicationContext classPathXmlApplicationContext;

	public static void main(String[] args) throws Exception {
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext("test/Spring-Quartz-Test.xml");

	}
}
