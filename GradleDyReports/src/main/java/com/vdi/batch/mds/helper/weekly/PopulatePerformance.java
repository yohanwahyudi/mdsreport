package com.vdi.batch.mds.helper.weekly;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.batch.mds.repository.dao.PerfTeamDAOService;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.performance.PerformanceTeam;
import com.vdi.tools.TimeTools;

@Component("populatePerformanceWeekly")
public class PopulatePerformance {

	@Autowired
	@Qualifier("weeklyPerfAllDAO")
	private PerfAllDAOService allDAO;

	@Autowired
	@Qualifier("weeklyPerfTeamDAO")
	private PerfTeamDAOService teamDAO;

	@Autowired
	@Qualifier("weeklyPerfAgentDAO")
	private PerfAgentDAOService agentDAO;

	@Autowired
	private TimeTools timeTools;

	private int currentMonth;
	private int currentWeek;

	private final Logger logger = LogManager.getLogger(PopulatePerformance.class);

	public PopulatePerformance() {

	}

	public void populatePerformance() {

		logger.info("Start populate performance weekly...");

		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeek = timeTools.getCurrentWeekYear();
		int previousWeek = currentWeek - 1;

		allDAO.insertPerformance(getPerformanceOverall(previousWeek, currentMonth));
		teamDAO.insertPerformance(getPerformanceTeamList(previousWeek, currentMonth));
		agentDAO.insertPerformance(getPerformanceAgentList(previousWeek, currentMonth));

		logger.info("finished populate performance weekly...");
	}

	public void populatePerformance(int week, int month) {
		logger.info("Start populate performance weekly...");

		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeek = timeTools.getCurrentWeekYear();
		int previousWeek = currentWeek - 1;

		allDAO.insertPerformance(getPerformanceOverall(week, month));
		teamDAO.insertPerformance(getPerformanceTeamList(week, month));
		agentDAO.insertPerformance(getPerformanceAgentList(week, month));
		
		logger.info("finished populate performance weekly...");
	}

	@SuppressWarnings("unused")
	public PerformanceOverall getPerformanceOverall(int week, int month) {

		logger.info("all week: " + week + " month: " + month);

		int ticketCount = allDAO.getTicketCount(week, month);
		int achievedCount = allDAO.getAchievedTicketCount(week, month);
		int missedCount = allDAO.getMissedTicketCount(week, month);
		float achievement = (getAchievementTicket(new BigDecimal(achievedCount), new BigDecimal(ticketCount)))
				.floatValue();

		logger.info("all " + "ticketCount: " + ticketCount);
		logger.info("all " + "achievedCount: " + achievedCount);
		logger.info("all " + "missedCount: " + missedCount);

		// check existing performance
		int weekCheck = currentWeek;
		int monthCheck = month;
		if (currentMonth != month) {
			weekCheck = currentWeek;
			monthCheck = currentMonth-1;
		}

		logger.info("weekcheck:" + weekCheck + "monthcheck:" + monthCheck);

		PerformanceOverall poUseThis = new PerformanceOverall();
		PerformanceOverall poExisting = allDAO.getPerformance(weekCheck, monthCheck);
		if (poExisting == null) {
			poUseThis.setTotalTicket(ticketCount);
			poUseThis.setTotalAchieved(achievedCount);
			poUseThis.setTotalMissed(missedCount);
			poUseThis.setAchievement(achievement);

			Integer currMonth = month;
			poUseThis.setMonth(currMonth.shortValue());

			poUseThis.setPeriod("weekly");
			poUseThis.setCategory("sa");
		} else {
			poExisting.setTotalTicket(ticketCount);
			poExisting.setTotalAchieved(achievedCount);
			poExisting.setTotalMissed(missedCount);
			poExisting.setAchievement(achievement);

			poUseThis = poExisting;
		}

		return poUseThis;

	}

	public List<PerformanceTeam> getPerformanceTeamList(int week, int month) {

		logger.info("team week: " + week + "month: " + month);

		// get new ticket
		List<Object[]> newObjList = new ArrayList<Object[]>();
		newObjList = teamDAO.getTeamTicketByDivision(week, month);

		Map<String, PerformanceTeam> newPerfMap = new HashMap<String, PerformanceTeam>();
		List<PerformanceTeam> newPerfList = new ArrayList<PerformanceTeam>();

		logger.info("object length: " + newObjList.size());

		for (Object[] object : newObjList) {

			int ticketCount = ((BigInteger) object[1]).intValue();
			int achievedCount = ((BigInteger) object[2]).intValue();
			int missedCount = ((BigInteger) object[3]).intValue();
			Integer currMonth = month;

			float achievement = (getAchievementTicket(new BigDecimal(achievedCount), new BigDecimal(ticketCount)))
					.floatValue();
			String teamName = (String) object[0];

			PerformanceTeam perfTeam = new PerformanceTeam();
			perfTeam.setTeamName(teamName);
			perfTeam.setTotalTicket(ticketCount);
			perfTeam.setTotalAchieved(achievedCount);
			perfTeam.setTotalMissed(missedCount);
			perfTeam.setMonth(currMonth.shortValue());
			perfTeam.setPeriod("weekly");
			perfTeam.setCategory("sa");
			perfTeam.setAchievement(achievement);

			newPerfList.add(perfTeam);
			newPerfMap.put(teamName, perfTeam);

		}

		// compare with existing ticket
		// check existing performance
		int weekCheck = currentWeek;
		int monthCheck = month;
		if (currentMonth != month) {
			weekCheck = currentWeek;
			monthCheck = currentMonth-1;
		}

		List<PerformanceTeam> existingList = teamDAO.getPerformance(weekCheck, monthCheck);
		List<PerformanceTeam> useThisList = new ArrayList<PerformanceTeam>();
		if (existingList.size() < 1) {
			useThisList = newPerfList;

		} else {
			Map<String, PerformanceTeam> existingMap = new HashMap<String, PerformanceTeam>();
			for (PerformanceTeam pf : existingList) {
				existingMap.put(pf.getTeamName(), pf);
			}

			for (Map.Entry<String, PerformanceTeam> entry : newPerfMap.entrySet()) {
				PerformanceTeam existing = existingMap.get(entry.getKey());
				PerformanceTeam updated = newPerfMap.get(entry.getKey());
				if (existing != null) {
					existing.setAchievement(updated.getAchievement());
					existing.setTeamName(updated.getTeamName());
					existing.setTotalAchieved(updated.getTotalAchieved());
					existing.setTotalMissed(updated.getTotalMissed());
					existing.setTotalTicket(updated.getTotalTicket());
				} else {
					existing = updated;
				}

				useThisList.add(existing);
			}
		}

		return useThisList;
	}

	public List<PerformanceAgent> getPerformanceAgentList(int week, int month) {

		logger.info("agent week: " + week + "month: " + month);

		// get new ticket
		List<Object[]> newObjList = new ArrayList<Object[]>();
		newObjList = agentDAO.getAgentTicket(week, month);

		Map<String, PerformanceAgent> newPerfMap = new HashMap<String, PerformanceAgent>();
		List<PerformanceAgent> newPerfList = new ArrayList<PerformanceAgent>();

		logger.info("object agent list: " + newObjList.size());

		for (Object[] object : newObjList) {

			String division = (String) object[0];
			String agentName = (String) object[1];
			int totalAssigned = ((BigInteger) object[2]).intValue();
			int totalPending = ((BigInteger) object[3]).intValue();
			int totalAchieved = ((BigInteger) object[4]).intValue();
			int totalMissed = ((BigInteger) object[5]).intValue();
			int totalTicket = ((BigInteger) object[6]).intValue();
			Integer currMonth = month;
			String period = "weekly";
			float achievement = (getAchievementTicket(new BigDecimal(totalAchieved), new BigDecimal(totalTicket)))
					.floatValue();

			PerformanceAgent perfAgent = new PerformanceAgent();
			perfAgent.setDivision(division);
			perfAgent.setAgentName(agentName);
			perfAgent.setTotalAssigned(totalAssigned);
			perfAgent.setTotalPending(totalPending);
			perfAgent.setTotalAchieved(totalAchieved);
			perfAgent.setTotalMissed(totalMissed);
			perfAgent.setTotalTicket(totalTicket);
			perfAgent.setPeriod(period);
			perfAgent.setMonth(currMonth.shortValue());
			perfAgent.setCategory("sa");
			perfAgent.setAchievement(achievement);

			logger.info("agent: " + agentName + " division: " + division);

			newPerfList.add(perfAgent);
			newPerfMap.put(agentName, perfAgent);
		}

		// compare with existing ticket
		// compare with existing ticket
		// check existing performance
		int weekCheck = currentWeek;
		int monthCheck = month;
		if (currentMonth != month) {
			weekCheck = currentWeek;
			monthCheck = currentMonth-1;
		}

		List<PerformanceAgent> existingList = agentDAO.getPerformance(weekCheck, monthCheck);
		List<PerformanceAgent> useThisList = new ArrayList<PerformanceAgent>();
		if (existingList.size() < 1) {
			useThisList = newPerfList;

		} else {
			Map<String, PerformanceAgent> existingMap = new HashMap<String, PerformanceAgent>();
			for (PerformanceAgent pf : existingList) {
				existingMap.put(pf.getAgentName(), pf);
			}

			for (Map.Entry<String, PerformanceAgent> entry : newPerfMap.entrySet()) {
				PerformanceAgent existing = existingMap.get(entry.getKey());
				PerformanceAgent updated = newPerfMap.get(entry.getKey());

				if (existing != null) {

					existing.setDivision(updated.getDivision());
					existing.setAgentName(updated.getAgentName());
					existing.setTotalAssigned(updated.getTotalAssigned());
					existing.setTotalPending(updated.getTotalPending());
					existing.setTotalAchieved(updated.getTotalAchieved());
					existing.setTotalMissed(updated.getTotalMissed());
					existing.setTotalTicket(updated.getTotalTicket());
					existing.setAchievement(updated.getAchievement());
				} else {
					existing = updated;
				}

				useThisList.add(existing);
			}
		}

		return useThisList;
	}

	public BigDecimal getAchievementTicket(BigDecimal ticketAchieved, BigDecimal ticketTotal) {

		BigDecimal achievement = new BigDecimal(0);
		try {
			achievement = ticketAchieved.divide(ticketTotal, 4, BigDecimal.ROUND_HALF_UP);
			achievement = achievement.multiply(new BigDecimal(100));
		} catch (ArithmeticException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return achievement;
	}

}
