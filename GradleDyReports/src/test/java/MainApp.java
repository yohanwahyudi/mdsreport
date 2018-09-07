
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import test.prop.config.AppConfig;
import test.prop.service.IService;

public class MainApp {

	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		IService service = (IService) context.getBean("service");
		System.out.println(service.getValue());
		
		context.close();
	}
}
