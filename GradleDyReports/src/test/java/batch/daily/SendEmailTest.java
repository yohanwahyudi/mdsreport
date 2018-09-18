package batch.daily;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;

public class SendEmailTest {
	
	private static AnnotationConfigApplicationContext ctx;
	
	public static void main(String args[]) {
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		String path = "/data/mdsitop/target/reports/";
		String fileNameArray[] = {"VDI_ITOP_Performance_Week2_September.pdf","VDI_ITOP_Performance_Week3_September.pdf"};
		
		sendEmail(path, fileNameArray, "monthly");
		
	}
	
	private static void sendEmail(String path, String[] fileNameArray, String period) {
		try {

			Map<String, Object> mapObject = new HashMap<>();
			
			List<FileSystemResource> fsrList = new ArrayList<FileSystemResource>();
			for(String fileName:fileNameArray) {
				File file = new File(path + fileName);
				FileSystemResource fileResource = new FileSystemResource(file);
				
				fsrList.add(fileResource);
			}			

			String subject = "Performance VDI For MDS Based on ITOP ";
			String subjectPeriod = "September" + " " + 2018;

			MailService mailService = ctx.getBean("mailService", MailService.class);
			mailService.sendEmail(mapObject, "fm_mailtemplateempty.txt", fsrList, subject + subjectPeriod);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
