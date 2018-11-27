package com.vdi.batch.mds.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.Problem;
import com.vdi.tools.IOToolsService;

@Service("problemDataLoaderService")
public class ProblemDataLoaderImpl implements ItopMDSDataLoaderService {

	private static final Logger logger = LogManager.getLogger(ProblemDataLoaderImpl.class);
	private final String HTML_REGEX_CLEAR_TAG = "<[^<>]+>";
	private final String HTML_ENTITY_CLEAR = "(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+";
	private final String UNACCENT_CLEAR = "[^\\p{Print}]";
	
	@Autowired
	private IOToolsService ioToolsService;

	@Autowired
	private AppConfig appConfig;
	
	@Override
	public List<List<String>> loadTrToListVisionetByUrl() {

		logger.debug("load problem from url....");
		
		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl(appConfig.getMdsHttpProblemUrl()));
		
		if (rows != null && rows.size() > 0) {

			for (int i = 1; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements cols = row.select("td");

				List<String> record = new ArrayList<String>();

				for (int j = 0; j < cols.size(); j++) {
					Element col = cols.get(j);
					List<?> temp = new ArrayList<Object>();

					int size = col.childNodesCopy().size();

					if (size > 1) {
						StringBuffer sb = new StringBuffer();
						temp = col.childNodesCopy();

						Iterator<?> iter = temp.iterator();
						while (iter.hasNext()) {
							sb.append(iter.next());
						}

						record.add(sb.toString());
					} else if (size < 1) {
						record.add("");

					} else {
						temp = col.childNodes();
						Iterator<?> iter = temp.iterator();
						while (iter.hasNext()) {
							Object value = iter.next();

							if (value instanceof TextNode) {
								record.add(value.toString());
							} else if (value instanceof Element) {
								Element element = (Element) value;

								if (element != null) {
									int size1 = element.childNodesCopy().size();
									List<?> temp1 = new ArrayList<Object>();

									if (size1 < 1) {
										record.add("");
									} else {
										StringBuffer sb1 = new StringBuffer();
										temp1 = new ArrayList<Object>();
										temp1 = element.childNodesCopy();

										Iterator<?> iter1 = temp1.iterator();
										while (iter1.hasNext()) {
											sb1.append(iter1.next().toString());
										}

										record.add(sb1.toString());

									}

								}

							}
						}
					}

				}
				records.add(record);

			}
		}

		return records;
		
	}

	@Override
	public List<List<String>> loadTrToListVisionetByFile() {

		return null;
	}
	
	private Elements parseTableTr(String data) {
		Elements rowsData;
		Document doc = Jsoup.parse(data);
		Element table = doc.select("table").get(0);
		rowsData = table.select("tr");

		return rowsData;
	}

	@Override
	public List getStagingAllByURL() {

		List<List<String>> input = loadTrToListVisionetByUrl();
		List<Problem> problemListMap = mapListToStaging(input);
		
		List<Problem> allProblemList  = new ArrayList<Problem>();
		
		for(Iterator<?> iterator = (Iterator<?>)problemListMap.iterator();iterator.hasNext();) {
			Problem problem = new Problem();
			problem = (Problem) iterator.next();
			
			allProblemList.add(problem);
		}
		
		logger.debug("finish load problem from url....");
		
		return allProblemList;
	}

	@Override
	public List getStagingAllByFile() {

		return null;
	}
	
	private List<Problem> mapListToStaging(List<?> input){
		
		List<Problem> temp = new ArrayList<Problem>();
		
		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();
			
			Problem problem = new Problem();
			problem = mapStaging(row);
			temp.add(problem);
		
		}
		
		return temp;
		
	}
	
	private Problem mapStaging(List<String> row) {
		
		Problem problem = new Problem();
		problem.setRef(row.get(0));
		problem.setTitle(row.get(1));
		problem.setStatus(row.get(2));
		problem.setPriority(row.get(3));
		problem.setOrg_id(row.get(4));
		problem.setOrg_name(row.get(5));
		problem.setCaller_name(row.get(6));
		problem.setTeam_id_friendlyname(row.get(7));
		problem.setAgent_id_friendlyname(row.get(8));
		problem.setRelated_change_id_friendlyname(row.get(9));
		problem.setStart_date(row.get(10));
		problem.setStart_time(row.get(11));
		problem.setEnd_date(row.get(12));
		problem.setEnd_time(row.get(13));
		problem.setLast_update(row.get(14));
		problem.setClose_date(row.get(16));
		problem.setClose_time(row.get(17));
		problem.setAssignment_date(row.get(18));
		problem.setResolution_date(row.get(20));
		
		return problem;
		
	}

}
