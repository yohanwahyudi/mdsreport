package http.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdi.batch.mds.BatchItopMDSMonthlyReport;

import http.rest.model.Response;

@RestController
@RequestMapping("/api/v1/monthly")
public class MonthlyReportREST {
	
	private final Logger logger = LogManager.getLogger(PopulateDataREST.class);

	@GetMapping("/report")
	public ResponseEntity<Response> monthlyReport() {
		try {
			
			BatchItopMDSMonthlyReport monthly = new BatchItopMDSMonthlyReport();
			monthly.executeBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"MDS Monthly report is finished", null);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

}
