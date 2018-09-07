package test.properties;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.vdi.configuration.AppConfig;

//@ComponentScan("test.properties")
public class Main {
	
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String args[]) {
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyClass.class);
//		MyClass mc = ctx.getBean(MyClass.class);
//		System.out.println(mc.getOrganizationCol());
//		System.out.println(mc.print());
		
		AnnotationConfigApplicationContext ctx2 = new AnnotationConfigApplicationContext(YourClass.class);
		YourClass yc = ctx2.getBean(YourClass.class);
		yc.printMyClass();
		
		MyClass mc = ctx2.getBean(MyClass.class);
		System.out.println("timeout: "+mc.getHttpTimeout());
		
//		AnnotationConfigApplicationContext ctx1 = new AnnotationConfigApplicationContext(IOTools.class);
//		String a = ctx1.getBean("readFile",String.class);
//		String b = ctx1.getBean("readUrl",String.class);
//		
//		logger.debug(a.length());
		
//		AnnotationConfigApplicationContext ctxnew = new AnnotationConfigApplicationContext(AppConfig.class);
//		AppConfig config = ctxnew.getBean("appConfig", AppConfig.class);
//		
//		for(String a:config.getOrganization()) {
//		
//			System.out.println(a);
//		}
		
	}

}
