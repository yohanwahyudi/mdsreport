package com.vdi.configuration;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
// @ComponentScan(basePackages = "com.vdi")
@ComponentScan({ "com.vdi", "com.vdi.batch.mds.service", "com.vdi.batch.mds.tools", "com.vdi.batch.mds.repository.dao",
		"com.vdi.batch.mds.repository.dao.impl" })
@EnableJpaRepositories(basePackages = { "com.vdi.batch.mds.repository" })
@EnableTransactionManagement
@PropertySources({ @PropertySource("classpath:config.properties"), @PropertySource("classpath:db.properties") })

public final class AppConfig {

	private final String log4JXmlLocation;

	// HTTP CONFIG
	private final int httpTimeout;
	private final int httpMaxPool;
	private final int httpMaxPerRoute;

	// MAIL CONFIG
	private final String mailHost;
	private final int mailPort;
	private final String mailFrom;
	private final String[] mailToMdsDaily;
	private final String mailMdsDailySubject;
	private final String[] mailSlaMgr;
	private final String[] mdsReportEmailTo;

	// mds config
	@Value("#{'${mds.jsoup.organization}'.split(';')}")
	private final String[] organization;

	private final int organizationCol;
	private final int startDateCol;
	private final int ttrDeadlineCol;
	private final int statusCol;
	private final int mdsDailyDeadlineThresholdDay;
	private final String mdsDailyReportPath;
	private final String mdsFileSource;
	private final String mdsHttpUrl;
	private final String mdsHttpSdUrl;
	private final String mdsHttpUrUrl;
	private final String mdsCsvAgentDelimiters;
	private final String mdsCsvAgentFile;

	@Value("#{'${mds.servicedesk.report.person}'.split(';')}")
	private final String[] servicedeskPerson;

	private final String servicedeskOtherTeam;
	private final int mdsSlaThreshold;
	private final String mdsReportPath;

	@Autowired
	public AppConfig(Environment env) {

		this.log4JXmlLocation = env.getRequiredProperty(PropertyNames.LOG4J_XML_LOCATION, String.class);

		this.httpTimeout = env.getRequiredProperty(PropertyNames.HTTP_TIMEOUT, Integer.class);
		this.httpMaxPool = env.getRequiredProperty(PropertyNames.HTTP_MAXPOOL, Integer.class);
		this.httpMaxPerRoute = env.getRequiredProperty(PropertyNames.HTTP_MAXPERROUTE, Integer.class);

		this.mdsHttpUrl = env.getRequiredProperty(PropertyNames.MDS_HTTP_URL, String.class);
		this.mdsHttpSdUrl = env.getRequiredProperty(PropertyNames.MDS_HTTP_SD_URL, String.class);
		this.mdsHttpUrUrl = env.getRequiredProperty(PropertyNames.MDS_HTTP_UR_URL, String.class);
		this.mailHost = env.getRequiredProperty(PropertyNames.MAIL_HOST, String.class);
		this.mailPort = env.getRequiredProperty(PropertyNames.MAIL_PORT, Integer.class);
		this.mailFrom = env.getRequiredProperty(PropertyNames.MAIL_FROM, String.class);
		this.mailToMdsDaily = env.getRequiredProperty(PropertyNames.MDS_EMAIL_DAILY_TO, String[].class);
		this.mailMdsDailySubject = env.getRequiredProperty(PropertyNames.MDS_EMAIL_DAILY_SUBJECT, String.class);
		this.mailSlaMgr = env.getRequiredProperty(PropertyNames.MAIL_SLA_MGR, String[].class);
		this.mdsReportEmailTo = env.getRequiredProperty(PropertyNames.MDS_REPORT_EMAIL_TO, String[].class);

		this.mdsDailyReportPath = env.getRequiredProperty(PropertyNames.MDS_DAILY_REPORT_PATH, String.class);
		this.mdsFileSource = env.getRequiredProperty(PropertyNames.MDS_JSOUP_FILE, String.class);

		this.mdsCsvAgentDelimiters = env.getRequiredProperty(PropertyNames.MDS_CSV_AGENT_DELIMITERS, String.class);
		this.mdsCsvAgentFile = env.getRequiredProperty(PropertyNames.MDS_CSV_AGENT_FILE, String.class);

		this.mdsDailyDeadlineThresholdDay = env.getRequiredProperty(PropertyNames.MDS_DAILY_THRESHOLD_DAY,
				Integer.class);
		this.organization = env.getRequiredProperty(PropertyNames.MDS_JSOUP_ORGANIZATION, String[].class);
		this.organizationCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_ORGANIZATION_COL, Integer.class);
		this.startDateCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_STARTDATE_COL, Integer.class);
		this.ttrDeadlineCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_TTRDEADLINE_COL, Integer.class);
		this.statusCol = env.getRequiredProperty(PropertyNames.MDS_JSOUP_STATUS_COL, Integer.class);

		this.servicedeskPerson = env.getRequiredProperty(PropertyNames.MDS_SERVICEDESK_REPORT_PERSON, String[].class);
		this.servicedeskOtherTeam = env.getRequiredProperty(PropertyNames.MDS_SERVICEDESK_REPORT_OTHERTEAM,
				String.class);
		this.mdsSlaThreshold = env.getRequiredProperty(PropertyNames.MDS_SLA_THRESHOLD, Integer.class);
		this.mdsReportPath = env.getRequiredProperty(PropertyNames.MDS_REPORT_PATH, String.class);
	}

	@Bean("mailSender")
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);

		Properties javaMailProperties = new Properties();
//		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;

	}

	@Bean("mailSenderDev")
	public JavaMailSender getMailSenderDev() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(465);
		mailSender.setUsername("vdichangemanagement@gmail.com");
		mailSender.setPassword("Visionet123");

		Properties javaMailProperties = new Properties();
//		javaMailProperties.put("mail.debug", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
//		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailProperties.put("mail.smtp.port", "465");
		javaMailProperties.put("mail.mime.multipart.allowempty", "true");

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}

	/*
	 * FreeMarker configuration.
	 */
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("/mail/freemarker/templates/");
		return bean;
	}

	/*
	 * Database Configuration
	 */
	@Bean(destroyMethod = "close")
	DataSource dataSource(Environment env) {
		
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(env.getRequiredProperty(PropertyNames.PROPERTY_NAME_DB_DRIVER_CLASS));
		dataSourceConfig.setJdbcUrl(env.getRequiredProperty(PropertyNames.PROPERTY_NAME_DB_URL));
		dataSourceConfig.setUsername(env.getRequiredProperty(PropertyNames.PROPERTY_NAME_DB_USER));
		dataSourceConfig.setPassword(env.getRequiredProperty(PropertyNames.PROPERTY_NAME_DB_PASSWORD));
		dataSourceConfig.setIdleTimeout(TimeUnit.SECONDS.toMillis(env.getRequiredProperty(PropertyNames.HIKARI_SETIDLETIMEOUT, Integer.class)));
		dataSourceConfig.setMaximumPoolSize(env.getRequiredProperty(PropertyNames.HIKARI_SETMAXIMUMPOOLSIZE, Integer.class));
		dataSourceConfig.setConnectionTimeout(TimeUnit.SECONDS.toMillis(env.getRequiredProperty(PropertyNames.HIKARI_SETCONNECTIONTIMEOUT, Integer.class)));
		dataSourceConfig.setLeakDetectionThreshold(TimeUnit.SECONDS.toMillis(env.getRequiredProperty(PropertyNames.HIKARI_SETLEAKDETECTIONTHRESHOLD, Integer.class)));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_CACHEPREPSTMTS, env.getRequiredProperty(PropertyNames.HIKARI_CACHEPREPSTMTS));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_PREPSTMTCACHESIZE, env.getRequiredProperty(PropertyNames.HIKARI_PREPSTMTCACHESIZE));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_PREPSTMTCACHESQLLIMIT, env.getRequiredProperty(PropertyNames.HIKARI_PREPSTMTCACHESQLLIMIT));		
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_USESERVERPREPSTMTS, env.getRequiredProperty(PropertyNames.HIKARI_USESERVERPREPSTMTS));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_USELOCALSESSIONSTATE, env.getRequiredProperty(PropertyNames.HIKARI_USELOCALSESSIONSTATE));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_REWRITEBATCHEDSTATEMENTS, env.getRequiredProperty(PropertyNames.HIKARI_REWRITEBATCHEDSTATEMENTS));		
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_CACHERESULTSETMETADATA, env.getRequiredProperty(PropertyNames.HIKARI_CACHERESULTSETMETADATA));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_CACHESERVERCONFIGURATION, env.getRequiredProperty(PropertyNames.HIKARI_CACHESERVERCONFIGURATION));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_ELIDESETAUTOCOMMITS, env.getRequiredProperty(PropertyNames.HIKARI_ELIDESETAUTOCOMMITS));
		dataSourceConfig.addDataSourceProperty(PropertyNames.HIKARI_MAINTAINTIMESTATS, env.getRequiredProperty(PropertyNames.HIKARI_MAINTAINTIMESTATS));
		
		return new HikariDataSource(dataSourceConfig);
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(PropertyNames.PROPERTY_NAME_ENTITY_PACKAGE);

		Properties jpaProperties = new Properties();

		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_DIALECT,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_SHOW_SQL,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_FORMAT_SQL,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_FORMAT_SQL));

		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_JDBC_BATCH_VERSIONED_DATA,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_JDBC_BATCH_VERSIONED_DATA));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_ORDER_INSERTS,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_ORDER_INSERTS));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_ORDER_UPDATES,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_ORDER_UPDATES));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_CONNECTION_AUTOCOMMIT,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_CONNECTION_AUTOCOMMIT));
		jpaProperties.put(PropertyNames.PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS,
				env.getRequiredProperty(PropertyNames.PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getMailHost() {
		return mailHost;
	}

	public int getMailPort() {
		return mailPort;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public String[] getMailToMdsDaily() {
		return mailToMdsDaily;
	}

	public String getMailMdsDailySubject() {
		return mailMdsDailySubject;
	}

	public String[] getOrganization() {
		return organization;
	}

	public int getOrganizationCol() {
		return organizationCol;
	}

	public int getStartDateCol() {
		return startDateCol;
	}

	public int getTtrDeadlineCol() {
		return ttrDeadlineCol;
	}

	public int getStatusCol() {
		return statusCol;
	}

	public String getLog4JXmlLocation() {
		return log4JXmlLocation;
	}

	public int getMdsDailyDeadlineThresholdDay() {
		return mdsDailyDeadlineThresholdDay;
	}

	public String getMdsDailyReportPath() {
		return mdsDailyReportPath;
	}

	public String getMdsFileSource() {
		return mdsFileSource;
	}

	public int getHttpTimeout() {
		return httpTimeout;
	}

	public int getHttpMaxPool() {
		return httpMaxPool;
	}

	public int getHttpMaxPerRoute() {
		return httpMaxPerRoute;
	}

	public String getMdsHttpUrl() {
		return mdsHttpUrl;
	}

	public String getMdsCsvAgentDelimiters() {
		return mdsCsvAgentDelimiters;
	}

	public String getMdsCsvAgentFile() {
		return mdsCsvAgentFile;
	}

	public String[] getMailSlaMgr() {
		return mailSlaMgr;
	}

	public String getMdsHttpSdUrl() {
		return mdsHttpSdUrl;
	}

	public String getMdsHttpUrUrl() {
		return mdsHttpUrUrl;
	}

	public String[] getServicedeskPerson() {
		return servicedeskPerson;
	}

	public String getServicedeskOtherTeam() {
		return servicedeskOtherTeam;
	}

	public String[] getMdsReportEmailTo() {
		return mdsReportEmailTo;
	}

	public int getMdsSlaThreshold() {
		return mdsSlaThreshold;
	}

	public String getMdsReportPath() {
		return mdsReportPath;
	}

}
