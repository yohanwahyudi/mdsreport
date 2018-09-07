package batch.daily;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.configuration.AppConfig;
import com.vdi.reports.ReportExporter;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.reports.djasper.templates.TemplateBuilders;
import com.vdi.reports.djasper.templates.TemplateFonts;
import com.vdi.reports.djasper.templates.TemplateStyles;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

public class ReportTest {
	
	public static final Logger logger = LogManager.getLogger(ReportTest.class);
	
	public static void main(String args[]) throws JRException, Exception {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//		TemplateStyles reportSvc = ctx.getBean("templateStyles", TemplateStyles.class);
//		
//		System.out.println(reportSvc.getStandardOddBgStyle().getFont().getFontName());
//		
//		TemplateBuilders tb = ctx.getBean("templateBuilders", TemplateBuilders.class);
//		
//		System.out.println(tb.getsAMaster().getColumn(1).getTitle());
//		System.out.println(tb.getSaAssignedSub().getColumns().get(2).getTitle());
		
		//weeklySaIncidentReport
//		ReportService rpt = ctx.getBean("weeklyIncidentSAReport", ReportService.class);		
//		ReportService rpt = ctx.getBean("monthlyIncidentSAReport", ReportService.class);
		ReportService rpt = ctx.getBean("weeklyIncidentSDReport", ReportService.class);
//		ReportService rpt = ctx.getBean("monthlyIncidentSDReport", ReportService.class);
//		ReportService rpt = ctx.getBean("weeklyTest", ReportService.class);
		
		
		ReportExporter.exportReport(rpt.getReport(), System.getProperty("user.dir") + "/target/reports/" 
				+ "weekly_incident_servicedesk" + ".pdf");
//		JasperViewer jv = new JasperViewer(rpt.getReport());
//		jv.setVisible(true);
		
		
		
		ctx.close();
	}

}
