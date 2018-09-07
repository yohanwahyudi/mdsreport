package com.vdi.reports.djasper.templates;

import java.awt.Color;

import org.springframework.context.annotation.Configuration;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;

@Configuration
public class TemplateFonts {

	private Font dejavuSansFont;
	private Font dejavuSansBoldFont;
	private Font dejavuSerifFont;
	private Font dejavuSerifBoldFont;
	
	private Style standard6Font;
	private Style standard7Font;
	private Style standard8Font;
	private Style standard10Font;
	private Style standard11Font;
	private Style standard12Font;

	private Style standard7BoldFont;
	private Style standard8BoldFont;
	private Style standard10BoldFont;
	private Style standard11BoldFont;
	private Style standard12BoldFont;

	private Style standard8ItalicFont;

	public TemplateFonts(){
		
		this.dejavuSansFont = createDejavuSansFont();
		this.dejavuSansBoldFont = createDejavuSansBoldFont();
		this.dejavuSerifFont = createDejavuSerifFont();
		this.dejavuSerifBoldFont = createDejavuSerifBoldFont();
		
		this.standard6Font = createStandard6Font();
		this.standard7Font = createStandard7Font();
		this.standard8Font = createStandard8Font();
		this.standard10Font = createStandard10Font();
		this.standard11Font = createStandard11Font();
		this.standard12Font = createStandard12Font();

		this.standard7BoldFont = createStandard7BoldFont();
		this.standard8BoldFont = createStandard8BoldFont();
		this.standard10BoldFont = createStandard10BoldFont();		
		this.standard11BoldFont = createStandard11BoldFont();	
		this.standard12BoldFont = createStandard12BoldFont();

		this.standard8ItalicFont = createStandard8ItalicFont();
		
	}
	
	private Font createDejavuSansFont() {
		
		Font font = new Font();
		font.setFontName("DejaVu Sans");
		font.setBold(false);
		font.setPdfFontName("DejaVu Sans");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);
		
		return font;
		
	}
	
	private Font createDejavuSansBoldFont() {
		Font font = new Font();
		font.setFontName("DejaVu Sans");
		font.setBold(true);
		font.setPdfFontName("DejaVu Sans");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);
		
		return font;
	}
	
private Font createDejavuSerifFont() {
		
		Font font = new Font();
		font.setFontName("DejaVu Serif");
		font.setBold(false);
		font.setPdfFontName("DejaVu Serif");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);
		
		return font;
		
	}
	
	private Font createDejavuSerifBoldFont() {
		Font font = new Font();
		font.setFontName("DejaVu Serif");
		font.setBold(true);
		font.setPdfFontName("DejaVu Serif");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);
		
		return font;
	}

	public Style createStandard6Font() {

		Font font = new Font();
//		font.setFontName("Roboto Black");
//		font.setPdfFontName("/fonts/Roboto-Black.ttf");
		font.setFontName("Helvetica-Normal");
		font.setPdfFontName("/fonts/helvetica-normal.ttf");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);
		font.setFontSize(6);
		font.setBold(false);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style createStandard7Font() {

		Font font = createStandard6Font().getFont();
		font.setFontSize(7);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style createStandard8Font() {

		Font font = createStandard7Font().getFont();
		font.setFontSize(8);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	public Style createStandard10Font() {

		Font font = createStandard7Font().getFont();
		font.setFontSize(10);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	public Style createStandard11Font() {

		Font font = createStandard7Font().getFont();
		font.setFontSize(11);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style createStandard12Font() {

		Font font = createStandard7Font().getFont();
		font.setFontSize(12);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style createStandard7BoldFont() {
		Font font = createStandard7Font().getFont();
		font.setBold(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	public Style createStandard8BoldFont() {
		Font font = createStandard8Font().getFont();
		font.setBold(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	public Style createStandard10BoldFont() {
		Font font = createStandard10Font().getFont();
		font.setBold(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}
	
	public Style createStandard11BoldFont() {
		Font font = createStandard11Font().getFont();
		font.setBold(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style createStandard12BoldFont() {
		Font font = createStandard12Font().getFont();
		font.setBold(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style createStandard8ItalicFont() {
		Font font = createStandard8Font().getFont();
		font.setItalic(true);

		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(font);
		sb.setTextColor(Color.BLACK);
		return sb.build();

	}

	public Style getStandard6Font() {
		return standard6Font;
	}

	public Style getStandard7Font() {
		return standard7Font;
	}

	public Style getStandard8Font() {
		return standard8Font;
	}

	public Style getStandard12Font() {
		return standard12Font;
	}

	public Style getStandard8BoldFont() {
		return standard8BoldFont;
	}

	public Style getStandard12BoldFont() {
		return standard12BoldFont;
	}

	public Style getStandard8ItalicFont() {
		return standard8ItalicFont;
	}

	public Style getStandard10Font() {
		return standard10Font;
	}

	public Style getStandard10BoldFont() {
		return standard10BoldFont;
	}

	public Style getStandard11Font() {
		return standard11Font;
	}

	public Style getStandard11BoldFont() {
		return standard11BoldFont;
	}

	public Style getStandard7BoldFont() {
		return standard7BoldFont;
	}

	public Font getDejavuSansFont() {
		return dejavuSansFont;
	}

	public Font getDejavuSansBoldFont() {
		return dejavuSansBoldFont;
	}

	public Font getDejavuSerifFont() {
		return dejavuSerifFont;
	}

	public Font getDejavuSerifBoldFont() {
		return dejavuSerifBoldFont;
	}

}
