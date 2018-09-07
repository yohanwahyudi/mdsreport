package com.vdi.reports.djasper.templates;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class DJasperTemplates {

	public static final Style standardFont;
	public static final Style standardBoldFont;
	public static final Style standardItalicFont;
	
	public static final Style titleStyle;
	public static final Style subTitleStyle;
	
	public static final Style headerStyle;
	public static final Style detailTextStyle;
	public static final Style oddBgStyle;

	static {

		standardFont = createStandardFont();
		standardBoldFont = createStandardBoldFont();
		standardItalicFont = createStandardItalicFont();
		
		titleStyle = createTitleStyle();
		subTitleStyle = createSubtitleStyle();
		
		headerStyle = createHeaderStyle();
		detailTextStyle = createDetailTextStyle();
		oddBgStyle = createOddBgStyle();

	}

	private static Style createStandardFont() {

		Font font = new Font();
		font.setFontName("Roboto Black");
		font.setPdfFontName("/fonts/Roboto-Black.ttf");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);
		font.setFontSize(8);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	private static Style createStandardBoldFont() {
		Font font = createStandardFont().getFont();
		font.setBold(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	private static Style createStandardItalicFont() {
		Font font = createStandardFont().getFont();
		font.setItalic(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	private static Style createTitleStyle() {
		
		Font font = createStandardBoldFont().getFont();
		font.setFontSize(12);
		
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.CENTER);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}
	
	private static Style createSubtitleStyle() {
		Font font = createStandardFont().getFont();
		font.setFontSize(6);
		
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.LEFT);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}
	
	private static Style createHeaderStyle() {
		Font font = createStandardBoldFont().getFont();
		font.setFontSize(8);
		
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
		Font font = createStandardFont().getFont();
		font.setFontSize(7);
		
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.LEFT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		return sb.build();
	}
	
	private static Style createOddBgStyle() {
		Font font = createStandardBoldFont().getFont();
		font.setFontSize(8);
		
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

}
