package report.model;

public class Performance {
	
	private int totalTicket;
	private int totalAchieved;
	private int totalMissed;
	private float achievement;
	
	public Performance(int totalTicket, int totalAchieved, int totalMissed, float achievement) {
		this.totalTicket = totalTicket;
		this.totalAchieved = totalAchieved;
		this.totalMissed = totalMissed;
		this.achievement = achievement;
	}
	
	public int getTotalTicket() {
		return totalTicket;
	}
	public void setTotalTicket(int totalTicket) {
		this.totalTicket = totalTicket;
	}
	public int getTotalAchieved() {
		return totalAchieved;
	}
	public void setTotalAchieved(int totalAchieved) {
		this.totalAchieved = totalAchieved;
	}
	public int getTotalMissed() {
		return totalMissed;
	}
	public void setTotalMissed(int totalMissed) {
		this.totalMissed = totalMissed;
	}
	public float getAchievement() {
		return achievement;
	}
	public void setAchievement(float achievement) {
		this.achievement = achievement;
	} 

}
