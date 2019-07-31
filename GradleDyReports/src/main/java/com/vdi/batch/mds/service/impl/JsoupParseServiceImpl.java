package com.vdi.batch.mds.service.impl;

/**
 * note: future, add regex: <[^<>]+> for html tag filter (best solution)
 * or <.+?> 
 */

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.vdi.batch.mds.service.JsoupParseService;
import com.vdi.configuration.AppConfig;
import com.vdi.configuration.PropertyNames;
import com.vdi.model.Incident;
import com.vdi.tools.IOToolsService;
import com.vdi.tools.component.SanitizeString;

@Service("jsoupParseServiceDailyMDS")
// @ComponentScan({ "com.vdi.batch.mds.service", "com.vdi.tools",
// "com.vdi.configuration" })

public class JsoupParseServiceImpl implements JsoupParseService {

	private final static Logger logger = LogManager.getLogger(JsoupParseServiceImpl.class);

	private List<Incident> deadlineList;
	private List<Incident> assignList;
	private List<Incident> pendingList;

	@Autowired
	private IOToolsService ioToolsService;

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private SanitizeString sanitize;

	@Autowired
	public JsoupParseServiceImpl(AppConfig appConfig) {
	}

	public Elements parseTableTr(String data) {
		Elements rowsData;
		Document doc = Jsoup.parse(data);
		Element table = doc.select("table").get(0);
		rowsData = table.select("tr");

		return rowsData;
	}

	@Override
	public List<List<String>> jsoupTrToListVisionetByUrl() {

		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl());

		if (rows != null && rows.size() > 0) {

			String[] organization = appConfig.getOrganization();

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

		logger.debug("records: " + records.size());

		return records;
	}

	public List<List<String>> jsoupTrToListVisionetByFile() {

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

	// @Bean(name="getIncidentAllByURLDaily")
	@Override
	public List<Incident> getIncidentAllByURL() {
		List<List<String>> input = jsoupTrToListVisionetByUrl();
		List<Incident> listIncident = JsoupMapperListtoIncident(input);
		List<Incident> temp = new ArrayList<Incident>();

		deadlineList = new ArrayList<Incident>();
		assignList = new ArrayList<Incident>();
		pendingList = new ArrayList<Incident>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date dtNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Iterator<?> iterator = (Iterator<?>) listIncident.iterator(); iterator.hasNext();) {
			Incident incident = (Incident) iterator.next();
			String status = incident.getStatus();
			String stDate = incident.getStart_date();
			String deadline = incident.getTtr_deadline();

			try {
				boolean isDeadline = isDeadline(deadline);
				boolean added = Boolean.FALSE;

				if (isDeadline && (status.trim().equalsIgnoreCase(PropertyNames.ASSIGNED)
						|| status.trim().equalsIgnoreCase(PropertyNames.PENDING)
						|| status.equalsIgnoreCase(PropertyNames.ESCALATED_TTR))) {
					temp.add(incident);
					added = Boolean.TRUE;

					// debug class with reflection
					// for(Field field : incident.getClass().getDeclaredFields()) {
					// field.setAccessible(true);
					// String name = field.getName();
					// Object val = field.get(incident);
					//
					// logger.debug(name+" : " +val);
					// }

					// deadline line
					deadlineList.add(incident);
				}

				if (dtNow.compareTo(sdf.parse(stDate)) == 0) {
					if (status.trim().equalsIgnoreCase(PropertyNames.ASSIGNED)
							|| status.trim().equalsIgnoreCase(PropertyNames.PENDING)) {
						if (!added) {
							temp.add(incident);
							if (incident.getStatus().equalsIgnoreCase(PropertyNames.PENDING)) {
								pendingList.add(incident);
							} else {
								assignList.add(incident);
							}
						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		logger.debug("All Incident Daily list size: " + temp.size());

		return temp;

	}

	// @Bean(name="getIncidentAllByFileDaily")
	@Override
	public List<Incident> getIncidentAllByFile() {
		List<List<String>> input = jsoupTrToListVisionetByFile();
		List<Incident> listIncident = JsoupMapperListtoIncident(input);
		List<Incident> temp = new ArrayList<Incident>();

		deadlineList = new ArrayList<Incident>();
		assignList = new ArrayList<Incident>();
		pendingList = new ArrayList<Incident>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date dtNow = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Iterator<?> iterator = (Iterator<?>) listIncident.iterator(); iterator.hasNext();) {
			Incident incident = (Incident) iterator.next();
			String status = incident.getStatus();
			String stDate = incident.getStart_date();
			String deadline = incident.getTtr_deadline();

			try {
				boolean isDeadline = isDeadline(deadline);
				boolean added = Boolean.FALSE;

				if (isDeadline && (status.trim().equalsIgnoreCase(PropertyNames.ASSIGNED)
						|| status.trim().equalsIgnoreCase(PropertyNames.PENDING)
						|| status.equalsIgnoreCase(PropertyNames.ESCALATED_TTR))) {
					temp.add(incident);
					added = Boolean.TRUE;

					// deadline line
					deadlineList.add(incident);
				}

				if (dtNow.compareTo(sdf.parse(stDate)) == 0) {
					if (status.trim().equalsIgnoreCase(PropertyNames.ASSIGNED)
							|| status.trim().equalsIgnoreCase(PropertyNames.PENDING)) {
						if (!added) {
							temp.add(incident);
							added = Boolean.TRUE;
							if (incident.getStatus().equalsIgnoreCase(PropertyNames.PENDING)) {
								pendingList.add(incident);
							} else {
								assignList.add(incident);
							}
						}
					}
				}else {
					if (status.trim().equalsIgnoreCase(PropertyNames.PENDING)) {
						pendingList.add(incident);
					} else if (status.trim().equalsIgnoreCase(PropertyNames.ASSIGNED)) {
						assignList.add(incident);
					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		logger.debug("All Incident Daily list size: " + temp.size());

		return temp;

	}

	public Incident mapIncident(List<String> row) {

		Incident incident = new Incident();
		incident.setRef(row.get(0));
		incident.setTitle(sanitize.getSanitizedString(row.get(1), 255));
		incident.setStatus(row.get(2));
		incident.setStart_date(row.get(3));
		incident.setStart_time(row.get(4));
		incident.setAssignment_date(row.get(5));
		incident.setAssignment_time(row.get(6));
		incident.setEnd_date(row.get(7));
		incident.setEnd_time(row.get(8));
		incident.setLastupdate_date(row.get(9));
		incident.setLastupdate_time(row.get(10));
		incident.setClose_date(row.get(11));
		incident.setClose_time(row.get(12));
		incident.setAgent_lastname(row.get(13));
		incident.setAgent_fullname(row.get(14));
		incident.setTeam(row.get(15));
		incident.setTeam_name(row.get(16));
		incident.setDescription(sanitize.getSanitizedString(row.get(17),4000));
		incident.setOrigin(row.get(18));
		incident.setLastpending_date(row.get(19));
		incident.setLastpending_time(row.get(20));
		incident.setCumulated_pending(row.get(21));
		incident.setPending_reason(row.get(22));
		incident.setParent_incident_ref(row.get(23));
		incident.setParent_problem_ref(row.get(24));
		incident.setParent_change_ref(row.get(25));
		incident.setIncident_organization_short(row.get(26));
		incident.setIncident_organization_name(row.get(27));
		incident.setAgent(row.get(28));
		incident.setPerson_first_name(row.get(29));
		incident.setPerson_last_name(row.get(30));
		incident.setPriority(row.get(31));
		incident.setResolution_delay(row.get(32));
		incident.setTto_over(row.get(33));
		incident.setTto_passed(row.get(34));
		incident.setTto_deadline(row.get(35));
		incident.setTtr_over(row.get(36));
		incident.setTtr_passed(row.get(37));
		incident.setTtr_deadline(row.get(38));
		incident.setStatus2(row.get(39));
		incident.setTeam_id(row.get(40));
		incident.setType(row.get(41));
		incident.setTto(row.get(42));
		incident.setTtr(row.get(43));
		incident.setSolution(row.get(44));
		incident.setPerson_full_name(row.get(45));

		incident.setEmail(sanitize.getSanitizedString(row.get(46), 255));

		incident.setPerson_org_short(row.get(47));
		incident.setPerson_org_name(row.get(48));
		incident.setUser_satisfaction(row.get(49));
		incident.setUser_comment(sanitize.getSanitizedString(row.get(50), 4000));
		incident.setResolution_date(row.get(51));
		incident.setResolution_time(row.get(52));
		incident.setHotflag(row.get(53));
		incident.setHotflag_reason(row.get(54));
		incident.setImpact(row.get(55));
		incident.setUrgency(row.get(56));

		return incident;
	}

	public boolean isDeadline(String deadline) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		boolean isDeadline = Boolean.FALSE;
		int deadlineThresshold = appConfig.getMdsDailyDeadlineThresholdDay();

		if (deadline != null && deadline != "") {
			Date dtTicket = sdf.parse(deadline);
			Date dtNow = new Date();

			long diff = dtTicket.getTime() - dtNow.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);

			if (diffDays < deadlineThresshold) {
				isDeadline = Boolean.TRUE;
			}
		}

		return isDeadline;
	}

	public List<Incident> JsoupMapperListtoIncident(List<?> input) {

		List<Incident> temp = new ArrayList<Incident>();

		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();

			Incident incident = new Incident();
			incident = mapIncident(row);

			temp.add(incident);
		}

		return temp;

	}

	@Override
	// @Bean
	public List<Incident> getIncidentDeadline() {

		return deadlineList;
	}

	@Override
	// @Bean
	public List<Incident> getIncidentAssign() {
		return assignList;
	}

	@Override
	public List<Incident> getIncidentPending() {
		return pendingList;
	}

}
