package com.vdi.reports.djasper.service.impl;

import java.util.List;

import com.vdi.reports.djasper.model.MasterReport;
import com.vdi.reports.djasper.model.PerformanceReport;
import com.vdi.reports.djasper.service.ReportService;

import ar.com.fdvs.dj.domain.DynamicReport;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class ChangeDedicatedReport implements ReportService{

	@Override
	public DynamicReport buildReport() {
		
		return null;
	}

	@Override
	public JasperPrint getReport() throws JRException, Exception {
		
		return null;
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
		
		return null;
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
