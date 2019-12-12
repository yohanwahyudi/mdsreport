package com.vdi.model;

public class Period {
	
	String currMonthStr;
	String prevMonthStr;
	String currWeekMonthStr;
	String prevWeekMonthStr;
	String currYearStr;
	
	Integer prevWeekMonth;
	Integer currMonth;
	Integer currYear;
	
	public Period() {
		
	}

	public String getCurrMonthStr() {
		return currMonthStr;
	}

	public String getPrevMonthStr() {
		return prevMonthStr;
	}

	public String getCurrWeekMonthStr() {
		return currWeekMonthStr;
	}

	public String getPrevWeekMonthStr() {
		return prevWeekMonthStr;
	}

	public String getCurrYearStr() {
		return currYearStr;
	}

	public Integer getPrevWeekMonth() {
		return prevWeekMonth;
	}

	public Integer getCurrMonth() {
		return currMonth;
	}

	public Integer getCurrYear() {
		return currYear;
	}

	public void setCurrMonthStr(String currMonthStr) {
		this.currMonthStr = currMonthStr;
	}

	public void setPrevMonthStr(String prevMonthStr) {
		this.prevMonthStr = prevMonthStr;
	}

	public void setCurrWeekMonthStr(String currWeekMonthStr) {
		this.currWeekMonthStr = currWeekMonthStr;
	}

	public void setPrevWeekMonthStr(String prevWeekMonthStr) {
		this.prevWeekMonthStr = prevWeekMonthStr;
	}

	public void setCurrYearStr(String currYearStr) {
		this.currYearStr = currYearStr;
	}

	public void setPrevWeekMonth(Integer prevWeekMonth) {
		this.prevWeekMonth = prevWeekMonth;
	}

	public void setCurrMonth(Integer currMonth) {
		this.currMonth = currMonth;
	}

	public void setCurrYear(Integer currYear) {
		this.currYear = currYear;
	}
	
	
	

}
