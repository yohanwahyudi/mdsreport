package com.vdi.reports.djasper.templates;

import java.awt.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

@Configuration
public class TemplateBuildersReport {

	private DynamicReportBuilder master;

	// summary report
	private DynamicReport summaryOverallSub;
	private DynamicReport summarySDSub;

	// for support agent report
	private DynamicReport summarySub;
	private DynamicReport summarySub2;
	private DynamicReport teamSub;
	private DynamicReport agentSub;
	private DynamicReport missedSub;
	private DynamicReport pendingSub;
	private DynamicReport assignedSub;
	private DynamicReport incidentListSub;

	// for service desk report
	private DynamicReport serviceDeskPersonSub;
	private DynamicReport serviceDeskAgentSub;
	private DynamicReport serviceDeskIncidentSub;
	private DynamicReport userRequestAgentSub;
	private DynamicReport userRequestIncidentSub;

	// for application team report
	private DynamicReport appTeamSub;
	private DynamicReport appTeamAgentSub;

	// for problem change report
	private DynamicReport problemSummarySub;
	private DynamicReport allProblemSummarySub;
	private DynamicReport allProblemSummarybyAgentSub;
	private DynamicReport allOpenProblemListSub;
	
	private DynamicReport changeSummarySub;
	private DynamicReport allChangeSummarySub;
	private DynamicReport allChangeSummarybyAgentSub;
	private DynamicReport allOpenChangeListSub;
	private DynamicReport dedicatedTeamSub;
	private DynamicReport dedicatedAgentSub;

	public TemplateBuildersReport() {
	}

	@Autowired
	public TemplateBuildersReport(TemplateStylesReport style) throws ColumnBuilderException, ClassNotFoundException {

		this.master = createMaster(style);

		this.summaryOverallSub = createSummaryOverallSub(style);
		this.summarySDSub = createSummarySDSub(style);

		this.summarySub = createSummarySub(style);
		this.summarySub2 = createSummarySub2(style);
		this.teamSub = createTeamSub(style);
		this.agentSub = createAgentSub(style);
		this.missedSub = createMissedSub(style);
		this.pendingSub = createPendingSub(style);
		this.assignedSub = createAssignedSub(style);
		this.incidentListSub = createIncidentListSub(style);

		this.serviceDeskPersonSub = createServicedeskPersonSub(style);
		this.serviceDeskAgentSub = createServicedeskAgentSub(style);
		this.serviceDeskIncidentSub = createServicedeskIncidentSub(style);
		this.userRequestAgentSub = createUserRequestAgentSub(style);
		this.userRequestIncidentSub = createUserRequestIncidentSub(style);

		this.appTeamSub = createAppTeamSub(style);
		this.appTeamAgentSub = createAppTeamAgentSub(style);

		this.problemSummarySub = createProblemSummarySub(style);
		this.allProblemSummarySub = createAllProblemSummarySub(style);
		this.allProblemSummarybyAgentSub = createAllOpenedProblemByAgentSub(style);
		this.allOpenProblemListSub = createAllOpenProblemList(style);
		
		this.changeSummarySub = createChangeSummarySub(style);
		this.allChangeSummarySub = createAllOpenChangeSummarySub(style);
		this.allChangeSummarybyAgentSub = createAllOpenedChangeByAgentSub(style);
		this.allOpenChangeListSub = createAllOpenChangeList(style);
		this.dedicatedTeamSub = createDedicatedTeamSummarySub(style);
		this.dedicatedAgentSub = createDedicatedAgentSub(style);

	}

	private DynamicReportBuilder createMaster(TemplateStylesReport style) {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(10)
				.setSubtitleStyle(style.getArialTitleAgentStyle()).setSubtitleHeight(15)
				// .setDetailHeight(15)
				.setLeftMargin(margin).setRightMargin(margin).setTopMargin(5).setBottomMargin(5);
				// .setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getOddRowStyle())
//				.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT);
		return drb;
	}

	private DynamicReport createSummarySub(TemplateStylesReport style) {

		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(45)).setStyle(style.getDetailStyle())
				.setHeaderStyle(style.getDejavuSansTitleStyle()).build();

		AbstractColumn columnAchievedTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Achieved")
				.setWidth(new Integer(45)).setStyle(style.getDetailStyle())
				.setHeaderStyle(style.getDejavuSansTitleStyle()).build();

		AbstractColumn columnMissedTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Missed").setWidth(new Integer(45))
				.setStyle(style.getDetailStyle()).setHeaderStyle(style.getDejavuSansTitleStyle()).build();

		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement")
				.setWidth(new Integer(45)).setStyle(style.getDetailStyle())
				.setHeaderStyle(style.getDejavuSansTitleStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnAchievedTicket);
		drb.addColumn(columnMissedTicket);
		drb.addColumn(columnAchievement);
		drb.setUseFullPageWidth(true);
		// drb.setWhenNoDataNoPages();

		return drb.build();

	}

	private DynamicReport createSummarySub2(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("Achievement").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnValue = ColumnBuilder.getNew().setColumnProperty("value", String.class.getName())
				.setTitle("").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnName);
		drb.addColumn(columnValue);
		// drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createSummaryOverallSub(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("").setWidth(new Integer(150)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnServiceDesk = ColumnBuilder.getNew().setColumnProperty("value1", String.class.getName())
				.setTitle("Service Desk").setWidth(new Integer(75)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnSupportAgent = ColumnBuilder.getNew().setColumnProperty("value2", String.class.getName())
				.setTitle("Support Agent").setWidth(new Integer(75)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("OVERALL ACHIEVEMENT (TARGET: 98%)");
		drb.addColumn(columnName);
		drb.addColumn(columnServiceDesk);
		drb.addColumn(columnSupportAgent);
		// drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createSummarySDSub(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("").setWidth(new Integer(150)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnIncident = ColumnBuilder.getNew().setColumnProperty("value1", String.class.getName())
				.setTitle("Incident").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnUserRequest = ColumnBuilder.getNew().setColumnProperty("value2", String.class.getName())
				.setTitle("User Request").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnTotal = ColumnBuilder.getNew().setColumnProperty("value3", String.class.getName())
				.setTitle("Total").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("SERVICE DESK ACHIEVEMENT (TARGET: 98%)");
		drb.addColumn(columnName);
		drb.addColumn(columnIncident);
		drb.addColumn(columnUserRequest);
		drb.addColumn(columnTotal);
		// drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createTeamSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Team").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Ticket Achieved")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Ticket Missed")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle())
				.setTitle("SUPPORT AGENT TEAM ACHIEVEMENT FOR INCIDENT (TARGET: 98%)");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnAchievement);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createAgentSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("division", String.class.getName())
				.setTitle("Team").setWidth(new Integer(70)).setStyle(style.getArialLeftAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAssigned = ColumnBuilder.getNew()
				.setColumnProperty("totalAssigned", Integer.class.getName()).setTitle("Assigned")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalPending = ColumnBuilder.getNew()
				.setColumnProperty("totalPending", Integer.class.getName()).setTitle("Pending")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Achieved")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Missed").setWidth(new Integer(50))
				.setStyle(style.getArialDetailAgentStyle()).setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotal = ColumnBuilder.getNew().setColumnProperty("totalTicket", Integer.class.getName())
				.setTitle("Total").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement(%)")
				.setWidth(new Integer(70)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		Style groupVariables = new Style("groupVariables");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		// groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTeamName).build();

		int margin = 20;
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitleHeight(0);
		drb.setLeftMargin(margin).setRightMargin(margin).setTopMargin(0).setBottomMargin(margin);
		drb.setTitle("VDI SUPPORT AGENT PERFORMANCE (INCIDENT)");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnAgent);
		drb.addColumn(columnTotalAssigned);
		drb.addColumn(columnTotalPending);
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotal);
		drb.addColumn(columnAchievement);
		drb.addGroup(g1);

		return drb.build();

	}

	private DynamicReport createMissedSub(TemplateStylesReport style) {

		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(45)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agent_fullname", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew().setColumnProperty("start_date", String.class.getName())
				.setTitle("Start Date").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnCloseDt = ColumnBuilder.getNew().setColumnProperty("close_date", String.class.getName())
				.setTitle("Close Date").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnStatus = ColumnBuilder.getNew().setColumnProperty("status", String.class.getName())
				.setTitle("Status").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitleHeight(20);
		drb.setTitle("MISSED TICKET");
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnAgent);
		drb.addColumn(columnStartDt);
		drb.addColumn(columnCloseDt);
		drb.addColumn(columnStatus);
		drb.setUseFullPageWidth(true);
		// drb.setWhenNoDataNoPages();

		return drb.build();

	}

	private DynamicReport createPendingSub(TemplateStylesReport style) {
		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(45)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agent_fullname", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew().setColumnProperty("start_date", String.class.getName())
				.setTitle("Start Date").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnPendingReason = ColumnBuilder.getNew()
				.setColumnProperty("pending_reason", String.class.getName()).setTitle("Pending Reason")
				.setWidth(new Integer(200)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitleHeight(20);
		drb.setTitle("PENDING TICKET");
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnAgent);
		drb.addColumn(columnStartDt);
		drb.addColumn(columnPendingReason);
		drb.setUseFullPageWidth(true);
		// drb.setWhenNoDataNoPages();

		return drb.build();
	}

	private DynamicReport createAssignedSub(TemplateStylesReport style) {

		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(45)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agent_fullname", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew().setColumnProperty("start_date", String.class.getName())
				.setTitle("Start Date").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitleHeight(20);
		drb.setTitle("ASSIGNED TICKET");
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnAgent);
		drb.addColumn(columnStartDt);
		drb.setUseFullPageWidth(true);
		// drb.setWhenNoDataNoPages();

		return drb.build();
	}

	private DynamicReport createIncidentListSub(TemplateStylesReport style) {

		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(35)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(200)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStatus = ColumnBuilder.getNew().setColumnProperty("status", String.class.getName())
				.setTitle("Status").setWidth(new Integer(40)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agent_fullname", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnPriority = ColumnBuilder.getNew().setColumnProperty("priority", String.class.getName())
				.setTitle("Priority").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew().setColumnProperty("start_date", String.class.getName())
				.setTitle("Start Date").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnResolutionDt = ColumnBuilder.getNew()
				.setColumnProperty("resolution_date", String.class.getName()).setTitle("Resolution Date")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnDeadline = ColumnBuilder.getNew().setColumnProperty("ttr_deadline", String.class.getName())
				.setTitle("Deadline").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnMissed = ColumnBuilder.getNew().setColumnProperty("ttr_passed", String.class.getName())
				.setTitle("Missed").setWidth(new Integer(30)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("VDI SUPPORT AGENT INCIDENT TICKET");
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(30);
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnStatus);
		drb.addColumn(columnAgent);
		drb.addColumn(columnPriority);
		drb.addColumn(columnStartDt);
		// drb.addColumn(columnResolutionDt);
		drb.addColumn(columnDeadline);
		drb.addColumn(columnMissed);
		drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createServicedeskPersonSub(TemplateStylesReport style) {

		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent Name").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotal = ColumnBuilder.getNew().setColumnProperty("totalTicket", Integer.class.getName())
				.setTitle("Ticket Total").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnMissed = ColumnBuilder.getNew().setColumnProperty("totalMissed", Integer.class.getName())
				.setTitle("Ticket Missed").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Ticket Achieved")
				.setWidth(new Integer(60)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getArialHeaderSimpleStyle()).setTitle("VDI Service Desk Agent Performance");
		drb.addColumn(columnAgent);
		drb.addColumn(columnAchieved);
		drb.addColumn(columnMissed);
		drb.addColumn(columnTotal);
		drb.addColumn(columnAchievement);
		drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createUserRequestAgentSub(TemplateStylesReport style) {
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Achieved")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Missed").setWidth(new Integer(50))
				.setStyle(style.getArialDetailAgentStyle()).setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotal = ColumnBuilder.getNew().setColumnProperty("totalTicket", Integer.class.getName())
				.setTitle("Total").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement")
				.setWidth(new Integer(70)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		int margin = 20;
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(20);
		drb.setLeftMargin(margin).setRightMargin(margin).setTopMargin(20).setBottomMargin(margin);
		drb.setTitle("VDI USER REQUEST AGENT PERFORMANCE");
		drb.addColumn(columnAgent);
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotal);
		drb.addColumn(columnAchievement);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createServicedeskAgentSub(TemplateStylesReport style) {

		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Achieved")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Missed").setWidth(new Integer(50))
				.setStyle(style.getArialDetailAgentStyle()).setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotal = ColumnBuilder.getNew().setColumnProperty("totalTicket", Integer.class.getName())
				.setTitle("Total").setWidth(new Integer(50)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement(%)")
				.setWidth(new Integer(70)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		int margin = 20;
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitleHeight(20);
		drb.setLeftMargin(margin).setRightMargin(margin).setTopMargin(20).setBottomMargin(5);
		drb.setTitle("VDI SERVICE DESK AGENT PERFORMANCE (INCIDENT AND USER REQUEST)");
		drb.addColumn(columnAgent);
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotal);
		drb.addColumn(columnAchievement);
		drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createServicedeskIncidentSub(TemplateStylesReport style) {

		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("incident_ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(35)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("incident_title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(200)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("incident_status", String.class.getName()).setTitle("Status")
				.setWidth(new Integer(40)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("scalar_user", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnPriority = ColumnBuilder.getNew()
				.setColumnProperty("incident_priority", String.class.getName()).setTitle("Priority")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew()
				.setColumnProperty("incident_startdate", String.class.getName()).setTitle("Start Date")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn ttrDeadline = ColumnBuilder.getNew()
				.setColumnProperty("incident_ttoDeadline", String.class.getName()).setTitle("Deadline")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn ttoOver = ColumnBuilder.getNew()
				.setColumnProperty("incident_slattopassed", String.class.getName()).setTitle("Missed")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("SERVICE DESK INCIDENT TICKET");
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(30);
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnStatus);
		drb.addColumn(columnAgent);
		drb.addColumn(columnPriority);
		drb.addColumn(columnStartDt);
		// drb.addColumn(ttrDeadline);
		drb.addColumn(ttoOver);
		drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createUserRequestIncidentSub(TemplateStylesReport style) {

		AbstractColumn columnRef = ColumnBuilder.getNew()
				.setColumnProperty("scalar_urequestref", String.class.getName()).setTitle("Ref")
				.setWidth(new Integer(40)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("urequest_title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(200)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("urequest_status", String.class.getName()).setTitle("Status")
				.setWidth(new Integer(40)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("scalar_user", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnPriority = ColumnBuilder.getNew()
				.setColumnProperty("urequest_priority", String.class.getName()).setTitle("Priority")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew()
				.setColumnProperty("urequest_startdate", String.class.getName()).setTitle("Start Date")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnMissed = ColumnBuilder.getNew()
				.setColumnProperty("urequest_slattopassed", String.class.getName()).setTitle("Missed")
				.setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("USER REQUEST TICKET");
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(30);
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnStatus);
		drb.addColumn(columnAgent);
		drb.addColumn(columnPriority);
		drb.addColumn(columnStartDt);
		drb.addColumn(columnMissed);
		drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createAppTeamSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Team").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Ticket Achieved")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Ticket Missed")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnContribution = ColumnBuilder.getNew()
				.setColumnProperty("contribution", Float.class.getName()).setTitle("Contribution(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Achievement per Section (target: 98%)");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnAchievement);
		drb.addColumn(columnContribution);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createAppTeamAgentSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Team").setWidth(new Integer(100)).setStyle(style.getArialLeftAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgentName = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(100)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew()
				.setColumnProperty("totalAchieved", Integer.class.getName()).setTitle("Ticket Achieved")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew()
				.setColumnProperty("totalMissed", Integer.class.getName()).setTitle("Ticket Missed")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew()
				.setColumnProperty("achievement", Float.class.getName()).setTitle("Achievement(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnContribution = ColumnBuilder.getNew()
				.setColumnProperty("contribution", Float.class.getName()).setTitle("Contribution(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTeamContribution = ColumnBuilder.getNew()
				.setColumnProperty("teamContribution", Float.class.getName()).setTitle("Team Contribution(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		Style groupVariables = new Style("groupVariablesApp");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		// groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTeamName).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Achievement per Dedicated Agent (target: 98%)");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnAgentName);
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnAchievement);
		drb.addColumn(columnContribution);
		drb.addColumn(columnTeamContribution);
		drb.addGroup(g1);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createProblemSummarySub(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("Monthly Problem Overview").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnValue = ColumnBuilder.getNew().setColumnProperty("value", String.class.getName())
				.setTitle("").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnName);
		drb.addColumn(columnValue);
		// drb.setUseFullPageWidth(true);

		return drb.build();

	}
	
	private DynamicReport createAllProblemSummarySub(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("All Problem Overview").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnValue = ColumnBuilder.getNew().setColumnProperty("value", String.class.getName())
				.setTitle("").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Problem Summary (Year To Date)");
		drb.addColumn(columnName);
		drb.addColumn(columnValue);
		// drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createChangeSummarySub(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("Change Overview").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnValue = ColumnBuilder.getNew().setColumnProperty("value", String.class.getName())
				.setTitle("").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnName);
		drb.addColumn(columnValue);
		// drb.setUseFullPageWidth(true);

		return drb.build();

	}
	
	private DynamicReport createAllOpenChangeSummarySub(TemplateStylesReport style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("All Change Overview").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		AbstractColumn columnValue = ColumnBuilder.getNew().setColumnProperty("value", String.class.getName())
				.setTitle("").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Change Summary (Year To Date)");
		drb.addColumn(columnName);
		drb.addColumn(columnValue);
		// drb.setUseFullPageWidth(true);

		return drb.build();

	}
	
	private DynamicReport createAllOpenedChangeByAgentSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Section").setWidth(new Integer(70)).setStyle(style.getArialLeftAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgentName = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(100)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		Style groupVariables = new Style("groupVariablesApp1");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		// groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTeamName).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Open Change By Agent");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnAgentName);
		drb.addColumn(columnTotalTicket);
		drb.addGroup(g1);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createDedicatedTeamSummarySub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Section").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnContribution = ColumnBuilder.getNew()
				.setColumnProperty("contribution", Float.class.getName()).setTitle("Contribution(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Contribution per Section");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnContribution);
		drb.setUseFullPageWidth(true);

		return drb.build();

	}

	private DynamicReport createDedicatedAgentSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Section").setWidth(new Integer(100)).setStyle(style.getArialLeftAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgentName = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(100)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnContribution = ColumnBuilder.getNew()
				.setColumnProperty("contribution", Float.class.getName()).setTitle("Contribution(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTeamContribution = ColumnBuilder.getNew()
				.setColumnProperty("teamContribution", Float.class.getName()).setTitle("Team Contribution(%)")
				.setWidth(new Integer(80)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		Style groupVariables = new Style("groupVariablesApp");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		// groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTeamName).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Contribution per Dedicated Agent");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnAgentName);
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnContribution);
		drb.addColumn(columnTeamContribution);
		drb.addGroup(g1);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}
	
	private DynamicReport createAllOpenedProblemByAgentSub(TemplateStylesReport style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Section").setWidth(new Integer(70)).setStyle(style.getArialLeftAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAgentName = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(100)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnPriority = ColumnBuilder.getNew().setColumnProperty("category", String.class.getName())
				.setTitle("Priority").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew()
				.setColumnProperty("totalTicket", Integer.class.getName()).setTitle("Ticket Total")
				.setWidth(new Integer(52)).setStyle(style.getArialDetailAgentStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		Style groupVariables = new Style("groupVariablesApp1");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		// groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTeamName).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getSummaryArialTitleStyle()).setTitle("Open Problem By Agent");
		drb.addColumn(columnTeamName);
		drb.addColumn(columnAgentName);
		drb.addColumn(columnPriority);
		drb.addColumn(columnTotalTicket);
		drb.addGroup(g1);
		drb.setUseFullPageWidth(true);

		return drb.build();
	}

	private DynamicReport createAllOpenProblemList(TemplateStylesReport style) {
		

		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(35)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(200)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStatus = ColumnBuilder.getNew().setColumnProperty("status", String.class.getName())
				.setTitle("Status").setWidth(new Integer(40)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agent_id_friendlyname", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnCaller = ColumnBuilder.getNew().setColumnProperty("caller_name", String.class.getName())
				.setTitle("Caller").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnPriority = ColumnBuilder.getNew().setColumnProperty("priority", String.class.getName())
				.setTitle("Priority").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnStartDt = ColumnBuilder.getNew().setColumnProperty("start_date", String.class.getName())
				.setTitle("Start Date").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("VDI ALL OPEN PROBLEM TICKET");
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(30);
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnPriority);		
		drb.addColumn(columnAgent);
		drb.addColumn(columnCaller);	
		drb.addColumn(columnStartDt);
		drb.addColumn(columnStatus);
		drb.setUseFullPageWidth(true);

		return drb.build();
		
	}
	
private DynamicReport createAllOpenChangeList(TemplateStylesReport style) {
		

		AbstractColumn columnRef = ColumnBuilder.getNew().setColumnProperty("ref", String.class.getName())
				.setTitle("Ref").setWidth(new Integer(35)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnTitle = ColumnBuilder.getNew().setColumnProperty("title", String.class.getName())
				.setTitle("Title").setWidth(new Integer(200)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agent_id_friendlyname", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
		AbstractColumn columnCaller = ColumnBuilder.getNew().setColumnProperty("caller_name", String.class.getName())
				.setTitle("Caller").setWidth(new Integer(150)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();
//		AbstractColumn columnStartDt = ColumnBuilder.getNew().setColumnProperty("start_date", String.class.getName())
//				.setTitle("Start Date").setWidth(new Integer(50)).setStyle(style.getArialDetailIncidentListStyle())
//				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("VDI ALL OPEN CHANGE TICKET");
		drb.setTitleStyle(style.getArialTitleAgentStyle()).setTitleHeight(30);
		drb.addColumn(columnRef);
		drb.addColumn(columnTitle);
		drb.addColumn(columnAgent);
		drb.addColumn(columnCaller);	
		drb.setUseFullPageWidth(true);

		return drb.build();
		
	}

	private DynamicReport createNoteSub(TemplateStylesReport style) {

		AbstractColumn columnNote = ColumnBuilder.getNew().setColumnProperty("notes", String.class.getName())
				.setTitle("Notes: ").setWidth(new Integer(100)).setStyle(style.getArialDetailIncidentListStyle())
				.setHeaderStyle(style.getArialHeaderIncidentListStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnNote);

		return drb.build();

	}

	public DynamicReportBuilder getMaster() {
		return master;
	}

	public DynamicReport getSummarySub() {
		return summarySub;
	}

	public DynamicReport getSummarySub2() {
		return summarySub2;
	}

	public DynamicReport getTeamSub() {
		return teamSub;
	}

	public DynamicReport getAgentSub() {
		return agentSub;
	}

	public DynamicReport getMissedSub() {
		return missedSub;
	}

	public DynamicReport getPendingSub() {
		return pendingSub;
	}

	public DynamicReport getAssignedSub() {
		return assignedSub;
	}

	public DynamicReport getIncidentListSub() {
		return incidentListSub;
	}

	public DynamicReport getServiceDeskPersonSub() {
		return serviceDeskPersonSub;
	}

	public DynamicReport getServiceDeskAgentSub() {
		return serviceDeskAgentSub;
	}

	public DynamicReport getServiceDeskIncidentSub() {
		return serviceDeskIncidentSub;
	}

	public DynamicReport getUserRequestIncidentSub() {
		return userRequestIncidentSub;
	}

	public DynamicReport getUserRequestAgentSub() {
		return userRequestAgentSub;
	}

	public DynamicReport getSummaryOverallSub() {
		return summaryOverallSub;
	}

	public DynamicReport getSummarySDSub() {
		return summarySDSub;
	}

	public DynamicReport getAppTeamSub() {
		return appTeamSub;
	}

	public DynamicReport getAppTeamAgentSub() {
		return appTeamAgentSub;
	}

	public DynamicReport getProblemSummarySub() {
		return problemSummarySub;
	}

	public DynamicReport getChangeSummarySub() {
		return changeSummarySub;
	}

	public DynamicReport getDedicatedTeamSub() {
		return dedicatedTeamSub;
	}

	public DynamicReport getDedicatedAgentSub() {
		return dedicatedAgentSub;
	}

	public DynamicReport getAllProblemSummarySub() {
		return allProblemSummarySub;
	}

	public DynamicReport getAllProblemSummarybyAgentSub() {
		return allProblemSummarybyAgentSub;
	}

	public DynamicReport getAllOpenProblemListSub() {
		return allOpenProblemListSub;
	}

	public void setAllOpenProblemListSub(DynamicReport allOpenProblemListSub) {
		this.allOpenProblemListSub = allOpenProblemListSub;
	}

	public DynamicReport getAllChangeSummarySub() {
		return allChangeSummarySub;
	}

	public DynamicReport getAllChangeSummarybyAgentSub() {
		return allChangeSummarybyAgentSub;
	}

	public DynamicReport getAllOpenChangeListSub() {
		return allOpenChangeListSub;
	}

}
