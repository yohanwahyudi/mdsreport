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
import com.vdi.model.staging.Staging;
import com.vdi.tools.IOToolsService;
import com.vdi.tools.component.SanitizeString;
//this is comment
@Service("itopMDSDataLoaderService")
public class ItopMDSDataLoaderServiceImpl implements ItopMDSDataLoaderService {

	private final static Logger logger = LogManager.getLogger(ItopMDSDataLoaderServiceImpl.class);
	private final String HTML_REGEX_CLEAR_TAG = "<[^<>]+>";
	private final String HTML_ENTITY_CLEAR = "(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+";
	private final String UNACCENT_CLEAR = "[^\\p{Print}]";

	@Autowired
	private IOToolsService ioToolsService;

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private SanitizeString sanitize;

	private List<Staging> allStagingList;
	private List<Staging> allStagingVisionetList;

	@Autowired
	public ItopMDSDataLoaderServiceImpl(AppConfig appconfig) {
		
	}

	public Elements parseTableTr(String data) {
		Elements rowsData;
		Document doc = Jsoup.parse(data);
		Element table = doc.select("table").get(0);
		rowsData = table.select("tr");

		return rowsData;
	}

	@Override
	public List<List<String>> loadTrToListVisionetByUrl() {
		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl());

		String[] organization = appConfig.getOrganization();

		if (rows != null && rows.size() > 0) {

			String organization1 = organization[0];
			String organization2 = organization[1];
			int organizationCol = appConfig.getOrganizationCol();

			for (int i = 0; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements cols = row.select("td");

				if (organization1.equalsIgnoreCase((cols.get(organizationCol)).ownText())
						|| organization2.equalsIgnoreCase((cols.get(organizationCol)).ownText())) {

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
		}

		return records;
	}

	public List<List<String>> loadTrToListAllByUrl() {
		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl());

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
	
	public List<Staging> getStagingVisionetByURL() {
		List<List<String>> input = loadTrToListVisionetByUrl();
		List<Staging> temp = mapListToStaging(input);
		allStagingVisionetList = new ArrayList<Staging>();

		for (Iterator<?> iterator = (Iterator<?>) temp.iterator(); iterator.hasNext();) {
			Staging staging = (Staging) iterator.next();

			allStagingList.add(staging);

		}
		logger.debug("All Incident url Daily list Visionet size: " + allStagingList.size());

		return allStagingVisionetList;
	}

	@Override
	public List<Staging> getStagingAllByURL() {
		List<List<String>> input = loadTrToListAllByUrl();
		List<Staging> temp = mapListToStaging(input);
		allStagingList = new ArrayList<Staging>();

		for (Iterator<?> iterator = (Iterator<?>) temp.iterator(); iterator.hasNext();) {
			Staging staging = (Staging) iterator.next();

			allStagingList.add(staging);

		}
		logger.debug("All Incident url Daily list size: " + allStagingList.size());

		return allStagingList;
	}

	@Override
	public List<List<String>> loadTrToListVisionetByFile() {
		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readFile());

		String[] organization = appConfig.getOrganization();

		if (rows != null && rows.size() > 0) {

			String organization1 = organization[0];
			String organization2 = organization[1];
			int organizationCol = appConfig.getOrganizationCol();

			for (int i = 0; i < rows.size(); i++) {
				Element row = rows.get(i);
				Elements cols = row.select("td");

				if (organization1.equalsIgnoreCase((cols.get(organizationCol)).ownText())
						|| organization2.equalsIgnoreCase((cols.get(organizationCol)).ownText())) {

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
		}

		return records;
	}

	@Override
	public List<Staging> getStagingAllByFile() {
		List<List<String>> input = loadTrToListVisionetByFile();
		List<Staging> temp = mapListToStaging(input);
		allStagingList = new ArrayList<Staging>();

		for (Iterator<?> iterator = (Iterator<?>) temp.iterator(); iterator.hasNext();) {
			Staging staging = (Staging) iterator.next();

			allStagingList.add(staging);

		}
		logger.debug("All Incident Daily list size: " + allStagingList.size());

		return allStagingList;
	}

	public Staging mapStaging(List<String> row) {

		Staging staging = new Staging();
		staging.setRef(row.get(0));
		
		staging.setTitle(sanitize.getSanitizedString(row.get(1), 4000));
		
		staging.setStatus(row.get(2));
		staging.setStart_date(row.get(3));
		staging.setStart_time(row.get(4));
		staging.setAssignment_date(row.get(5));
		staging.setAssignment_time(row.get(6));
		staging.setEnd_date(row.get(7));
		staging.setEnd_time(row.get(8));
		staging.setLastupdate_date(row.get(9));
		staging.setLastupdate_time(row.get(10));
		staging.setClose_date(row.get(11));
		staging.setClose_time(row.get(12));
		staging.setAgent_lastname(row.get(13));
		staging.setAgent_fullname(row.get(14));
		staging.setTeam(row.get(15));
		staging.setTeam_name(row.get(16));
		
		//staging.setDescription(sanitize.getSanitizedString(row.get(17), 4000));

		staging.setOrigin(row.get(18));
		staging.setLastpending_date(row.get(19));
		staging.setLastpending_time(row.get(20));
		staging.setCumulated_pending(row.get(21));

		staging.setPending_reason(sanitize.getSanitizedString(row.get(22), 4000));

		staging.setParent_incident_ref(row.get(23));
		staging.setParent_problem_ref(row.get(24));
		staging.setParent_change_ref(row.get(25));
		staging.setIncident_organization_short(row.get(26));
		staging.setIncident_organization_name(row.get(27));
		staging.setAgent(row.get(28));
		staging.setPerson_first_name(row.get(29));
		staging.setPerson_last_name(row.get(30));
		staging.setPriority(row.get(31));
		staging.setResolution_delay(row.get(32));
		staging.setTto_over(row.get(33));
		staging.setTto_passed(row.get(34));
		staging.setTto_deadline(row.get(35));
		staging.setTtr_over(row.get(36));
		staging.setTtr_passed(row.get(37));
		staging.setTtr_deadline(row.get(38));
		staging.setStatus2(row.get(39));
		staging.setTeam_id(row.get(40));
		staging.setType(row.get(41));
		staging.setTto(row.get(42));
		staging.setTtr(row.get(43));

		//staging.setSolution(sanitize.getSanitizedString(row.get(44), 4000));

		staging.setPerson_full_name(row.get(45));
		
		staging.setEmail(sanitize.getSanitizedString(row.get(46), 255));
		
		staging.setPerson_org_short(row.get(47));
		staging.setPerson_org_name(row.get(48));
		staging.setUser_satisfaction(row.get(49));
		
//		String userComment = row.get(50);
//		userComment = userComment.replaceAll(HTML_REGEX_CLEAR_TAG,"");
//		userComment = userComment.replaceAll(HTML_ENTITY_CLEAR, " ");
//		userComment = userComment.replaceAll(UNACCENT_CLEAR, "");
//		if (userComment.length() > 4000) {
//			userComment = userComment.substring(0, 4000);
//		}
		
		//staging.setUser_comment(sanitize.getSanitizedString(row.get(50), 4000));
		
		staging.setResolution_date(row.get(51));
		staging.setResolution_time(row.get(52));
		staging.setHotflag(row.get(53));
		staging.setHotflag_reason(row.get(54));
		staging.setImpact(row.get(55));
		staging.setUrgency(row.get(56));
		
		return staging;
	}

	public List<Staging> mapListToStaging(List<?> input) {

		List<Staging> temp = new ArrayList<Staging>();

		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();

			Staging staging = new Staging();
			staging = mapStaging(row);

			temp.add(staging);
		}

		return temp;

	}

}
