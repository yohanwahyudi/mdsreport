package batch.daily;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.configuration.AppConfig;
import com.vdi.tools.IOToolsService;

public class WriteFileFromURL {
	
	
	public static void main(String args[]) throws IOException {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		IOToolsService iotools = ctx.getBean("ioTools", IOToolsService.class);
		
		String str = iotools.readUrl("http://172.17.6.21/itop/web/api/Query4_7de218123e61b7efd7778a054d4f992c.php");
		iotools.writeBuffered(str, 1024, "userrequest.txt");
		
	}
	

}
