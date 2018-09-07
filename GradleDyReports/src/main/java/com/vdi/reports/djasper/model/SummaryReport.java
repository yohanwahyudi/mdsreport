package com.vdi.reports.djasper.model;

public class SummaryReport {
	
	private String name;
	private String value;
	private String value1;
	private String value2;
	private String value3;
	
	public SummaryReport() {
		
	}
	
	public SummaryReport(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public SummaryReport(String name, String value1, String value2) {
		this.name = name;
		this.value1 = value1;
		this.value2 = value2;
	}
	
	public SummaryReport(String name, String value1, String value2, String value3) {
		this.name = name;
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}
	
	

}
