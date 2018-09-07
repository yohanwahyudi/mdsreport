package test.prop.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
@ComponentScan(basePackages = "test.prop")
@PropertySource(value={"classpath:myapp.properties","classpath:myapp2.properties","classpath:myapp3.properties"})
public class AppConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//		return new PropertySourcesPlaceholderConfigurer();
		
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//		configurer.setLocation(new ClassPathResource("myapp.properties"));
		
		configurer.setIgnoreUnresolvablePlaceholders(true);
		configurer.setNullValue("This is NULL");
		
		return configurer;
	}
	
	@Bean
	public static ConversionService conversionService() {
//		return new DefaultFormattingConversionService();
		
		DefaultFormattingConversionService cs = new DefaultFormattingConversionService();

		cs.addConverter(String.class, Pattern.class, (Converter<String, Pattern>) Pattern::compile);
		
		return cs;
	}
}
