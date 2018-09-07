package test.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("test.properties")
@PropertySource("classpath:config.properties")
public class YourClass {
	
	@Value("${mds.daily.prefix}")
	private String dailyPrefix;
	
	@Value("#{'${mds.jsoup.organization}'.split(';')}")
	private String[] organization;
	
//	@Autowired
//	private MyClass myClass;
	
	@Autowired
	public YourClass(Environment env) {
		this.organization = env.getRequiredProperty("mds.jsoup.organization",String[].class);
	}
	
	public void printMyClass() {
		System.out.println(dailyPrefix);
		
//		System.out.println(myClass.getOrganizationCol());
		
		for(String a:organization) {
			System.out.println("a "+a);
		}
	}

	public String[] getOrganization() {
		return organization;
	}

}
