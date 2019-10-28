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
import com.vdi.model.Change;
import com.vdi.tools.IOToolsService;

@Service("changeDataLoaderService")
public class ChangeDataLoaderImpl implements ItopMDSDataLoaderService {

	private static final Logger logger = LogManager.getLogger(ChangeDataLoaderImpl.class);
	private final String HTML_REGEX_CLEAR_TAG = "<[^<>]+>";
	private final String HTML_ENTITY_CLEAR = "(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+";
	private final String UNACCENT_CLEAR = "[^\\p{Print}]";

	@Autowired
	private IOToolsService ioToolsService;

	@Autowired
	private AppConfig appConfig;

	public Elements parseTableTr(String data) {
		Elements rowsData;
		Document doc = Jsoup.parse(data);
		Element table = doc.select("table").get(0);
		rowsData = table.select("tr");

		return rowsData;
	}

	@Override
	public List<List<String>> loadTrToListVisionetByUrl() {
		
		logger.debug("Load Change List from URL");

		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl(appConfig.getMdsHttpChangeUrl()));
		
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

	@Override
	public List getStagingAllByURL() {
		
		List<List<String>> input = loadTrToListVisionetByUrl();
		List<Change> changeList = mapListToStaging(input);		

		logger.debug("finish load change from url");
		
		return changeList;
	}

	@Override
	public List getStagingAllByFile() {

		return null;
	}
	
	public List<Change> mapListToStaging(List<?> input){
		
		List<Change> changeList =  new ArrayList<Change>();
		
		for(Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			
			List<String> row = iterator.next();
			
			Change change = new Change();
			change = mapChange(row);
			
			changeList.add(change);
			
		}
		
		return changeList;
		
	}

	private Change mapChange(List<String> row) {
		
		Change change = new Change();
		change.setRef(row.get(0));
		change.setTitle(row.get(1));
		change.setStatus(row.get(2));
		change.setOrg_name(row.get(3));
		change.setCaller_name(row.get(4));
		change.setRequestor_id_friendlyname(row.get(5));
		change.setAgent_id_friendlyname(row.get(6));
		change.setTeam_id_friendlyname(row.get(7));
		change.setSupervisor_id_friendlyname(row.get(8));
		change.setSupervisor_group_id_friendlyname(row.get(9));
		change.setManager_id_friendlyname(row.get(10));
		change.setManager_group_id_friendlyname(row.get(11));
		change.setStart_date(row.get(12));
		change.setStart_time(row.get(13));
		change.setEnd_date(row.get(14));
		change.setEnd_time(row.get(15));
		change.setLast_update(row.get(16));
		change.setClose_date(row.get(18));
//		change.setRelated_incident_list(row.get(19));
//		change.setRelated_problems_list(row.get(20));
//		change.setRelated_request_list(row.get(21));
		change.setCreation_date(row.get(23));
		change.setCreation_time(row.get(24));
		change.setUserinfo(row.get(25));

		return change;
	}

}
