package batch.daily;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.batch.mds.helper.PopulateSupportAgent;
import com.vdi.configuration.AppConfig;
import com.vdi.tools.ParseCSVService;
import com.vdi.tools.impl.ParseCSVServiceImpl;

public class CsvTest {
	
	private static final Logger logger = LogManager.getLogger(CsvTest.class.getName());
	
	public static void main(String args[]) {
		
//		String file = System.getProperty("user.dir")+File.separator+"test.csv";
		String delimiters = ";";
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		ParseCSVService parse = ctx.getBean("parseCSVService", ParseCSVService.class);
		
		
		PopulateSupportAgent populate = ctx.getBean(PopulateSupportAgent.class);		
		try {
			populate.populate();
			
//			System.out.println(file);
			System.out.println(parse.readHeader());
			System.out.println(parse.readRows());
			
//			System.out.println(populate.getAgentForInsert());
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
