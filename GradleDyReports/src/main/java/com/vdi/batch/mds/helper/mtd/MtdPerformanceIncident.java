package com.vdi.batch.mds.helper.mtd;

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
import com.vdi.batch.mds.repository.dao.PerformanceAgentService;
import com.vdi.batch.mds.repository.dao.PerformanceTeamService;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.performance.PerformanceTeam;
import com.vdi.tools.TimeStatic;
import com.vdi.tools.TimeTools;

@Component("mtdPerformanceIncident")
public class MtdPerformanceIncident {
	
	private final Logger logger = LogManager.getLogger(MtdPerformanceIncident.class);
	
	@Autowired
	@Qualifier("mtdPerfAllDao")
	private PerfAllDAOService perfAllDao;
	
	@Autowired
	@Qualifier("mtdPerfTeamDao")
	private PerfTeamDAOService perfTeamDao;
	
	@Autowired
	@Qualifier("mtdPerfAgentDao")
	private PerfAgentDAOService perfAgentDao;
	
	@Autowired
	private PerformanceTeamService performanceTeamService;
	
	@Autowired
	private PerformanceAgentService performanceAgentService;
	
	private Integer thisMonth;
	
	public MtdPerformanceIncident() {
		
	}
	
	@Autowired
	public MtdPerformanceIncident(TimeTools timeTools) {
		this.thisMonth = timeTools.getCurrentMonth();
	}
	
	public void populate() {
		
		logger.info("start populate mtd performance incident......");
		
		perfAllDao.insertPerformance(getOverall());
		perfTeamDao.insertPerformance(getPerformanceTeam());
		perfAgentDao.insertPerformance(getPerformanceAgent());
		
		performanceTeamService.deleteUnassignedTeam();
		performanceAgentService.deleteUnassignedAgent();
		
		logger.info("finish populate mtd performance incident......");
		
	}
	
	private PerformanceOverall getOverall() {
		
		int ticketCount = perfAllDao.getTicketCount();
		int achievedCount = perfAllDao.getAchievedTicketCount();
		int missedCount = perfAllDao.getMissedTicketCount();
		float achievement = (getAchievementTicket(new BigDecimal(achievedCount), new BigDecimal(ticketCount)))
				.floatValue();
		
		logger.info(ticketCount);
		logger.info(achievedCount);
		logger.info(missedCount);
		logger.info(achievement);
		
		PerformanceOverall poUseThis = new PerformanceOverall();
		PerformanceOverall poExisting = perfAllDao.getPerformance();
		if(poExisting==null) {
			logger.info("no mtd overall performance exist, insert data");
			poUseThis.setTotalTicket(ticketCount);
			poUseThis.setTotalAchieved(achievedCount);
			poUseThis.setTotalMissed(missedCount);
			poUseThis.setAchievement(achievement);
			poUseThis.setPeriod("monthly");
			poUseThis.setCategory("sa");
			poUseThis.setMonth(thisMonth.shortValue());
		} else {
			logger.info("overall performance exist, update data");
			poExisting.setTotalTicket(ticketCount);
			poExisting.setTotalAchieved(achievedCount);
			poExisting.setTotalMissed(missedCount);
			poExisting.setAchievement(achievement);

			poUseThis = poExisting;
		}
		
		return poUseThis;
		
	}
	
	private List<PerformanceTeam> getPerformanceTeam() {
		
		// get new ticket
		List<Object[]> newObjList = new ArrayList<Object[]>();
		newObjList = perfTeamDao.getTeamTicketByDivision();
		
		Map<String, PerformanceTeam> newPerfMap = new HashMap<String, PerformanceTeam>();
		List<PerformanceTeam> newPerfList = new ArrayList<PerformanceTeam>();
		
		for (Object[] object : newObjList) {

			int ticketCount = ((BigInteger) object[1]).intValue();
			int achievedCount = ((BigInteger) object[2]).intValue();
			int missedCount = ((BigInteger) object[3]).intValue();
			float achievement = (getAchievementTicket(new BigDecimal(achievedCount), new BigDecimal(ticketCount)))
					.floatValue();
			String teamName = (String) object[0];

			PerformanceTeam perfTeam = new PerformanceTeam();
			perfTeam.setTeamName(teamName);
			perfTeam.setTotalTicket(ticketCount);
			perfTeam.setTotalAchieved(achievedCount);
			perfTeam.setTotalMissed(missedCount);
			perfTeam.setPeriod("monthly");
			perfTeam.setCategory("sa");
			perfTeam.setMonth(thisMonth.shortValue());
			perfTeam.setAchievement(achievement);

			newPerfList.add(perfTeam);
			newPerfMap.put(teamName, perfTeam);

		}
		
		// compare with existing ticket
		List<PerformanceTeam> existingList = perfTeamDao.getPerformance();
		List<PerformanceTeam> useThisList = new ArrayList<PerformanceTeam>();
		
		if (existingList.size() < 1) {
			logger.info("no mtd team performance exist, insert data");
			useThisList = newPerfList;

		} else {
			logger.info("mtd team performance exist, insert data");
			Map<String, PerformanceTeam> existingMap = new HashMap<String, PerformanceTeam>();
			for (PerformanceTeam pf : existingList) {
				existingMap.put(pf.getTeamName(), pf);
			}

			for (Map.Entry<String, PerformanceTeam> entry : newPerfMap.entrySet()) {
				PerformanceTeam existing = existingMap.get(entry.getKey());
				PerformanceTeam updated = newPerfMap.get(entry.getKey());

				if(existing!=null) {
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
	
	private List<PerformanceAgent> getPerformanceAgent() {
		
		// get new ticket
		List<Object[]> newObjList = new ArrayList<Object[]>();
		newObjList = perfAgentDao.getAgentTicket();

		Map<String, PerformanceAgent> newPerfMap = new HashMap<String, PerformanceAgent>();
		List<PerformanceAgent> newPerfList = new ArrayList<PerformanceAgent>();
		
		for (Object[] object : newObjList) {

			String division = (String) object[0];
			String agentName = (String) object[1];
			int totalAssigned = ((BigInteger) object[2]).intValue();
			int totalPending = ((BigInteger) object[3]).intValue();
			int totalAchieved = ((BigInteger) object[4]).intValue();
			int totalMissed = ((BigInteger) object[5]).intValue();
			int totalTicket = ((BigInteger) object[6]).intValue();
			String period = "monthly";
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
			perfAgent.setCategory("sa");
			perfAgent.setMonth(thisMonth.shortValue());
			perfAgent.setAchievement(achievement);

			newPerfList.add(perfAgent);
			newPerfMap.put(agentName, perfAgent);
		}
		
		// compare with existing ticket
		List<PerformanceAgent> existingList = perfAgentDao.getPerformance();
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

				if(existing!=null) {
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
	
	private BigDecimal getAchievementTicket(BigDecimal ticketAchieved, BigDecimal ticketTotal) {

		BigDecimal achievement = new BigDecimal(0);
		
		achievement = ticketAchieved.divide(ticketTotal, 4, BigDecimal.ROUND_HALF_UP);
		achievement = achievement.multiply(new BigDecimal(100));

		return achievement;
	}

}
