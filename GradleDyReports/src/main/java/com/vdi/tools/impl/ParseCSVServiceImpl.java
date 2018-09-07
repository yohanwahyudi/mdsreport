package com.vdi.tools.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vdi.configuration.AppConfig;
import com.vdi.tools.ParseCSVService;

@Service("parseCSVService")
public class ParseCSVServiceImpl implements ParseCSVService{
	
	private String file;
	private String delimiters;
	private Reader source;
	
	@Autowired
	public ParseCSVServiceImpl(AppConfig appConfig) {
		this.file=appConfig.getMdsCsvAgentFile();
		this.delimiters=appConfig.getMdsCsvAgentDelimiters();
	}
	
	public ParseCSVServiceImpl(String file, String delimiters) {
		 this.file = file;
		 this.delimiters = delimiters;
	}
	
	@Override
	public void createCSV() {
		Path path = Paths.get(file);
		Reader reader = null;
		
		try {
			reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
		} catch(IOException e) {
			throw new UncheckedIOException(e);	
		} 		

		this.source=reader;
	}

	@Override
	public List<String> readHeader() {
		createCSV();
		
		List<String> list = new ArrayList<String>();
		try (BufferedReader buffRead = new BufferedReader(source)){
			list = buffRead.lines()
					.findFirst()
					.map(mapper)
					.get();
			try {
				source.close();
			} catch(Exception e) {}
		
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
		
		return list;
	}

	@Override
	public List<List<String>> readRows() {
		createCSV();
		
		List<List<String>> list = new ArrayList<List<String>>();
		try (BufferedReader buffRead = new BufferedReader(source)){
			list = buffRead.lines()
					.skip(1)
					.map(mapper)
					.collect(Collectors.toList());
			
			try {
				source.close();
			} catch(Exception e) {}
			
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
		
		return list;
	}
	
	private Function<String, List<String>> mapper = line -> Arrays.asList(line.split(delimiters));		
	
}
