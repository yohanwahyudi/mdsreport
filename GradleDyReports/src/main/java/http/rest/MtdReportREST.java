package http.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdi.batch.mds.BatchMDSMtdPerformance;
import com.vdi.batch.mds.BatchMDSMtdReport;

import http.rest.model.Response;

@RestController
@RequestMapping("/api/v1/mtd")
public class MtdReportREST {

	private final Logger logger = LogManager.getLogger(PopulateDataREST.class);

	@GetMapping("/performance")
	public ResponseEntity<Response> mtdPerformance() {

		try {
			BatchMDSMtdPerformance batchMDSMtdPerformance = new BatchMDSMtdPerformance();
			batchMDSMtdPerformance.executeBatch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"populate data performance for MTD is finished", null);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		
	}
	
	@GetMapping("/report")
	public ResponseEntity<Response> mtdReport() {
		
		try {
			BatchMDSMtdReport batchMDSMtdReport = new BatchMDSMtdReport();
			batchMDSMtdReport.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"Batch for MTD Report is finished", null);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

}
