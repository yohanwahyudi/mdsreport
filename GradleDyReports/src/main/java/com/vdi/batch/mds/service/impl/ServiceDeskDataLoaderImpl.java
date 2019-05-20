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
import com.vdi.model.staging.StagingServiceDesk;
import com.vdi.tools.IOToolsService;

@Service("serviceDeskDataLoaderService")
public class ServiceDeskDataLoaderImpl implements ItopMDSDataLoaderService{
	
	private static final Logger logger = LogManager.getLogger(ServiceDeskDataLoaderImpl.class);
	private final String HTML_REGEX_CLEAR_TAG = "<[^<>]+>";
	private final String HTML_ENTITY_CLEAR = "(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+";
	private final String UNACCENT_CLEAR = "[^\\p{Print}]";
	
	private List<StagingServiceDesk> allStagingList;
	
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
		return null;
	}

	@Override
	public List<List<String>> loadTrToListVisionetByFile() {
		return null;
	}
	
	public List<List<String>> loadTrToListAllByUrl() {
		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl(appConfig.getMdsHttpSdUrl()));

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
	public List<StagingServiceDesk> getStagingAllByURL() {
		List<List<String>> input = loadTrToListAllByUrl();
		List<StagingServiceDesk> temp = mapListToStaging(input);
		allStagingList = new ArrayList<StagingServiceDesk>();

		for (Iterator<?> iterator = (Iterator<?>) temp.iterator(); iterator.hasNext();) {
			StagingServiceDesk staging = (StagingServiceDesk) iterator.next();

			allStagingList.add(staging);

		}
		logger.debug("All Incident url Daily list size: " + allStagingList.size());

		return allStagingList;
	}

	@Override
	public List<StagingServiceDesk> getStagingAllByFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public StagingServiceDesk mapStaging(List<String> row) {
		
		StagingServiceDesk sdStaging = new StagingServiceDesk();
		sdStaging.setScalar_date(row.get(0));
		sdStaging.setScalar_time(row.get(1));
		sdStaging.setScalar_user(row.get(2));
		sdStaging.setScalar_objectclass(row.get(3));
		sdStaging.setScalar_objectid(row.get(4));
		sdStaging.setScalar_attribute(row.get(5));
		sdStaging.setScalar_previousvalue(row.get(6));
		sdStaging.setScalar_newvalue(row.get(7));
		sdStaging.setScalar_type(row.get(8));
		sdStaging.setIncident_ref(row.get(9));
		
		
		String title = row.get(10);
		title = title.replaceAll(HTML_REGEX_CLEAR_TAG, "");
		title = title.replaceAll(HTML_ENTITY_CLEAR, " ");
		title = title.replaceAll(UNACCENT_CLEAR, "");
		if (title.length() > 4000) {
			title = title.substring(0, 4000);
		}
		sdStaging.setIncident_title(title);
		
		sdStaging.setIncident_startdate(row.get(11));
		sdStaging.setIncident_starttime(row.get(12));
		sdStaging.setIncident_enddate(row.get(13));
		sdStaging.setIncident_enddatetime(row.get(14));
		sdStaging.setIncident_assignmentdate(row.get(15));
		sdStaging.setIncident_assignmenttime(row.get(16));
		sdStaging.setIncident_lastupdatedate(row.get(17));
		sdStaging.setIncident_lastupdatetime(row.get(18));
		sdStaging.setIncident_closedate(row.get(19));
		sdStaging.setIncident_closetime(row.get(20));
		sdStaging.setIncident_caller(row.get(21));
		sdStaging.setIncident_agentName(row.get(22));
		sdStaging.setIncident_agent(row.get(23));
		sdStaging.setIncident_teamName(row.get(24));
		sdStaging.setIncident_team(row.get(25));
		
		String description = row.get(26);
		description = description.replaceAll(HTML_REGEX_CLEAR_TAG,"");
		description = description.replaceAll(HTML_ENTITY_CLEAR, " ");
		description = description.replaceAll(UNACCENT_CLEAR, "");
		if (description.length() > 4000) {
			description = description.substring(0, 4000);
		}		
		sdStaging.setIncident_description(description);
		
		
		sdStaging.setIncident_status(row.get(27));
		sdStaging.setIncident_priority(row.get(28));
		sdStaging.setIncident_origin(row.get(29));
		sdStaging.setIncident_lastpendingdate(row.get(30));
		sdStaging.setIncident_lastpendingtime(row.get(31));
		sdStaging.setIncident_cumulatedpending(row.get(32));
		
		String pendingReason = row.get(33);
		pendingReason = pendingReason.replaceAll(HTML_REGEX_CLEAR_TAG,"");
		pendingReason = pendingReason.replaceAll(HTML_ENTITY_CLEAR, " ");
		pendingReason = pendingReason.replaceAll(UNACCENT_CLEAR, "");
		if (pendingReason.length() > 4000) {
			pendingReason = pendingReason.substring(0, 4000);
		}
		sdStaging.setIncident_pendingreason(pendingReason);
		
		sdStaging.setIncident_parentincidentref(row.get(34));
		sdStaging.setIncident_ref2(row.get(35));
		sdStaging.setIncident_parentchangeref(row.get(36));
		sdStaging.setIncident_organization(row.get(37));
		sdStaging.setIncident_organizationName(row.get(38));
		sdStaging.setIncident_slattopassed(row.get(39));
		sdStaging.setIncident_slattoover(row.get(40));
		sdStaging.setIncident_ttoDeadline(row.get(41));
		sdStaging.setIncident_slattrpassed(row.get(42));
		sdStaging.setIncident_slattrover(row.get(43));
		sdStaging.setIncident_ttrDeadline(row.get(44));
		sdStaging.setIncident_resolutiondelay(row.get(45));
		
		String solution = row.get(46);
		solution = solution.replaceAll(HTML_REGEX_CLEAR_TAG,"");
		solution = solution.replaceAll(HTML_ENTITY_CLEAR, " ");
		solution = solution.replaceAll(UNACCENT_CLEAR, "");
		if (solution.length() > 4000) {
			solution = solution.substring(0, 4000);
		}
		sdStaging.setIncident_solution(solution);
		
		sdStaging.setIncident_tto(row.get(47));
		sdStaging.setIncident_ttr(row.get(48));
		
		sdStaging.setPerson_fullname(row.get(49));
		sdStaging.setPerson_organizationname(row.get(50));
		sdStaging.setPerson_organization(row.get(51));
		
		sdStaging.setIncident_usersatisfaction(row.get(52));
		
		String userComment = row.get(53);
		userComment = userComment.replaceAll(HTML_REGEX_CLEAR_TAG,"");
		userComment = userComment.replaceAll(HTML_ENTITY_CLEAR, " ");
		userComment = userComment.replaceAll(UNACCENT_CLEAR, "");
		if (userComment.length() > 4000) {
			userComment = userComment.substring(0, 4000);
		}
		sdStaging.setIncident_usercomment(userComment);
		
		return sdStaging;
	}
	
	public List<StagingServiceDesk> mapListToStaging(List<?> input) {
		List<StagingServiceDesk> temp = new ArrayList<StagingServiceDesk>();
		
		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();
			
			StagingServiceDesk staging = new StagingServiceDesk();
			staging = mapStaging(row);
			temp.add(staging);
		}
		
		return temp;
	}

}
