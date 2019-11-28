package test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mysql.fabric.xmlrpc.base.Array;

public class ParseCsv2 {
	
	static class CSVReader{
		
		private static final String SEPARATOR = ";";
		private final Reader source;
		
		public CSVReader(Reader source) {
			this.source = source;
		}
		
		List<String> readHeader(){
			try (BufferedReader buffRead = new BufferedReader(source)){
				return buffRead.lines()
						.findFirst()
						.map(line -> Arrays.asList(line.split(SEPARATOR)))
						.get();
				
			} catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		
		List<List<String>> readRows(){
			try (BufferedReader buffRead = new BufferedReader(source)){
				return buffRead.lines()
						.skip(1)
						.map(mapper)
						.collect(Collectors.toList());
			} catch(IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		
		public static  Function<String, List<String>> mapper = line -> Arrays.asList(line.split(SEPARATOR));		
		
	}
	
	
	private static CSVReader createCSVReader(String file) throws IOException {
		Path path = Paths.get(file);
		Reader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"));
		
		return new CSVReader(reader);
	}
	
	private static void readHeader() {
		try {
			CSVReader csvReader = createCSVReader(System.getProperty("user.dir")+File.separator+"test.csv");
			List<String> header = csvReader.readHeader();
			
			System.out.println("header");
			System.out.println(header);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void readRows() {
		try {
			CSVReader csvReader = createCSVReader(System.getProperty("user.dir")+File.separator+"test.csv");
			List<List<String>> list = csvReader.readRows();
			
			System.out.println("rows");
			System.out.println(list);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main (String args[]) {
		readHeader();
		readRows();
	}

}
