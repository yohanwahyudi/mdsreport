package batch.daily;


import java.io.IOException;
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
import com.vdi.batch.mds.repository.dao.StagingServiceDeskDAOService;
import com.vdi.batch.mds.repository.dao.TempValueService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.TempValue;
import com.vdi.model.performance.PerformanceAgent;
import com.vdi.model.staging.StagingServiceDesk;
import com.vdi.tools.IOToolsService;
import com.vdi.tools.TimeStatic;

public class ReadStream {
	
	public static final Logger logger = LogManager.getLogger(ReadStream.class);
	private static final Double bufferSize = (Math.pow(1024, 2));
	
	public static void main(String args[]) throws IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		TempValueService tempValue = ctx.getBean("tempValueDAO", TempValueService.class);
//		
//		TempValue tv = new TempValue("LAST_MONTH","6");
//		
//		tempValue.save(tv);
		
//		TempValue temp = tempValue.getTempValueByName("LAST_MONTH");
		
		System.out.println(tempValue.getTempValueByName("LAST_MONTH").getValue());
		
		PerfAllDAOService perfAllSdWeekly = ctx.getBean("weeklySDPerfAllDAO", PerfAllDAOService.class);
		
		int week = TimeStatic.currentWeekYear-1;
		int month = TimeStatic.currentMonth;
		
		System.out.println("all "+perfAllSdWeekly.getTicketCount(week, month));
		System.out.println("achieved: "+perfAllSdWeekly.getAchievedTicketCount(week, month));
		System.out.println("missed: "+perfAllSdWeekly.getMissedTicketCount(week, month));
		
		PerfAgentDAOService perfAgentSdWeekly = ctx.getBean("weeklySDPerfAgentDAO", PerfAgentDAOService.class);
		
		List<Object[]> newObjList = new ArrayList<Object[]>();
		newObjList = perfAgentSdWeekly.getAgentTicket(week, month);

		for (Object[] object : newObjList) {

			logger.debug(object[0]+" "+object[0].getClass());
			logger.debug(object[1]);
			
			String division = (String) object[0];
			String agentName = (String) object[1];
			int totalAchieved = ((BigInteger) object[2]).intValue();
			int totalMissed = ((BigInteger) object[3]).intValue();
			int totalTicket = ((BigInteger) object[4]).intValue();
			
			System.out.println("agent: "+agentName);
			System.out.println("totalTicket: "+totalTicket);
		}
		
		
	}
	
	

}
