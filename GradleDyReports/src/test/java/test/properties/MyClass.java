package test.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("test.properties")
@PropertySource("classpath:config.properties")
public final class MyClass {
	
	private final String httpTimeout;
	private final String[] organization;	
	private final int organizationCol;

	@Autowired
	public MyClass(@Value("${mds.jsoup.organization.col}") int organizationcol, @Value("${http.timeout}") String httpTimeout, @Value("${mds.jsoup.organization}") String[] organization) {
		System.out.println("Enter myclass constructor");
		
		this.organizationCol=organizationcol;
		this.httpTimeout=httpTimeout;
		this.organization=organization;
	}
	
	@Bean
	public String print() {
		System.out.println("httptimeout: "+httpTimeout);
		for(String a : organization) {
			System.out.println(a);
		}
		return httpTimeout;
	}

	public int getOrganizationCol() {
		return organizationCol;
	}

	public String getHttpTimeout() {
		return httpTimeout;
	}

	public String[] getOrganization() {
		return organization;
	}
	

}
