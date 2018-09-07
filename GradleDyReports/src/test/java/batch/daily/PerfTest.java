package batch.daily;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.vdi.batch.mds.repository.dao.PerfAgentDAOService;
import com.vdi.batch.mds.repository.dao.PerfAllDAOService;
import com.vdi.batch.mds.repository.dao.PerfTeamDAOService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.performance.PerformanceOverall;
import com.vdi.model.performance.PerformanceTeam;

public class PerfTest {
	
	private static final Logger logger = LogManager.getLogger(PerfTest.class);
	
	public static void main(String args[]) {
		
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		PerfAllDAOService all = ctx.getBean("weeklyPerfAllDAO", PerfAllDAOService.class);	
		PerformanceOverall overall = new PerformanceOverall();
		overall.setTotalTicket(all.getTicketCount());
		overall.setTotalMissed(all.getMissedTicketCount());
		overall.setTotalAchieved(all.getAchievedTicketCount());
		overall.setPeriod("weekly");
		BigDecimal bd = (new BigDecimal(overall.getTotalAchieved())).divide(new BigDecimal(overall.getTotalTicket()), 4, BigDecimal.ROUND_HALF_UP);
		bd = bd.multiply(new BigDecimal(100));
		overall.setAchievement(bd.floatValue());
		
//		logger.debug(bd.floatValue());
//		
//		logger.debug(overall.getTotalTicket());
//		logger.debug(overall.getTotalAchieved());
//		logger.debug(overall.getTotalMissed());
//		logger.debug(overall.getPeriod());
		
		PerformanceOverall temp1 = all.getPerformance();
		//insert if no rows, update if weekly exist
		if(temp1==null) {
			logger.debug("null");
			all.insertPerformance(overall);
		}else {
			temp1.setAchievement(overall.getAchievement());
			temp1.setTotalAchieved(overall.getTotalAchieved());
			temp1.setTotalMissed(overall.getTotalMissed());
			temp1.setTotalTicket(overall.getTotalTicket());
			all.updatePerformance(temp1);
			
//			logger.debug("weekly "+temp1.getPeriod());
//			logger.debug("weekly "+temp1.getCreatedDate());
		}
		
		
		PerfTeamDAOService team = ctx.getBean("weeklyPerfTeamDAO", PerfTeamDAOService.class);		
		List<Object[]> obj = new ArrayList<Object[]>();		
		obj = team.getTeamTicketByDivision();		
		Map<String, PerformanceTeam> map = new HashMap<String, PerformanceTeam>();
		
		List<PerformanceTeam> newPerfList = new ArrayList<PerformanceTeam>();
		
		for(Object[] object:obj) {
				PerformanceTeam perfTeam = new PerformanceTeam();
				perfTeam.setTeamName((String) object[0]);
				perfTeam.setTotalTicket( ((BigInteger) object[1]).intValue());
				perfTeam.setTotalAchieved( ((BigInteger) object[2]).intValue());
				perfTeam.setTotalMissed(((BigInteger) object[3]).intValue());
				perfTeam.setPeriod("weekly");
				
				BigDecimal achievementTeam = (new BigDecimal(perfTeam.getTotalAchieved())).divide(new BigDecimal(perfTeam.getTotalTicket()), 4, BigDecimal.ROUND_HALF_UP);
				achievementTeam = achievementTeam.multiply(new BigDecimal(100));
				perfTeam.setAchievement(achievementTeam.floatValue());
				
				newPerfList.add(perfTeam);
				
				map.put((String)object[0], perfTeam);
		}		
		
		//logger
		for(Map.Entry<String, PerformanceTeam> entry : map.entrySet()) {
			PerformanceTeam temp = new PerformanceTeam();
			temp = entry.getValue();
//			logger.debug(entry.getKey()+" "+temp.getTotalTicket()+" "+ temp.getTotalAchieved() +" "+temp.getTotalMissed());
		}
		
		//get updated team
		List<PerformanceTeam> listTeam = team.getPerformance();
		if(listTeam.size()<1) {
			logger.debug("weekly db empty, insert new records");
			team.insertPerformance(newPerfList);
			
		} else {
			logger.debug("weekly db exist, update records");
			
			Map<String, PerformanceTeam> mapExisting = new HashMap<String, PerformanceTeam>();
			for(PerformanceTeam pf:listTeam) {				
				mapExisting.put(pf.getTeamName(), pf);
			}
			
			List<PerformanceTeam> newUpdatedList = new ArrayList<PerformanceTeam>();
			
			for(Map.Entry<String, PerformanceTeam> entry : map.entrySet()) {
				PerformanceTeam existing = mapExisting.get(entry.getKey());
				PerformanceTeam updated = map.get(entry.getKey());
				
				existing.setAchievement(updated.getAchievement());
				existing.setTeamName(updated.getTeamName());
				existing.setTotalAchieved(updated.getTotalAchieved());
				existing.setTotalMissed(updated.getTotalMissed());
				existing.setTotalTicket(updated.getTotalTicket());
				
				newUpdatedList.add(existing);
			}
			
			team.updatePerformance(newUpdatedList);			
		}
		
		PerfAgentDAOService agentCtx = ctx.getBean("weeklyPerfAgentDAO", PerfAgentDAOService.class);
		List<Object[]> agentList = new ArrayList<Object[]>(); 
		agentList = agentCtx.getAgentTicket();		
		Map<String,PerformanceAgent> mapAgent = new HashMap<String,PerformanceAgent>();
		List<PerformanceAgent> newAgentList = new ArrayList<PerformanceAgent>();
		for(Object[] object: agentList) {
			PerformanceAgent agent = new PerformanceAgent();
			agent.setDivision((String) object[0]);
			agent.setAgentName((String) object[1]);
			agent.setTotalAssigned( ((BigInteger) object[2]).intValue());
			agent.setTotalPending( ((BigInteger) object[3]).intValue());
			agent.setTotalAchieved( ((BigInteger) object[4]).intValue());
			agent.setTotalMissed(((BigInteger) object[5]).intValue());
			agent.setTotalTicket(((BigInteger) object[6]).intValue());
			agent.setPeriod("weekly");
			
			BigDecimal achievementTeam = (new BigDecimal(agent.getTotalAchieved())).divide(new BigDecimal(agent.getTotalTicket()), 4, BigDecimal.ROUND_HALF_UP);
			achievementTeam = achievementTeam.multiply(new BigDecimal(100));
			agent.setAchievement(achievementTeam.floatValue());
			
			mapAgent.put((String) object[1], agent);
			newAgentList.add(agent);
		}		
		for(Map.Entry<String, PerformanceAgent> entry : mapAgent.entrySet()) {
			PerformanceAgent temp = new PerformanceAgent();
			temp = entry.getValue();
//			logger.debug(entry.getKey()+" "+temp.getDivision()+" "+temp.getTotalAssigned()+" "+ temp.getTotalPending() +" "+temp.getTotalAchieved()+" "+temp.getTotalMissed()+" "+temp.getTotalTicket());
		}
		
		List<PerformanceAgent> agentExisting = agentCtx.getPerformance();
		if(agentExisting.size() < 1) {
			logger.debug("weekly agent db empty, insert new records");
			agentCtx.insertPerformance(newAgentList);
		} else {
			logger.debug("weekly agent db exist, update records");
			
			Map<String, PerformanceAgent> mapExisting = new HashMap<String, PerformanceAgent>();
			for(PerformanceAgent pa:agentExisting) {				
				mapExisting.put(pa.getAgentName(), pa);
			}
			
			List<PerformanceAgent> newUpdatedAgentList = new ArrayList<PerformanceAgent>();
			
			for(Map.Entry<String, PerformanceAgent> entry : mapAgent.entrySet()) {
				PerformanceAgent existing = mapExisting.get(entry.getKey());
				PerformanceAgent updated = mapAgent.get(entry.getKey());
				
				existing.setAchievement(updated.getAchievement());
				existing.setAgentName(updated.getAgentName());
				existing.setTotalAchieved(updated.getTotalAchieved());
				existing.setTotalMissed(updated.getTotalMissed());
				existing.setTotalTicket(updated.getTotalTicket());
				
				newUpdatedAgentList.add(existing);
			}
			
			agentCtx.updatePerformance(newUpdatedAgentList);	
			
		}

	}

}
