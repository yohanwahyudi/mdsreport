package com.vdi.reports.djasper.templates;

import org.springframework.context.annotation.Configuration;

import ar.com.fdvs.dj.domain.constants.Font;

@Configuration
public class TemplateFontsReport {

	private Font dejavuSansFont;
	private Font dejavuSansBoldFont;
	private Font dejavuSerifFont;
	private Font dejavuSerifBoldFont;

	private Font arialFont;
	private Font arialBoldFont;
	private Font arialItalicFont;

	public TemplateFontsReport() {

		this.dejavuSansFont = createDejavuSansFont();
		this.dejavuSansBoldFont = createDejavuSansBoldFont();
		this.dejavuSerifFont = createDejavuSerifFont();
		this.dejavuSerifBoldFont = createDejavuSerifBoldFont();

		this.arialFont = createArialFont();
		this.arialBoldFont = createArialBoldFont();
		this.arialItalicFont = createArialItalicFont();

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

	private Font createArialFont() {

		Font font = new Font();
		font.setFontName(Font._FONT_ARIAL);
		font.setBold(false);
		font.setPdfFontName("/fonts/arial.ttf");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);

		return font;

	}

	private Font createArialBoldFont() {

		Font font = new Font();
		font.setFontName(Font._FONT_ARIAL);
		font.setBold(true);
		font.setPdfFontName("/fonts/arialbd.ttf");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);

		return font;

	}

	private Font createArialItalicFont() {

		Font font = new Font();
		font.setFontName(Font._FONT_ARIAL);
		font.setBold(false);
		font.setItalic(true);
		font.setPdfFontName("/fonts/ariali.ttf");
		font.setPdfFontEncoding(Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing);
		font.setPdfFontEmbedded(true);

		return font;

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

	public Font getArialFont() {
		return arialFont;
	}

	public Font getArialBoldFont() {
		return arialBoldFont;
	}

	public Font getArialItalicFont() {
		return arialItalicFont;
	}

}
