package report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.querydsl.core.types.Template;
import com.vdi.reports.djasper.templates.TemplateBuilders;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import report.model.Agent;
import report.model.Performance;
import report.model.Subreport1;

@Configuration
public class SimpleReport2 {
	
	protected final static Map<String, Object> params = new HashMap<String, Object>();
	
	@Autowired
	private static TemplateBuilders templateBuilders;
	
	private static Style createTitleStyle() {
		Font font = new Font(12,"Roboto Black","/fonts/Roboto-Black.ttf",Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing,true);
		
		return new StyleBuilder(true).setFont(font).setHorizontalAlign(HorizontalAlign.CENTER).build();
	}
	
	private static Style createHeaderStyle() {
		Font font = new Font();
		font.setFontSize(8);
		font.setBold(true);
		
		Color color = new Color(247, 199, 104);
		
		
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setBackgroundColor(color);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.CENTER);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}

	private static Style createDetailTextStyle() {
		Font font = new Font();
		font.setFontSize(7);
		
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.LEFT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		return sb.build();
	}
	
	private static Style createOddBgStyle() {
		Font font = new Font();
		font.setFontSize(8);
		font.setBold(true);
		
		Color color = new Color(253, 247, 233);
		
		
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setBackgroundColor(color);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.CENTER);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}
	
	public static DynamicReport buildReport() throws Exception{
		
//		FastReportBuilder frb = TemplateBuilders.sAMaster;
//		frb.addColumn("Ticket Total", "totalTicket", Integer.class, 45, createDetailTextStyle(), createHeaderStyle())
//				.addColumn("Ticket Achieved", "totalAchieved", Integer.class, 45, createDetailTextStyle(), createHeaderStyle())
//				.addColumn("Ticket Missed", "totalMissed", Integer.class, 45, createDetailTextStyle(), createHeaderStyle())
//				.addColumn("Achievement", "achievement", Float.class, 45, createDetailTextStyle(), createHeaderStyle())
//				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(createOddBgStyle())
//				.setUseFullPageWidth(true);
		
//		frb = TemplateBuilders.sAMaster;
		
		return templateBuilders.getSAMaster().build();
	}
	
	public static JRDataSource getDataSource() {
		List<Performance> list = new ArrayList<Performance>();
		
		Performance perf = new Performance(100, 100, 0, 100);
		
//		Performance perf = new Performance();
//		perf.setAchievement(100);
//		perf.setTotalAchieved(100);
//		perf.setTotalMissed(0);
//		perf.setTotalTicket(100);
		
		list.add(perf);
		
		return new JRBeanCollectionDataSource(list);
	}
	
	public static JasperPrint getReport() throws JRException, Exception {
		JRDataSource ds = getDataSource();
		JasperReport jr = DynamicJasperHelper.generateJasperReport(buildReport(), new ClassicLayoutManager(), params);
		JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
		
		return jp;
	}
	
	public static void main (String args[]) throws JRException, Exception {
		JasperViewer jv = new JasperViewer(getReport());
		jv.setVisible(true);
	}

}
