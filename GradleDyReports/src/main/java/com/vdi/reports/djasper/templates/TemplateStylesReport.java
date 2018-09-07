package com.vdi.reports.djasper.templates;

import java.awt.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

@Configuration
public class TemplateStylesReport {

	private Style dejavuSansTitleStyle;

	private Style arialHeaderStyle;
	private Style arialHeaderVariableStyle;

	private Style detailStyle;

	private Style groupVariableStyle;

	private Style importSTyle;

	private Style oddRowStyle;

	// for summary style
	private Style arialHeaderSummaryStyle;
	private Style arialDetailSummaryStyle;
	private Style arialHeaderSimpleStyle;

	// for agent
	private Style arialTitleAgentStyle;
	private Style arialLeftAgentStyle;
	private Style arialDetailAgentStyle;
	private Style arialDetailGreenAgentStyle;

	// for incidentList
	private Style arialHeaderIncidentListStyle;
	private Style arialDetailIncidentListStyle;

	//for summary sub
	private Style summaryArialTitleStyle;
	
	// for note
	private Style noteHeaderStyle;
	private Style noteDetailStyle;

	public TemplateStylesReport() {
	}

	@Autowired
	public TemplateStylesReport(TemplateFontsReport font) {

		this.dejavuSansTitleStyle = createDejavuSansTitleStyle(font);

		this.arialHeaderStyle = createArialHeaderStyle(font);
		this.arialHeaderVariableStyle = createArialHeaderVariableStyle(font);

		this.detailStyle = createDetailStyle(font);

		this.groupVariableStyle = createGroupVariableStyle(font);

		this.importSTyle = createImportStyle(font);

		this.oddRowStyle = createOddRowStyle(font);

		// for summary
		this.arialHeaderSummaryStyle = createArialHeaderSummaryStyle(font);
		this.arialDetailSummaryStyle = createArialDetailSummaryStyle(font);
		this.arialHeaderSimpleStyle = createArialHeaderSimpleStyle(font);

		// for agent
		this.arialTitleAgentStyle = createArialTitleAgentStyle(font);
		this.arialDetailAgentStyle = createArialDetailAgentStyle(font);
		this.arialLeftAgentStyle = createArialLeftAgentStyle(font);
		this.arialDetailGreenAgentStyle = createArialDetailGreenAgentStyle(font);

		// for incidentlist
		this.arialHeaderIncidentListStyle = createArialHeaderIncidentStyle(font);
		this.arialDetailIncidentListStyle = createArialDetailIncidentStyle(font);

		//for summary sub
		this.summaryArialTitleStyle = createSummaryArialTitleStyle(font);
		
		// for note
		this.noteHeaderStyle = createHeaderNoteStyle(font);
		this.noteDetailStyle = createDetailNoteStyle(font);

	}

	private Style createDejavuSansTitleStyle(TemplateFontsReport font) {
		Font myfont = font.getDejavuSansBoldFont();
		myfont.setFontSize(18);

		Style dejavuSansTitleStyle = new Style("dejavuSansTitleStyle");
		dejavuSansTitleStyle.setFont(myfont);

		return dejavuSansTitleStyle;
	}

	private Style createArialHeaderStyle(TemplateFontsReport font) {

		Style headerStyle = new Style("headerStyle");
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_1_POINT());
		headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		return headerStyle;
	}

	private Style createArialHeaderVariableStyle(TemplateFontsReport font) {

		Style headerVariables = new Style("headerVariables");
		headerVariables.setFont(Font.ARIAL_BIG_BOLD);
		headerVariables.setBorderBottom(Border.THIN());
		headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		headerVariables.setVerticalAlign(VerticalAlign.TOP);
		headerVariables.setStretchWithOverflow(true);

		return headerVariables;
	}

	private Style createDetailStyle(TemplateFontsReport font) {
		Style detailStyle = new Style("detailStyle");

		return detailStyle;
	}

	private Style createGroupVariableStyle(TemplateFontsReport font) {

		Style groupVariables = new Style("groupVariables");
		groupVariables.setFont(Font.ARIAL_MEDIUM_BOLD);
		groupVariables.setTextColor(Color.BLUE);
		groupVariables.setBorderBottom(Border.THIN());
		groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		return groupVariables;
	}

	private Style createImportStyle(TemplateFontsReport font) {

		Style importeStyle = new Style();
		importeStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		return importeStyle;
	}

	private Style createOddRowStyle(TemplateFontsReport font) {
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER());
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		return oddRowStyle;
	}

	// style for Summary Reports
	private Style createArialHeaderSummaryStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(7);
		myFont.setBold(false);
		
		Style headerStyle = new Style("arialHeaderSummaryStyle");
		headerStyle.setFont(myFont);
		headerStyle.setBorderTop(Border.PEN_1_POINT());
		headerStyle.setBorderBottom(Border.PEN_1_POINT());
		headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		return headerStyle;
	}

	private Style createArialDetailSummaryStyle(TemplateFontsReport font) {

		Style headerStyle = new Style("arialDetailSummaryStyle");
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_1_POINT());
		// headerStyle.setBackgroundColor(Color.gray);
		// headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		// headerStyle.setTransparency(Transparency.OPAQUE);

		return headerStyle;

	}

	private Style createArialHeaderSimpleStyle(TemplateFontsReport font) {

		Style headerStyle = new Style("arialHeaderSimpleStyle");
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorderTop(Border.NO_BORDER());
		headerStyle.setBorderBottom(Border.NO_BORDER());
		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		return headerStyle;

	}

	// for agent sub report
	private Style createArialTitleAgentStyle(TemplateFontsReport font) {
		Font myfont = font.getArialBoldFont();
		myfont.setFontSize(14);

		Style style = new Style("arialTitleAgentStyle");
		style.setFont(myfont);
		style.setHorizontalAlign(HorizontalAlign.CENTER);

		return style;
	}

	private Style createArialLeftAgentStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(9);
		myFont.setBold(true);

		Style style = new Style("leftAgentStyle");
		style.setFont(myFont);
		style.setHorizontalAlign(HorizontalAlign.LEFT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);

		return style;

	}

	private Style createArialDetailAgentStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(7);
		myFont.setBold(false);

		Style style = new Style("detailAgentStyle");
		style.setFont(myFont);
		style.setBorderBottom(Border.PEN_1_POINT());
		style.setHorizontalAlign(HorizontalAlign.LEFT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);

		return style;

	}

	private Style createArialDetailGreenAgentStyle(TemplateFontsReport font) {

		Color color = new Color(0, 122, 22);

		Font myFont = font.getArialFont();
		myFont.setFontSize(9);
		myFont.setBold(true);

		Style style = new Style("detailAgentStyle");
		style.setFont(myFont);
		style.setTextColor(color);
		style.setBorderBottom(Border.PEN_1_POINT());
		style.setHorizontalAlign(HorizontalAlign.LEFT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);

		return style;

	}

	// for incident list subreport
	private Style createArialHeaderIncidentStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(7);
		myFont.setBold(true);

		Style headerStyle = new Style("headerIncidentListStyle");
		headerStyle.setFont(myFont);
		headerStyle.setBorderTop(Border.PEN_1_POINT());
		headerStyle.setBorderBottom(Border.PEN_1_POINT());
		headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.white);
		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		return headerStyle;
	}

	private Style createArialDetailIncidentStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(7);
		myFont.setBold(false);

		Style style = new Style("detailAgentStyle");
		style.setFont(myFont);
		style.setBorderBottom(Border.PEN_1_POINT());
		style.setHorizontalAlign(HorizontalAlign.LEFT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);

		return style;

	}

	private Style createHeaderNoteStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(8);
		myFont.setBold(true);

		Style headerStyle = new Style("headerNotetStyle");
		headerStyle.setFont(myFont);
		headerStyle.setBorderTop(Border.NO_BORDER());
		headerStyle.setBorderBottom(Border.NO_BORDER());
		// headerStyle.setBackgroundColor(Color.gray);
		headerStyle.setTextColor(Color.black);
		headerStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		return headerStyle;

	}

	private Style createDetailNoteStyle(TemplateFontsReport font) {

		Font myFont = font.getArialFont();
		myFont.setFontSize(8);
		myFont.setBold(false);

		Style style = new Style("detailNoteStyle");
		style.setFont(myFont);
		style.setHorizontalAlign(HorizontalAlign.LEFT);
		style.setVerticalAlign(VerticalAlign.MIDDLE);

		return style;

	}

	// summary sub report
	private Style createSummaryArialTitleStyle(TemplateFontsReport font) {
		Font myfont = font.getArialBoldFont();
		myfont.setFontSize(10);

		Style style = new Style("summaryArialTitleStyle");
		style.setFont(myfont);
		style.setHorizontalAlign(HorizontalAlign.LEFT);

		return style;
	}

	public Style getDejavuSansTitleStyle() {
		return dejavuSansTitleStyle;
	}

	public Style getOddRowStyle() {
		return oddRowStyle;
	}

	public Style getArialHeaderStyle() {
		return arialHeaderStyle;
	}

	public Style getArialHeaderVariableStyle() {
		return arialHeaderVariableStyle;
	}

	public Style getGroupVariableStyle() {
		return groupVariableStyle;
	}

	public Style getImportSTyle() {
		return importSTyle;
	}

	public Style getDetailStyle() {
		return detailStyle;
	}

	public Style getArialHeaderSummaryStyle() {
		return arialHeaderSummaryStyle;
	}

	public Style getArialTitleAgentStyle() {
		return arialTitleAgentStyle;
	}

	public Style getArialDetailSummaryStyle() {
		return arialDetailSummaryStyle;
	}

	public Style getArialDetailGreenAgentStyle() {
		return arialDetailGreenAgentStyle;
	}

	public Style getArialDetailAgentStyle() {
		return arialDetailAgentStyle;
	}

	public Style getArialLeftAgentStyle() {
		return arialLeftAgentStyle;
	}

	public Style getArialDetailIncidentListStyle() {
		return arialDetailIncidentListStyle;
	}

	public Style getArialHeaderIncidentListStyle() {
		return arialHeaderIncidentListStyle;
	}

	public Style getNoteHeaderStyle() {
		return noteHeaderStyle;
	}

	public Style getNoteDetailStyle() {
		return noteDetailStyle;
	}

	public Style getArialHeaderSimpleStyle() {
		return arialHeaderSimpleStyle;
	}

	public Style getSummaryArialTitleStyle() {
		return summaryArialTitleStyle;
	}

}
