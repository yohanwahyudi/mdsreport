package com.vdi.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportExporter {
    /**
     * Logger for this class
     */
    private static final Logger logger = LogManager.getLogger(ReportExporter.class);

    /**
     * The path to the file must exist.
     *
     * @param jp
     * @param path
     * @throws JRException
     * @throws FileNotFoundException
     */
    public static void exportReport(JasperPrint jp, String path) throws FileNotFoundException, JRException {
        
        JRPdfExporter exporter = new JRPdfExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        
        FileOutputStream fos = null;
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = null;
        
        try {
	        fos= new FileOutputStream(outputFile);
	
	        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
	        simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);
	
	        exporter.setExporterInput(simpleExporterInput);
	        exporter.setExporterOutput(simpleOutputStreamExporterOutput);
	
	        exporter.exportReport();
        }
	    catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				simpleOutputStreamExporterOutput.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

        logger.info("Report exported: " + path);
        
    }

    public static void exportReportXls(JasperPrint jp, String path, SimpleXlsReportConfiguration configuration) throws JRException, FileNotFoundException {
        JRXlsExporter exporter = new JRXlsExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        FileOutputStream fos = new FileOutputStream(outputFile);

        exporter.setConfiguration(configuration);

        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);

        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);

        exporter.exportReport();

        logger.debug("Xlsx Report exported: " + path);
    }

    public static void exportReportXls(JasperPrint jp, String path) throws JRException, FileNotFoundException {
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setIgnoreGraphics(false);
        configuration.setIgnorePageMargins(true);

        exportReportXls(jp, path, configuration);
    }

    public static void exportReportHtml(JasperPrint jp, String path) throws JRException, FileNotFoundException {
        HtmlExporter exporter = new HtmlExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        FileOutputStream fos = new FileOutputStream(outputFile);

        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
        SimpleHtmlExporterOutput simpleOutputStreamExporterOutput = new SimpleHtmlExporterOutput(fos);

        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);
        SimpleHtmlExporterConfiguration configuration = new SimpleHtmlExporterConfiguration();

        exporter.setConfiguration(configuration);

        exporter.exportReport();

        logger.debug("HTML Report exported: " + path);
    }

}
