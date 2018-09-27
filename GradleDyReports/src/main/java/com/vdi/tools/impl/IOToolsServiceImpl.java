package com.vdi.tools.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.ConnectException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.vdi.configuration.AppConfig;
import com.vdi.model.Incident;
import com.vdi.tools.IOToolsService;

@Service("ioTools")
@ComponentScan({"com.vdi.tools","com.vdi.configuration"})
public class IOToolsServiceImpl implements IOToolsService{
	
	private static final Logger logger = LogManager.getLogger(IOToolsServiceImpl.class);
//	private static final double bufferSize = (Math.pow(1024, 2));
	
	private String file;
	private int timeout;
	private int maxPool;
	private int maxPerRoute;
	private String url;
	
	@Autowired	
	public IOToolsServiceImpl(AppConfig appConfig) {
		
		logger.info("enter cons IOTools");
		
		this.file = appConfig.getMdsFileSource();
		this.timeout = appConfig.getHttpTimeout();
		this.maxPool = appConfig.getHttpMaxPool();
		this.maxPerRoute = appConfig.getHttpMaxPerRoute();
		this.url = appConfig.getMdsHttpUrl();
	}
	
	//@Bean("readFile")
	public String readFile() {		
		logger.debug("read file "+file);
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String str = "";

			while ((str = br.readLine()) != null) {
				sBuilder.append(str);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sBuilder.toString();
	}
	
	public String readFile(String filePath) {
		logger.debug("read file "+filePath);
		StringBuilder sBuilder = new StringBuilder();
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(filePath);
			br = new BufferedReader(fileReader);

			String str = "";

			while ((str = br.readLine()) != null) {
				sBuilder.append(str);
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				br.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return sBuilder.toString();
	}
	
	private void writeBuffered(List<String> records, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);

			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			write(records, bufferedWriter);
		} finally {
			// comment this out if you want to inspect the files afterward
			// file.delete();
		}
	}
	
	public void writeBuffered(String str, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bufWriter = new BufferedWriter(fw, bufSize);
			
			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			
			write(str,bufWriter);
			
			logger.debug("write finished");
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void writeBufferedIncident(List<Incident> records, int bufSize, String fileName) throws IOException {
		File file = new File(fileName);
		BufferedWriter bufferedWriter = null;
		try {
			FileWriter fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter, bufSize);

			logger.debug("Writing buffered (buffer size: " + bufSize + ")... ");
			
			Writer writer = bufferedWriter;
			for(Incident incident : records) {
				writer.write(incident.getRef());
			}
			
		} finally {
			try {
				bufferedWriter.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void write(List<String> records, Writer writer) throws IOException {
		long start = System.currentTimeMillis();
		for (String record : records) {
			writer.write(record);
		}
		writer.flush();
		writer.close();
		long end = System.currentTimeMillis();
		logger.debug((end - start) / 1000f + " seconds");
	}
	
	private void write(String str, Writer writter) throws IOException {
		try {
			writter.write(str);
		} catch(IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			writter.flush();
			writter.close();
		}
		
	}
	
	//HTTP Tools
	public HttpClient init() {

		// set param
		RequestConfig.Builder builder = RequestConfig.custom().setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000);
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setMaxTotal(maxPool);
		pool.setDefaultMaxPerRoute(maxPerRoute);

		// http builder
		HttpClientBuilder client = HttpClientBuilder.create();
		client.setDefaultRequestConfig(builder.build());
		client.setConnectionManager(pool);

		return client.build();
	}
	
//	@Bean("readUrl")
	public String readUrl() {
		// init
		HttpClient client = init();
		HttpGet get = new HttpGet(url);
		StringBuilder sBuilder = new StringBuilder();

		try {
			HttpResponse resp = client.execute(get);
			logger.debug("response code : " + resp.getStatusLine().getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String line = "";

			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sBuilder.toString();
	}
	
	public String readUrl(String url) {
		// init
		HttpClient client = init();
		HttpGet get = new HttpGet(url);
		StringBuilder sBuilder = new StringBuilder();

		try {
			HttpResponse resp = client.execute(get);
			logger.debug("response code : " + resp.getStatusLine().getStatusCode());

			BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String line = "";

			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sBuilder.toString();
	}

}
