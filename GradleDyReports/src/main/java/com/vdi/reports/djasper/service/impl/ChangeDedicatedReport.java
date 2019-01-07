package com.vdi.reports.djasper.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.PerformanceReport;
import com.vdi.reports.djasper.service.ReportService;
import com.vdi.reports.djasper.service.helper.ChangeReportHelper;
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

@Component("changeDedicatedReportService")
public class ChangeDedicatedReport implements ReportService {

	private final Logger logger = LogManager.getLogger(ChangeDedicatedReport.class);

	@Autowired
	private ChangeReportHelper changeHelper;

	@Autowired
	private TimeTools timeTools;

	@Autowired
	private TemplateBuildersReport templateBuilders;

	protected final Map<String, Object> params = new HashMap<String, Object>();

	@Override
	public DynamicReport buildReport() {

		String monthStr = timeTools.getPrevMonthString();
		int year = timeTools.getCurrentYear();
		
		if(timeTools.getCurrentMonth()==1) {
			year = year - 1;
		}
		
		DynamicReportBuilder master = templateBuilders.getMaster();
		master.setTitle("DEDICATED AGENT CONTRIBUTION FOR CHANGE");
		master.setSubtitle("Based on iTop "+monthStr+" "+year);
		
		params.put("summarySubParam", changeHelper.getChangeReport().getOverallAchievementList());
		params.put("teamSubParam", changeHelper.getChangeReport().getDedicatedTeamList());
		params.put("agentSubParam", changeHelper.getChangeReport().getDedicatedAgentTeamList());
		params.put("allChangeSummaryParam", changeHelper.getChangeReport().getSupportAgentSummaryList());
		params.put("allOpenChangeAgentParam", changeHelper.getChangeReport().getDedicatedTeamList1());
		params.put("allOpenChangeListParam", changeHelper.getChangeReport().getChangeList());
		
		DynamicReport summarySub = templateBuilders.getChangeSummarySub();
		DynamicReport teamSub = templateBuilders.getDedicatedTeamSub();
		DynamicReport agentSub = templateBuilders.getDedicatedAgentSub();
		DynamicReport allSummarySub = templateBuilders.getAllChangeSummarySub();
		DynamicReport openChangeTeamSub = templateBuilders.getAllChangeSummarybyAgentSub();
		DynamicReport openChangeListSub = templateBuilders.getAllOpenChangeListSub();
		
		master.addConcatenatedReport(summarySub, new ClassicLayoutManager(), "summarySubParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(teamSub, new ClassicLayoutManager(), "teamSubParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(agentSub, new ClassicLayoutManager(), "agentSubParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		master.addConcatenatedReport(allSummarySub, new ClassicLayoutManager(), "allChangeSummaryParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		master.addConcatenatedReport(openChangeTeamSub, new ClassicLayoutManager(), "allOpenChangeAgentParam",
				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
//		master.addConcatenatedReport(openChangeListSub, new ClassicLayoutManager(), "allOpenChangeListParam",
//				DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, true);
		
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

		return new JRBeanCollectionDataSource(changeHelper.getChangeReportList());
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
