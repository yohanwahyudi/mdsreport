package com.vdi.batch.mds.service;

import java.util.List;

import org.jsoup.select.Elements;

import com.vdi.model.Incident;

import freemarker.core.ParseException;

public interface JsoupParseService {

	public List<List<String>> jsoupTrToListVisionetByUrl();
	public List<List<String>> jsoupTrToListVisionetByFile();
	
	public List<Incident> getIncidentAllByURL(); 
	public List<Incident> getIncidentAllByFile();
	public List<Incident> getIncidentDeadline();
	public List<Incident> getIncidentAssign();
	public List<Incident> getIncidentPending();
}
