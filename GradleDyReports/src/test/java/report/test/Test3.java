package report.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.configuration.AppConfig;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import report.SimpleReport3;

public class Test3 {
	
	public static void main(String args[]) throws JRException, Exception {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		SimpleReport3 simpleReport = ctx.getBean("confSaIncidentReport", SimpleReport3.class);
		
		JasperViewer jv = new JasperViewer(simpleReport.getReport());
		jv.setVisible(true);
		
		ctx.close();
	}

}
