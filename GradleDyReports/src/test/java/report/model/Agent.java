package report.model;

import java.util.ArrayList;
import java.util.List;


public class Agent {
	
	private String division;
	private String name;
	
	private int assigned;
	private int pending;
	private int achieved;
	private int missed;
	private int ticketTotal;
	
	private String achievement;
	private String remarks;
	
	public List<Subreport1> subreport1 = new ArrayList<Subreport1>();
	
	public List<Subreport1> getSubreport1() {
		return subreport1;
	}

	public void setSubreport1(List<Subreport1> subreport1) {
		this.subreport1 = subreport1;
	}

	public Agent(String division, String name, int assigned, int pending, int achiveved, int missed, int ticketTotal, String achievement, String remarks) {
		this.division = division;
		this.name = name;
		this.assigned = assigned;
		this.pending = pending;
		this.achieved = achiveved;
		this.missed = missed;
		this.ticketTotal = ticketTotal;
		this.achievement = achievement;
		this.remarks = remarks;
	}
	
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAssigned() {
		return assigned;
	}
	public void setAssigned(int assigned) {
		this.assigned = assigned;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	public int getAchieved() {
		return achieved;
	}
	public void setAchieved(int achieved) {
		this.achieved = achieved;
	}
	public int getMissed() {
		return missed;
	}
	public void setMissed(int missed) {
		this.missed = missed;
	}
	public int getTicketTotal() {
		return ticketTotal;
	}
	public void setTicketTotal(int ticketTotal) {
		this.ticketTotal = ticketTotal;
	}
	public String getAchievement() {
		return achievement;
	}
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}

