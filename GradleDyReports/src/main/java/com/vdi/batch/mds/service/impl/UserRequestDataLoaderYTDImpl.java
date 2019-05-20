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
import com.vdi.model.staging.StagingUserRequestYTD;
import com.vdi.tools.IOToolsService;

@Service("userRequestDataLoaderYTDService")
public class UserRequestDataLoaderYTDImpl implements ItopMDSDataLoaderService{
	
	private final static Logger logger = LogManager.getLogger(UserRequestDataLoaderYTDImpl.class);
	
	private final String HTML_REGEX_CLEAR_TAG = "<[^<>]+>";
	private final String HTML_ENTITY_CLEAR = "(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+";
	private final String UNACCENT_CLEAR = "[^\\p{Print}]";
	
	private List<StagingUserRequestYTD> allStagingList;

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

		List<List<String>> records = new ArrayList<List<String>>();
		Elements rows = parseTableTr(ioToolsService.readUrl(appConfig.getMdsHttpURYtdUrl()));

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getStagingAllByURL() {
		List<List<String>> input = loadTrToListVisionetByUrl();
		List<StagingUserRequestYTD> temp = mapListToStaging(input);
		allStagingList = new ArrayList<StagingUserRequestYTD>();

		for (Iterator<?> iterator = (Iterator<?>) temp.iterator(); iterator.hasNext();) {
			StagingUserRequestYTD staging = (StagingUserRequestYTD) iterator.next();

			allStagingList.add(staging);

		}
		logger.debug("All UR url Daily list size: " + allStagingList.size());

		return allStagingList;
	}

	@Override
	public List getStagingAllByFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public StagingUserRequestYTD mapStaging(List<String> row) {

		StagingUserRequestYTD ur = new StagingUserRequestYTD();
		ur.setRef(row.get(0));
		
		String title = row.get(1);
		title = title.replaceAll(HTML_REGEX_CLEAR_TAG, "");
		title = title.replaceAll(HTML_ENTITY_CLEAR, " ");
		title = title.replaceAll(UNACCENT_CLEAR, "");
		if (title.length() > 4000) {
			title = title.substring(0, 4000);
		}
		ur.setTitle(title);
		
		ur.setStatus(row.get(2));
		
		String pendingReason = row.get(3);
		pendingReason = pendingReason.replaceAll(HTML_REGEX_CLEAR_TAG, "");
		pendingReason = pendingReason.replaceAll(HTML_ENTITY_CLEAR, " ");
		pendingReason = pendingReason.replaceAll(UNACCENT_CLEAR, "");
		if (pendingReason.length() > 4000) {
			pendingReason = pendingReason.substring(0, 4000);
		}
		ur.setPendingReason(pendingReason);
		
		ur.setStartDate(row.get(4));
		ur.setStartTime(row.get(5));
		ur.setAssignmentDate(row.get(6));
		ur.setAssignmentTime(row.get(7));
		ur.setEndDate(row.get(8));
		ur.setEndTime(row.get(9));
		ur.setLastUpdateDate(row.get(10));
		ur.setLastUpdateTime(row.get(11));
		ur.setCloseDate(row.get(12));
		ur.setCloseTime(row.get(13));
		ur.setResolutionDate(row.get(14));
		ur.setResolutionTime(row.get(15));
		ur.setAgent(row.get(16));
		
		String email = row.get(17);
		email = email.replaceAll(HTML_REGEX_CLEAR_TAG, "");
		email = email.replaceAll(HTML_ENTITY_CLEAR, " ");
		email = email.replaceAll(UNACCENT_CLEAR, "");
		ur.setEmail(email);
		
		ur.setFullName(row.get(18));
		ur.setPersonOrganizationName(row.get(19));
		ur.setOrigin(row.get(20));
		ur.setLastPendingDate(row.get(21));
		ur.setLastPendingTime(row.get(22));
		ur.setCumulatedpending(row.get(23));		
		ur.setOrganizationName(row.get(25));
		ur.setPriority(row.get(26));
		ur.setResolutionDelay(row.get(27));
		ur.setSlaTtoOver(row.get(28));
		ur.setSlaTtoPassed(row.get(29));
		ur.setTtoDeadline(row.get(30));
		ur.setSlaTtrOver(row.get(31));
		ur.setSlaTtrPassed(row.get(32));
		ur.setTtrDeadline(row.get(33));
		ur.setTicketSubClass(row.get(34));
		
		String solution = row.get(35);
		solution = solution.replaceAll(HTML_REGEX_CLEAR_TAG, "");
		solution = solution.replaceAll(HTML_ENTITY_CLEAR, " ");
		solution = solution.replaceAll(UNACCENT_CLEAR, "");
		if (solution.length() > 4000) {
			solution = solution.substring(0, 4000);
		}
		ur.setSolution(solution);
		
		ur.setUserSatisfaction(row.get(36));
		ur.setUserComment(row.get(37));
		ur.setServiceName(row.get(38));

		return ur;
	}
	
	public List<StagingUserRequestYTD> mapListToStaging(List<?> input) {
		List<StagingUserRequestYTD> temp = new ArrayList<StagingUserRequestYTD>();

		for (Iterator<ArrayList> iterator = (Iterator<ArrayList>) input.iterator(); iterator.hasNext();) {
			List<String> row = iterator.next();

			StagingUserRequestYTD staging = new StagingUserRequestYTD();
			staging = mapStaging(row);
			temp.add(staging);
		}

		return temp;
	}

}
