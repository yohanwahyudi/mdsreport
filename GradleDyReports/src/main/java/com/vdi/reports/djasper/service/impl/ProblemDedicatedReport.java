package com.vdi.reports.djasper.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.PerformanceReport;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.reports.djasper.service.helper.ProblemReportHelper;
import com.vdi.reports.djasper.templates.TemplateBuildersReport;
import com.vdi.tools.TimeTools;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service("problemDedicatedReportService")
public class ProblemDedicatedReport implements ReportService{
	
	private final Logger logger = LogManager.getLogger(ProblemDedicatedReport.class);
	
	@Autowired
	private ProblemReportHelper problemHelper;
	
	@Autowired
	private TimeTools timeTools;
	
	@Autowired
	private TemplateBuildersReport templateBuilders;
	
	protected final Map<String, Object> params = new HashMap<String, Object>();

	@Override
	public DynamicReport buildReport() {
		
		String monthStr = timeTools.getPrevMonthString();
		int year = timeTools.getCurrentYear();
		
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("DEDICATED AGENT CONTRIBUTION FOR PROBLEM");
		master.setSubtitle("Based on iTop "+monthStr+" "+year);
		
		params.put("summarySubParam", problemHelper.getProblemReport().getOverallAchievementList());
		params.put("teamSubParam", problemHelper.getProblemReport().getDedicatedTeamList());
		params.put("agentSubParam", problemHelper.getProblemReport().getDedicatedAgentTeamList());
		
		DynamicReport summarySub = templateBuilders.getProblemSummarySub();
		DynamicReport teamSub = templateBuilders.getDedicatedTeamSub();
		DynamicReport agentSub = templateBuilders.getDedicatedAgentSub();
		
		master.addConcatenatedReport(summarySub, new ClassicLayoutManager(), "summarySubParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(teamSub, new ClassicLayoutManager(), "teamSubParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(agentSub, new ClassicLayoutManager(), "agentSubParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		
		return master.build();
	
	}

	@Override
	public JasperPrint getReport() throws JRException, Exception {
		
		JRDataSource ds = getDataSource();
		JasperReport jr = DynamicJasperHelper.generateJasperReport(buildReport(), new ClassicLayoutManager(),
				params);
		JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
		
		return jp;
	}

	@Override
	public JasperPrint getReport(String period) throws JRException, Exception {
		
		return null;
	}

	@Override
	public JasperPrint getReport(String period, int month, int week) throws JRException, Exception {
		
		return null;
	}

	@Override
	public JRDataSource getDataSource() {		
		
		return new JRBeanCollectionDataSource(problemHelper.getProblemReportList());
	}

	@Override
	public List<PerformanceReport> getPerformanceReport() {
		
		return null;
	}

	@Override
	public List<MasterReport> getPerformanceReport(String period) throws Exception {
		
		return null;
	}
	
	

}
