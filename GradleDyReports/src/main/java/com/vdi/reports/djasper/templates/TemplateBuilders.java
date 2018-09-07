package com.vdi.reports.djasper.templates;

import java.awt.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.vdi.tools.TimeStatic;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

@Configuration
public class TemplateBuilders {
	//test versioning

	private DynamicReportBuilder sAMaster;
	private DynamicReportBuilder master;

	//for support agent report
	private DynamicReport allSub;
	private DynamicReport allSub2;
	private DynamicReport teamSub;
	private DynamicReport agentSub;

	private DynamicReport sAAllSub;
	private DynamicReport saTeamSub;
	private DynamicReport saAgentSub;
	private DynamicReport saMissedtSub;
	private DynamicReport saPendingSub;
	private DynamicReport saAssignedSub;
	private DynamicReport saIncidentSub;

	public TemplateBuilders() {
	}

	@Autowired
	public TemplateBuilders(TemplateStyles style) throws ColumnBuilderException, ClassNotFoundException {

		this.sAMaster = createSAMaster(style);
		this.master = createMaster(style);

		this.allSub = createAllSub(style);
		this.allSub2 = createAllSub2(style);
		this.teamSub = createTeamSub(style);
		this.agentSub = createAgentSub(style);

		this.sAAllSub = createSAAllSub(style);
		this.saTeamSub = createSATeamSub(style);
		this.saAgentSub = createSAAgentSub(style);
		this.saMissedtSub = createSAMissedSub(style);
		this.saPendingSub = createSAPendingSub(style);
		this.saAssignedSub = createSAAssignedSub(style);
		this.saIncidentSub = createSAIncidentSub(style);

	}

	public DynamicReportBuilder createMaster(TemplateStyles style) {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
		drb.setTitleStyle(style.getDejavuSansTitleStyle())
				.setTitle("VDI SUPPORT AGENT PERFORMANCE BASED ON iTop JULY 2018").setTitleHeight(60)
				.setSubtitle("July 2018").setSubtitleHeight(15)
//				.setDetailHeight(15)
				.setLeftMargin(margin).setRightMargin(margin).setTopMargin(20)
				.setBottomMargin(margin).setPrintBackgroundOnOddRows(true)
				// .setGrandTotalLegend("Grand Total")
				// .setGrandTotalLegendStyle(headerVariables)
				.setOddRowBackgroundStyle(style.getOddRowStyle())
				.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT);
		return drb;
	}

	public DynamicReport createAllSub(TemplateStyles style) {

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

		return drb.build();

	}

	public DynamicReport createAllSub2(TemplateStyles style) {

		AbstractColumn columnName = ColumnBuilder.getNew().setColumnProperty("name", String.class.getName())
				.setTitle("Achievement").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle())
				.build();

		AbstractColumn columnValue = ColumnBuilder.getNew().setColumnProperty("value", String.class.getName())
				.setTitle("").setWidth(new Integer(100)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnName);
		drb.addColumn(columnValue);
//		drb.setUseFullPageWidth(true);

		return drb.build();
	}
	
	public DynamicReport createTeamSub(TemplateStyles style) {

		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("teamName", String.class.getName())
				.setTitle("Team").setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle())
				.build();
		AbstractColumn columnTotalTicket = ColumnBuilder.getNew().setColumnProperty("totalTicket", Integer.class.getName())
				.setTitle("Total Ticket").setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew().setColumnProperty("totalAchieved", Integer.class.getName())
				.setTitle("Achieved Ticket").setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew().setColumnProperty("totalMissed", Integer.class.getName())
				.setTitle("Missed Ticket").setWidth(new Integer(52)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew().setColumnProperty("achievement", Float.class.getName())
				.setTitle("Achievement").setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.addColumn(columnTeamName);		
		drb.addColumn(columnTotalAchieved);
		drb.addColumn(columnTotalMissed);
		drb.addColumn(columnTotalTicket);
		drb.addColumn(columnAchievement);
//		drb.setUseFullPageWidth(true);
		
		return drb.build();		
	}

	public DynamicReport createAgentSub(TemplateStyles style) {
		
		AbstractColumn columnTeamName = ColumnBuilder.getNew().setColumnProperty("division", String.class.getName())
				.setTitle("Team").setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle())
				.build();
		AbstractColumn columnAgent = ColumnBuilder.getNew().setColumnProperty("agentName", String.class.getName())
				.setTitle("Agent").setWidth(new Integer(150)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAssigned = ColumnBuilder.getNew().setColumnProperty("totalAssigned", Integer.class.getName())
				.setTitle("Assigned").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalPending = ColumnBuilder.getNew().setColumnProperty("totalPending", Integer.class.getName())
				.setTitle("Pending").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalAchieved = ColumnBuilder.getNew().setColumnProperty("totalAchieved", Integer.class.getName())
				.setTitle("Achieved").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotalMissed = ColumnBuilder.getNew().setColumnProperty("totalMissed", Integer.class.getName())
				.setTitle("Missed").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnTotal = ColumnBuilder.getNew().setColumnProperty("totalTicket", Integer.class.getName())
				.setTitle("Total").setWidth(new Integer(50)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();
		AbstractColumn columnAchievement = ColumnBuilder.getNew().setColumnProperty("achievement", Float.class.getName())
				.setTitle("Achievement").setWidth(new Integer(80)).setStyle(style.getArialDetailSummaryStyle())
				.setHeaderStyle(style.getArialHeaderSummaryStyle()).build();

		Style groupVariables = new Style("groupVariables");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);
		
		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnTeamName).build();
		
		
		DynamicReportBuilder drb = new DynamicReportBuilder();
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

	public DynamicReportBuilder createSAMaster(TemplateStyles style)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitleStyle(style.getStandardTitleStyle()).setTitle("VDI SUPPORT AGENT PERFORMANCE BASED ON iTop ")
				.setSubtitleStyle(style.getStandardSubTitleStyle())
				.setSubtitle("WEEK " + TimeStatic.currentWeekMonth + " " + TimeStatic.currentYear).setLeftMargin(30)
				.setRightMargin(30).setTopMargin(30).setBottomMargin(30)
				.setPageSizeAndOrientation(Page.Page_A4_Landscape()).setUseFullPageWidth(true)// .setWhenNoDataAllSectionNoDetail()
				.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_LEFT);

		return drb;
	}

	public DynamicReport createSAAllSub(TemplateStyles style) throws ColumnBuilderException, ClassNotFoundException {

		FastReportBuilder frb = new FastReportBuilder();
		DynamicReport drSub = frb
				.addColumn("Ticket Total", "totalTicket", Integer.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Ticket Achieved", "totalAchieved", Integer.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Ticket Missed", "totalMissed", Integer.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Achievement", "achievement", Float.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getStandardOddBgStyle())
				.setUseFullPageWidth(true).setWhenNoDataAllSectionNoDetail().build();

		return drSub;
	}

	public DynamicReport createSATeamSub(TemplateStyles style) {

		FastReportBuilder frb = new FastReportBuilder();

		DynamicReport drSub = frb
				.addColumn("Team", "teamName", String.class, 80, style.getStdLeftPerfTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Ticket Total", "totalTicket", Integer.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Ticket Achieved", "totalAchieved", Integer.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Ticket Missed", "totalMissed", Integer.class, 45, style.getStandardDetailRedTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Achievement", "achievement", Float.class, 45, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getStandardOddBgStyle())
				.setUseFullPageWidth(true).build();

		return drSub;
	}

	public DynamicReport createSAAgentSub(TemplateStyles style) {

		FastReportBuilder rb = new FastReportBuilder();

		DynamicReport drSub = rb
				.addColumn("Division", "division", String.class, 45, style.getStdLeftPerfTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Agent", "agentName", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Assigned", "totalAssigned", Integer.class, 50, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Pending", "totalPending", Integer.class, 50, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Achieved", "totalAchieved", Integer.class, 50, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Missed", "totalMissed", Integer.class, 50, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Ticket Total", "totalTicket", Integer.class, 40, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Achievement", "achievement", Float.class, 50, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				// .addColumn("Remarks", "remarks", String.class, 50,
				// style.getStandardDetailTextStyle(), style.getStandardHeaderStyle())
				.addGroups(1)
				// .setTitleStyle(style.getStandardTitleStyle()).setTitle("VDI Support Agent
				// Performance")
				.setTitle("VDI SUPPORT AGENT PERFORMANCE").setPrintBackgroundOnOddRows(true)
				.setOddRowBackgroundStyle(style.getStandardOddBgStyle()).setUseFullPageWidth(true).build();

		return drSub;
	}

	public DynamicReport createSAMissedSub(TemplateStyles style) {

		FastReportBuilder rb = new FastReportBuilder();

		DynamicReport drSub = rb
				.addColumn("Ticket Number", "ref", String.class, 45, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Title", "title", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Agent", "agent_fullname", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Start Date", "start_date", String.class, 50, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Closed Date", "close_date", String.class, 50, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Status", "status", String.class, 50, style.getStandardDetailTextStyle(),
						style.getStandardHeaderStyle())
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getStandardOddBgStyle())
				.setUseFullPageWidth(true).setTitle("MISSED TICKET").build();

		return drSub;

	}

	public DynamicReport createSAPendingSub(TemplateStyles style) {

		FastReportBuilder rb = new FastReportBuilder();

		DynamicReport drSub = rb
				.addColumn("Ticket Number", "ref", String.class, 45, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Title", "title", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Agent", "agent_fullname", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Start Date", "start_date", String.class, 50, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Pending Reason", "pending_reason", String.class, 50, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getStandardOddBgStyle())
				.setUseFullPageWidth(true).setTitle("PENDING TICKET").build();

		return drSub;

	}

	public DynamicReport createSAAssignedSub(TemplateStyles style) {

		FastReportBuilder rb = new FastReportBuilder();

		DynamicReport drSub = rb
				.addColumn("Ticket Number", "ref", String.class, 45, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Title", "title", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Agent", "agent_fullname", String.class, 150, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Start Date", "start_date", String.class, 50, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getStandardOddBgStyle())
				.setUseFullPageWidth(true).setTitle("ASSIGNED TICKET").build();

		return drSub;

	}

	public DynamicReport createSAIncidentSub(TemplateStyles style) {

		FastReportBuilder rb = new FastReportBuilder();

		DynamicReport drSub = rb
				.addColumn("Ticket Number", "ref", String.class, 45, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Title", "title", String.class, 100, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Status", "status", String.class, 30, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Agent", "agent_fullname", String.class, 100, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Start Date", "start_date", String.class, 30, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Start Time", "start_time", String.class, 30, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Resolution Date", "resolution_date", String.class, 30,
						style.getStandardDetailLeftTextStyle(), style.getStandardHeaderStyle())
				.addColumn("Priority", "priority", String.class, 30, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.addColumn("Organization", "person_org_name", String.class, 80, style.getStandardDetailLeftTextStyle(),
						style.getStandardHeaderStyle())
				.setPrintBackgroundOnOddRows(true).setOddRowBackgroundStyle(style.getStandardOddBgStyle())
				.setUseFullPageWidth(true).setTitle("INCIDENT LIST").build();

		return drSub;
	}

	public DynamicReportBuilder getSAMaster() {
		return sAMaster;
	}

	public DynamicReport getSATeamSub() {
		return saTeamSub;
	}

	public DynamicReport getSAAgentSub() {
		return saAgentSub;
	}

	public DynamicReport getSAMissedtSub() {
		return saMissedtSub;
	}

	public DynamicReport getSAPendingSub() {
		return saPendingSub;
	}

	public DynamicReport getSAAssignedSub() {
		return saAssignedSub;
	}

	public DynamicReport getSAAllSub() {
		return sAAllSub;
	}

	public DynamicReport getSAIncidentSub() {
		return saIncidentSub;
	}

	public DynamicReportBuilder getMaster() {
		return master;
	}

	public DynamicReport getAllSub() {
		return allSub;
	}

	public DynamicReport getAllSub2() {
		return allSub2;
	}

	public DynamicReport getTeamSub() {
		return teamSub;
	}

	public DynamicReport getAgentSub() {
		return agentSub;
	}

}
