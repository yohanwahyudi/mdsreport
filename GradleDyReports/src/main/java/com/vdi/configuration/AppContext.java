package com.vdi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({
	"com.vdi.configuration"
})
//@EnableWebMvc
@PropertySource("classpath:config.properties")
public class AppContext {

	@Bean
	static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		System.out.println("propertysourceplaceholder");
        return new PropertySourcesPlaceholderConfigurer();
    }
}
