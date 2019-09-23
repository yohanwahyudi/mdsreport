package com.vdi.batch.mds.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.vdi.batch.mds.service.MailService;
import com.vdi.configuration.AppConfig;
import com.vdi.model.Incident;

import freemarker.template.Configuration;

@Service("mailService")
@ComponentScan("com.vdi.batch.mds.service")
public class MailServiceImpl implements MailService{

	private final Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	@Autowired
	@Qualifier("mailSenderDev")
	JavaMailSender mailSender;
	
	@Autowired
	@Qualifier("mailSender")
	JavaMailSender mailSenderDev;
	
	@Autowired
	Configuration freemarkerConfiguration;
	
	@Autowired
	AppConfig appConfig;
	
	@Override
	public void sendEmail() {
		
		logger.info("Sending email......");
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Team"));
			helper.setTo(appConfig.getMailToMdsDaily());
			helper.setSubject(appConfig.getMailMdsDailySubject());
			
			List<String> list = new ArrayList<String>();
			list.add("1");
			list.add("2");
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("list", list);
			
			String text = getTemplateContentMdsDaily(map,"fm_mailTemplateDaily.txt");
			helper.setText(text, true);
//			helper.addAttachment("image.png", new ClassPathResource("classpath:/test/mail/linux-icon.png"));
			
			mailSender.send(message);
			System.out.println("Message has been sent......");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	public void sendEmail(Map<String,Object> mapObject, String template) {
		
		logger.info("Sending email......");
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			
			List<String> toEmailList = new ArrayList<String>();
			
			String[] toEmailArr = appConfig.getMailToMdsDaily();			
			for(int i=0; i<toEmailArr.length; i++) {
				toEmailList.add(toEmailArr[i]);
			}			
						
			for(Map.Entry<String, Object> entry : mapObject.entrySet()) {
				List<Incident> tempList = (List<Incident>) entry.getValue();
				if(tempList != null) {
					for(Incident inc : tempList) {
						String email = inc.getEmail();
						toEmailList.add(email);
					}
				}		
			}
			
			//remove duplicates
			Set<String> hs = new HashSet<>();
			hs.addAll(toEmailList);
			toEmailList.clear();
			toEmailList.addAll(hs);
			
			for(String a:toEmailList) {
				logger.info("email: "+a);
			}
			
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Team"));
			helper.setTo(toEmailList.toArray(new String[toEmailList.size()]));
//			helper.setTo(appConfig.getMailToMdsDaily());
			helper.setSubject(appConfig.getMailMdsDailySubject());
						
			String text = getTemplateContentMdsDaily(mapObject, template);			
			
			logger.info("email body: "+text);
			
			helper.setText(text, true);
			mailSender.send(message);
			
			logger.info("Message has been sent......");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void sendEmail(Map<String, Object> mapObject, String template, String[] to, String subject) {
		
		MimeMessage message = mailSender.createMimeMessage();
		try {
			
			logger.info("Sending email......");
			
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Team"));
			helper.setTo(to);
			helper.setSubject(subject);
						
			String text = getTemplateContentMdsDaily(mapObject, template);			
			
			logger.info("email body: "+text);
			
			helper.setText(text, true);
			mailSender.send(message);
			
			logger.info("Message has been sent......");
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		
	}
	
	@Override
	public void sendEmail(Map<String, Object> mapObject, String template, FileSystemResource file, String subject) {

		MimeMessage message = mailSender.createMimeMessage();

		String[] toEmailArr = appConfig.getMdsReportEmailTo();
		try {

			logger.info("Sending email......");
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Team"));
			helper.setTo(toEmailArr);
			helper.setSubject(subject);
			helper.addAttachment(file.getFilename(), file);
			String text = getTemplateContentMdsDaily(mapObject, template);
			
			logger.info("file attachment: "+file.getPath()+"/"+file.getFilename());
			logger.info("email body: " +text);
			
			helper.setText(text, true);
			mailSender.send(message);
			
			logger.info("Message has been sent......");
			
		} catch (MessagingException e) {
			throw new MailParseException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void sendEmail(Map<String, Object> mapObject, String template, List<FileSystemResource> file, String subject) {

		MimeMessage message = mailSender.createMimeMessage();

		String[] toEmailArr = appConfig.getMdsReportEmailTo();
		try {

			logger.info("Sending email......");
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(new InternetAddress(appConfig.getMailFrom(), "SLA Team"));
			helper.setTo(toEmailArr);
			helper.setSubject(subject);
			
			for(FileSystemResource fsr : file) {
				
				logger.info("file attachment: "+fsr.getPath());
				
				File f = new File(fsr.getPath());
				
				logger.info("file isExist:"+f.exists());
				
				if(f.exists()) {
					helper.addAttachment(fsr.getFilename(), fsr);
				}
			}
			
			String text = getTemplateContentMdsDaily(mapObject, template);
			
			logger.info("email body: " +text);
			
			helper.setText(text, true);
			mailSender.send(message);
			
			logger.info("Message has been sent......");
			
		} catch (MessagingException e) {
			throw new MailParseException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

	}
	
	public String getTemplateContentMdsDaily(Map<String, Object> object, String template) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate(template), object));
			return content.toString();
		} catch (Exception e) {
			System.out.println("Exception occured while processing fmtemplate:" + e.getMessage());
		}
		return "";
	}

}
