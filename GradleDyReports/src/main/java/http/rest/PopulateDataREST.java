package http.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdi.batch.mds.helper.PopulateIncident;
import com.vdi.batch.mds.helper.PopulateProblemChange;
import com.vdi.batch.mds.helper.PopulateServiceDesk;
import com.vdi.batch.mds.helper.PopulateUserRequest;
import com.vdi.batch.mds.helper.PopulateUserRequestYTD;
import com.vdi.configuration.AppConfig;
import com.zaxxer.hikari.HikariDataSource;

import http.rest.model.Response;

@RestController
@RequestMapping("/api/v1/populate")
public class PopulateDataREST {

	private final Logger logger = LogManager.getLogger(PopulateDataREST.class);
	private AnnotationConfigApplicationContext ctx;

	@GetMapping("/saIncident")
	public ResponseEntity<Response> populatesAIncident() {

		logger.info("Execute BatchMDSPopulateIncident......");
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		PopulateIncident populateIncident = ctx.getBean(PopulateIncident.class);
		int dataSize = 0;
		try {
			populateIncident.populate();
			dataSize = populateIncident.getDataLength();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}

		logger.info("Execute BatchMDSPopulateIncident finished......");

		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("stagingSize", dataSize);

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"populate data for incident is finished", mapData);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("sdIncident")
	public ResponseEntity<Response> populateSdIncident() {

		logger.info("Execute BatchMDSPopulateServiceDesk......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		PopulateServiceDesk populateServiceDesk = ctx.getBean(PopulateServiceDesk.class);
		int dataSize = 0;
		try {
			populateServiceDesk.populate();
			dataSize = populateServiceDesk.getDataLength();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}

		logger.info("Execute BatchMDSPopulateServiceDesk finished......");

		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("stagingSize", dataSize);

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"populate data for service desk incident is finished", mapData);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	@GetMapping("sdUreq")
	public ResponseEntity<Response> populateSdUreq() {
		logger.info("Execute BatchMDSPopulateUserRequest......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		int dataSize = 0;
		try {
			PopulateUserRequest populateUserRequest = ctx.getBean(PopulateUserRequest.class);
			populateUserRequest.populate();
			dataSize = populateUserRequest.getDataLength();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}

		logger.info("Execute BatchMDSPopulateUserRequest finished......");
		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("stagingSize", dataSize);

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"populate data for service desk user request is finished", mapData);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}
	
	@GetMapping("problemChange")
	public ResponseEntity<Response> populateProblemChange() {
		logger.info("Execute BatchPopulateProblemChange......");
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		int dataProblemSize=0;
		int dataChangeSize=0;
		try {
			PopulateProblemChange populate = ctx.getBean("populateProblemChange", PopulateProblemChange.class);
			populate.populate();
			dataProblemSize = populate.getProblemDataSize();
			dataChangeSize = populate.getChangeDataSize();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}		
		
		logger.info("Execute BatchPopulateProblemChange finished......");
		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("stagingProblemSize", dataProblemSize);
		mapData.put("stagingChangeSize", dataChangeSize);

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"populate data for problem-change is finished", mapData);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
		
	}
	
	@GetMapping("urYtd")
	public ResponseEntity<Response> populateUrYtd() {
		logger.info("Execute BatchMDSPopulateURYTD......");

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		int size=0;
		try {
			PopulateUserRequestYTD populateUR = ctx.getBean(PopulateUserRequestYTD.class);
			populateUR.populate();
			size = populateUR.getDataLength();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDataSource();
		}
		
		logger.info("Execute BatchMDSPopulateURYTD finished......");
		
		String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("stagingSize", size);

		Response response = new Response(this.getClass().getName() + '.' + nameofCurrMethod,
				"populate data for ureq YTD is finished", mapData);

		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
	}

	private void closeDataSource() {

		HikariDataSource hds = ctx.getBean("dataSource", HikariDataSource.class);
		logger.info("close datasource");
		logger.info(hds.getPoolName() + "-" + hds.getJdbcUrl());
		try {
			hds.close();
		} catch (Exception e) {
			logger.info("Error closing datasource ");
			e.printStackTrace();
		}

	}

}
