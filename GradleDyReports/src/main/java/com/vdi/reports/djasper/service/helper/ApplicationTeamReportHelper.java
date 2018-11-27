package com.vdi.reports.djasper.service.helper;

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

import com.vdi.batch.mds.repository.dao.MonthlyAppTeamDAOService;
import com.vdi.model.performance.DedicatedAgentTeam;
import com.vdi.reports.djasper.model.MasterReport;

@Component("applicationTeamReportHelper")
public class ApplicationTeamReportHelper {

	private final Logger logger = LogManager.getLogger(ApplicationTeamReportHelper.class);
	
	@Autowired
	@Qualifier("monthlyAppTeamDAO")
	private MonthlyAppTeamDAOService appTeam;

	private MasterReport masterReport;
	private List<DedicatedAgentTeam> performanceByTeam;
	private List<DedicatedAgentTeam> performanceByAgentTeamList;
	private List<Map<String, DedicatedAgentTeam>> performanceByAgentTeam;

	private void setPerformanceByTeam() {
		
		logger.debug("================================");		
		logger.debug("appTeam: " +appTeam.getTeamPerformance());
		
		List<Object[]> teamObjectList = appTeam.getTeamPerformance();
		
		logger.debug("TEAM OBJECT LIST");
		for(Object[] obj : teamObjectList) {
			logger.debug(obj[0]);
			logger.debug(obj[1]);
			logger.debug(obj[2]);
			logger.debug(obj[3]);
			logger.debug(obj[4]);
			logger.debug(obj[5]);
		}

		performanceByTeam = new ArrayList<DedicatedAgentTeam>();
		for (Object[] obj : teamObjectList) {
			String teamName = (String) obj[0];
			int ticketCount = ((BigInteger) obj[1]).intValue();
			int achieved = ((BigInteger) obj[2]).intValue();
			int missed = ((BigInteger) obj[3]).intValue();
			float achievement = ((BigDecimal) obj[4]).floatValue();
			float contribution = ((BigDecimal) obj[5]).floatValue();

			DedicatedAgentTeam app = new DedicatedAgentTeam();
			app.setTeamName(teamName);
			app.setTotalTicket(ticketCount);
			app.setTotalAchieved(achieved);
			app.setTotalMissed(missed);
			app.setAchievement(achievement);
			app.setContribution(contribution);

			performanceByTeam.add(app);
		}

	}

	public List<DedicatedAgentTeam> getPerformanceByTeam() {

		setPerformanceByTeam();

		return performanceByTeam;
	}

	private void setPerformanceByAgentTeam() {
		
		logger.debug("================================");		
		logger.debug("appAgent: " +appTeam.getAgentPerformance());

		List<Object[]> teamObjectList = appTeam.getAgentPerformance();

		performanceByAgentTeam = new ArrayList<Map<String, DedicatedAgentTeam>>();
		for (Object[] obj : teamObjectList) {
			String teamName = (String) obj[0];
			String agentName= ((String) obj[1]);
			int ticketCount = ((BigInteger) obj[2]).intValue();
			int achieved = ((BigInteger) obj[3]).intValue();
			int missed = ((BigInteger) obj[4]).intValue();
			float achievement = ((BigDecimal) obj[5]).floatValue();
			float contribution = ((BigDecimal) obj[6]).floatValue();
			float teamContribution = ((BigDecimal) obj[7]).floatValue();

			DedicatedAgentTeam app = new DedicatedAgentTeam();
			app.setTeamName(teamName);
			app.setTotalTicket(ticketCount);
			app.setTotalAchieved(achieved);
			app.setTotalMissed(missed);
			app.setAchievement(achievement);
			app.setContribution(contribution);
			app.setAgentName(agentName);
			app.setTeamContribution(teamContribution);

			Map<String, DedicatedAgentTeam> map = new HashMap<String, DedicatedAgentTeam>();
			map.put(teamName, app);
			
			performanceByAgentTeam.add(map);
		}

	}

	public List<Map<String, DedicatedAgentTeam>> getPerformanceByAgentTeam() {

		setPerformanceByAgentTeam();
		return performanceByAgentTeam;
	}
	
	private void setPerformanceByAgentTeamList() {
		
		List<Object[]> teamObjectList = appTeam.getAgentPerformance();
		
		logger.debug("AGENT OBJECT LIST");
		for(Object[] obj : teamObjectList) {
			logger.debug(obj[0]);
			logger.debug(obj[1]);
			logger.debug(obj[2]);
			logger.debug(obj[3]);
			logger.debug(obj[4]);
			logger.debug(obj[5]);
			logger.debug(obj[6]);
			logger.debug(obj[7]);
		}

		performanceByAgentTeamList = new ArrayList<DedicatedAgentTeam>();
		for (Object[] obj : teamObjectList) {
			String teamName = (String) obj[0];
			String agentName= ((String) obj[1]);
			int ticketCount = ((BigInteger) obj[2]).intValue();
			int achieved = ((BigInteger) obj[3]).intValue();
			int missed = ((BigInteger) obj[4]).intValue();
			float achievement = ((BigDecimal) obj[5]).floatValue();
			float contribution = ((BigDecimal) obj[6]).floatValue();
			float teamContribution = ((BigDecimal) obj[7]).floatValue();

			DedicatedAgentTeam app = new DedicatedAgentTeam();
			app.setTeamName(teamName);
			app.setTotalTicket(ticketCount);
			app.setTotalAchieved(achieved);
			app.setTotalMissed(missed);
			app.setAchievement(achievement);
			app.setContribution(contribution);
			app.setAgentName(agentName);
			app.setTeamContribution(teamContribution);

			performanceByAgentTeamList.add(app);
		}		
		
	}
	
	public List<DedicatedAgentTeam> getPerformanceByAgentTeamList(){
		
		setPerformanceByAgentTeamList();
		return performanceByAgentTeamList;		
	}
	
	public void setMasterReport() {
		
		MasterReport masterReport = new MasterReport();
		masterReport.setPerformanceByTeam(getPerformanceByTeam());
		masterReport.setPerformanceByAgentTeam(getPerformanceByAgentTeam());
		
		this.masterReport = masterReport;

	}
	
	public MasterReport getMasterReport() {
		return masterReport;
	}
	
	public List<MasterReport> getMasterReportList() {
		
		List<MasterReport> masterReportList = new ArrayList<MasterReport>();
		masterReportList.add(getMasterReport());
		
		return masterReportList;
	}

}
