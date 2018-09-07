package com.vdi.batch.mds.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;

public interface MailService {
	
//	public void sendEmail(final Object object);
	public void sendEmail();
	public void sendEmail(Map<String,Object> mapObject, String template);
	public void sendEmail(Map<String,Object> mapObject, String template, FileSystemResource file, String subject);
	public void sendEmail(Map<String,Object> mapObject, String template, String[] to, String subject);

}
