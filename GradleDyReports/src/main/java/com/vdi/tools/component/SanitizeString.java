package com.vdi.tools.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SanitizeString {
	
	private final static Logger logger = LogManager.getLogger(SanitizeString.class);
	
	private final String HTML_REGEX_CLEAR_TAG = "<[^<>]+>";
	private final String HTML_ENTITY_CLEAR = "(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+";
	private final String UNACCENT_CLEAR = "[^\\p{Print}]";
	
	private String sanitizeStr;
	
	public SanitizeString() {
		
	}
	
	public String getSanitizedString(String str, int maxLength) {
		
		sanitizeStr = str;
		sanitizeStr = sanitizeStr.replaceAll(HTML_REGEX_CLEAR_TAG,"");
		sanitizeStr = sanitizeStr.replaceAll(HTML_ENTITY_CLEAR, " ");
		sanitizeStr = sanitizeStr.replaceAll(UNACCENT_CLEAR, "");
		if (sanitizeStr.length() > maxLength) {
			sanitizeStr = sanitizeStr.substring(0, maxLength);
		}
		
		//logger.info("sanitized: "+sanitizeStr);
		
		return sanitizeStr;
	}

}
