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
import com.vdi.batch.mds.repository.dao.TempValueService;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.tools.TimeStatic;

@Component("populateURPerformanceWeekly")
public class PopulateURPerformance {

	@Autowired
	@Qualifier("weeklyURPerfAllDAO")
	private PerfAllDAOService allDAO;

	@Autowired
	@Qualifier("weeklyURPerfAgentDAO")
	private PerfAgentDAOService agentDAO;

	private TempValueService tempValue;

	private int currentMonth;
	private int lastSavedMonth;
	private int currentWeekYear;

	private final String LAST_MONTH = "LAST_MONTH";

	private final Logger logger = LogManager.getLogger(PopulateURPerformance.class);

	@Autowired
	public PopulateURPerformance(TempValueService tempValueService) {

		this.currentMonth = TimeStatic.currentMonth;
		this.currentWeekYear = TimeStatic.currentWeekYear;
		this.tempValue = tempValueService;

		this.lastSavedMonth = Integer.parseInt(tempValue.getTempValueByName(LAST_MONTH).getValue());
	}

	public void populatePerformance() {

		int prevWeekYear = currentWeekYear - 1;

		allDAO.insertPerformance(getPerformanceOverall(prevWeekYear, lastSavedMonth));
		agentDAO.insertPerformance(getPerformanceAgentList(prevWeekYear, lastSavedMonth));

		if (overlapMonth(lastSavedMonth)) {
			logger.debug("overlap month");

			allDAO.insertPerformance(getPerformanceOverall(prevWeekYear, currentMonth));
			agentDAO.insertPerformance(getPerformanceAgentList(prevWeekYear, currentMonth));

//			tempValue.updateTempValue(String.valueOf(currentMonth), LAST_MONTH);
		}

	}

	@SuppressWarnings("unused")
	public PerformanceOverall getPerformanceOverall(int week, int month) {

		logger.debug("week: " + week + " month: " + month);
		
		int ticketCount = allDAO.getTicketCount(week, month);
		int achievedCount = allDAO.getAchievedTicketCount(week, month);
		int missedCount = allDAO.getMissedTicketCount(week, month);
		float achievement = 0;

		logger.debug(ticketCount);
		logger.debug(achievedCount);
		logger.debug(missedCount);

		achievement = (getAchievementTicket(new BigDecimal(achievedCount), new BigDecimal(ticketCount))).floatValue();

		PerformanceOverall poUseThis = new PerformanceOverall();
		PerformanceOverall poExisting = allDAO.getPerformance(currentWeekYear, month);
		if (poExisting == null) {
			poUseThis.setTotalTicket(ticketCount);
			poUseThis.setTotalAchieved(achievedCount);
			poUseThis.setTotalMissed(missedCount);
			poUseThis.setAchievement(achievement);
			
			Integer currMonth = month;			
			poUseThis.setMonth(currMonth.shortValue());
			
			poUseThis.setPeriod("weekly");
			poUseThis.setCategory("ur");
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

		logger.debug("getPerformanceAgent");
		logger.debug("week: " + week);
		logger.debug("month: " + month);
		
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
			perfAgent.setCategory("ur");
			perfAgent.setAchievement(achievement);

			newPerfList.add(perfAgent);
			newPerfMap.put(agentName, perfAgent);
		}

		// compare with existing ticket
		List<PerformanceAgent> existingList = agentDAO.getPerformance(currentWeekYear, month);
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

	private Boolean overlapMonth(int lastSavedMonth) {
		if (currentMonth == lastSavedMonth) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

}
