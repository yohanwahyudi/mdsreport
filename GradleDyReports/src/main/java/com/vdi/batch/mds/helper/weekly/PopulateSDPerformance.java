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
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.tools.TimeTools;

@Component("populateSDPerformanceWeekly")
public class PopulateSDPerformance {

	@Autowired
	@Qualifier("weeklySDPerfAllDAO")
	private PerfAllDAOService allDAO;

	@Autowired
	@Qualifier("weeklySDPerfAgentDAO")
	private PerfAgentDAOService agentDAO;
	
	@Autowired
	private TimeTools timeTools;

	private int currentMonth;
	private int currentWeekYear;

	private final Logger logger = LogManager.getLogger(PopulateSDPerformance.class);

	public PopulateSDPerformance() {
		
	}

	public void populatePerformance() {
		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeekYear = timeTools.getCurrentWeekYear(); 
		int prevWeekYear = currentWeekYear - 1;

		allDAO.insertPerformance(getPerformanceOverall(prevWeekYear, currentMonth));
		agentDAO.insertPerformance(getPerformanceAgentList(prevWeekYear, currentMonth));
	}
	
	public void populatePerformance(int week, int month) {	
		this.currentMonth = timeTools.getCurrentMonth();
		this.currentWeekYear = timeTools.getCurrentWeekYear();
		int prevWeekYear = currentWeekYear - 1;
		
		allDAO.insertPerformance(getPerformanceOverall(week, month));
		agentDAO.insertPerformance(getPerformanceAgentList(week, month));
	}

	@SuppressWarnings("unused")
	public PerformanceOverall getPerformanceOverall(int week, int month) {
		logger.info("SD Overall");
		logger.info("week: " + week + " month: " + month);
		
		int ticketCount = allDAO.getTicketCount(week, month);
		int achievedCount = allDAO.getAchievedTicketCount(week, month);
		int missedCount = allDAO.getMissedTicketCount(week, month);
		float achievement = 0;

		logger.info(ticketCount);
		logger.info(achievedCount);
		logger.info(missedCount);

		achievement = (getAchievementTicket(new BigDecimal(achievedCount), new BigDecimal(ticketCount))).floatValue();

		// check existing performance
		int weekCheck = currentWeekYear;
		int monthCheck = month;
		if (currentMonth != month) {
			weekCheck = currentWeekYear;
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
			poUseThis.setCategory("sd");
		} else {
			poExisting.setTotalTicket(ticketCount);
			poExisting.setTotalAchieved(achievedCount);
			poExisting.setTotalMissed(missedCount);
			poExisting.setAchievement(achievement);

			poUseThis = poExisting;
		}

		return poUseThis;

	}

	public List<PerformanceAgent> getPerformanceAgentList(int week, int month) {

		logger.info("getPerformanceAgent");
		logger.info("week: " + week);
		logger.info("month: " + month);
		
		// get new ticket
		List<Object[]> newObjList = new ArrayList<Object[]>();
		newObjList = agentDAO.getAgentTicket(week, month);

		Map<String, PerformanceAgent> newPerfMap = new HashMap<String, PerformanceAgent>();
		List<PerformanceAgent> newPerfList = new ArrayList<PerformanceAgent>();

		for (Object[] object : newObjList) {

			String division = (String) object[0];
			String agentName = (String) object[1];
			int totalAchieved = ((BigInteger) object[2]).intValue();
			int totalMissed = ((BigInteger) object[3]).intValue();
			int totalTicket = ((BigInteger) object[4]).intValue();
			Integer currMonth = month;
			float achievement = (getAchievementTicket(new BigDecimal(totalAchieved), new BigDecimal(totalTicket)))
					.floatValue();

			PerformanceAgent perfAgent = new PerformanceAgent();
			perfAgent.setDivision(division);
			perfAgent.setAgentName(agentName);
			perfAgent.setTotalAchieved(totalAchieved);
			perfAgent.setTotalMissed(totalMissed);
			perfAgent.setTotalTicket(totalTicket);
			perfAgent.setMonth(currMonth.shortValue());
			perfAgent.setPeriod("weekly");
			perfAgent.setCategory("sd");
			perfAgent.setAchievement(achievement);
			
			logger.info("agent: "+agentName);
			logger.info("all: "+totalTicket);

			newPerfList.add(perfAgent);
			newPerfMap.put(agentName, perfAgent);
		}

		// compare with existing ticket
		// check existing performance
		int weekCheck = currentWeekYear;
		int monthCheck = month;
		if (currentMonth != month) {
			weekCheck = currentWeekYear;
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
