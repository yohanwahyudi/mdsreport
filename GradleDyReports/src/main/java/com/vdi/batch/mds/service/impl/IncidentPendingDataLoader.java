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
import com.vdi.model.IncidentPending;
import com.vdi.tools.IOToolsService;
import com.vdi.tools.component.SanitizeString;

@Service("incidentPendingDLService")
public class IncidentPendingDataLoader implements ItopMDSDataLoaderService {
	
	private final static Logger logger = LogManager.getLogger(IncidentPendingDataLoader.class);
	
	private List<IncidentPending> allPendingList;
	
	@Autowired
	private IOToolsService ioToolsService;

	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private SanitizeString sanitize;
	
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
		Elements rows = parseTableTr(ioToolsService.readUrl(appConfig.getMdsHttpPendingIncidentUrl()));
		
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
		
		logger.info("pending incident size:" +input.size());
		
		List<IncidentPending> temp = mapListToStaging(input);
		
		allPendingList = new ArrayList<IncidentPending>();
		
		for (Iterator<?> iterator = (Iterator<?>) temp.iterator(); iterator.hasNext();) {
			IncidentPending pending = (IncidentPending) iterator.next();
			allPendingList.add(pending);
		}
		
		return allPendingList;
	}
	
	public List<IncidentPending>mapListToStaging(List<?> input) {
		
		List<IncidentPending> temp = new ArrayList<IncidentPending>();
		
		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();
			
			IncidentPending pending = new IncidentPending();
			pending = mapIncidentPending(row);
			temp.add(pending);
			
		}
		
		return temp;
		
	}
	
	public IncidentPending mapIncidentPending(List<String> row) {
		
		IncidentPending pending = new IncidentPending();
		pending.setRef(row.get(0));
		pending.setOrganizationName(row.get(1));
		pending.setCallerLastName(row.get(2));
		pending.setTeamEmail(row.get(3));
		pending.setAgentLastName(row.get(4));
		pending.setTitle(row.get(5));
		//pending.setDescription(row.get(6));
		pending.setStartDate(row.get(7));
		pending.setStartTime(row.get(8));
		pending.setEndDate(row.get(9));
		pending.setEndTime(row.get(10));
		pending.setLastupdateDate(row.get(11));
		pending.setLastupdateTime(row.get(12));
		pending.setCloseDate(row.get(13));
		pending.setCloseTime(row.get(14));
		pending.setPrivateLog(row.get(15));
		pending.setStatus(row.get(16));
		pending.setImpact(row.get(17));
		pending.setPriority(row.get(18));
		pending.setUrgency(row.get(19));
		pending.setOrigin(row.get(20));
		pending.setServiceName(row.get(21));
		pending.setServicesubcategoryName(row.get(22));
		pending.setHotFlag(row.get(23));
		pending.setHotReason(row.get(24));
		pending.setAssignmentDate(row.get(25));
		pending.setAssignmentTime(row.get(26));
		pending.setResolutionDate(row.get(27));
		pending.setResolutionTime(row.get(28));
		pending.setLastPendingDate(row.get(29));
		pending.setLastPendingTime(row.get(30));
		pending.setCumulatedPending(row.get(31));
		pending.setTto(row.get(32));
		pending.setTtr(row.get(33));
		pending.setResolutionDelay(row.get(34));
		pending.setResolutionCode(row.get(35));
		pending.setSolution(row.get(36));
		pending.setPendingReason(row.get(37));
		pending.setParentIncidentRef(row.get(38));
		pending.setParentProblemRef(row.get(39));
		pending.setParentChangeRef(row.get(40));
		//pending.setPublicLog(row.get(41));
		//pending.setUserSatisfaction(row.get(42));
		//pending.setUserComment(row.get(43));
		pending.setOrganizationFullname(row.get(44));
		pending.setOrganizationObsolete(row.get(45));
		pending.setCallerFullname(row.get(46));
		pending.setCallerObsolete(row.get(47));
		pending.setTeamFullname(row.get(48));
		pending.setTeamObsolete(row.get(49));
		pending.setAgentFullname(row.get(50));
		pending.setAgentObsolete(row.get(51));
		pending.setServiceFullname(row.get(52));
		pending.setServicesubcategoryFullname(row.get(53));
		pending.setParentIncidentFullname(row.get(54));
		pending.setParentProblemidFullname(row.get(55));
		pending.setParentChangeFullname(row.get(56));
		pending.setParentChangeTicketSub(row.get(57));
		pending.setOrganization(row.get(58));
		pending.setCaller(row.get(59));
		pending.setTeam(row.get(60));
		pending.setAgent(row.get(61));
		pending.setService(row.get(62));
		pending.setServiceSubcategory(row.get(63));
		pending.setParentIncident(row.get(64));
		pending.setParentProblem(row.get(65));
		pending.setParentChange(row.get(66));

		return pending;
		
	}



	@Override
	public List getStagingAllByFile() {
		
		return null;
	}

}
