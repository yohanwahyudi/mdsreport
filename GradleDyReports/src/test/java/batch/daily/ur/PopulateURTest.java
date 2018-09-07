package batch.daily.ur;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.batch.mds.helper.PopulateUserRequest;
import com.vdi.configuration.AppConfig;

public class PopulateURTest {

	public static void main(String args[]) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		PopulateUserRequest populate = ctx.getBean("populateUserRequest", PopulateUserRequest.class);
		populate.populate();
	}
	
}
