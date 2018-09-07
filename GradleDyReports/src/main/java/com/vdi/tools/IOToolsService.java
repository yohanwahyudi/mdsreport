package com.vdi.tools;

import java.io.IOException;

public interface IOToolsService {
	
	public String readFile();
	public String readFile(String filePath);
	public String readUrl();
	public String readUrl(String url);
	
	public void writeBuffered(String str, int bufSize, String fileName) throws IOException;

}
