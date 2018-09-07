package test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseCsv {
	
	static List<String> list = new ArrayList<String>();
	
	public static void main(String args[]) {
		File file = new File(System.getProperty("user.dir")+File.separator+"test.csv");
		try {
			InputStream is = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line="";
			while ((line=br.readLine())!=null) {
				System.out.println(line);
			}
			
			try (Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir")+File.separator+"test.csv"))){
				
				List<List<String>> values = lines.skip(1).map(row -> Arrays.asList(row.split(","))).collect(Collectors.toList());
				
				System.out.println(values.get(0).get(2));
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
