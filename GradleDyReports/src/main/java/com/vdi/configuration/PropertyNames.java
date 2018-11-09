package com.vdi.configuration;

public final class PropertyNames {

	public PropertyNames() {
	}

	public static final String LOG4J_XML_LOCATION = "log4j.xml.location";

	public static final String MDS_DAILY_THRESHOLD_DAY = "mds.daily.deadline.threshold.day";
	public static final String MDS_DAILY_PREFIX = "mds.daily.prefix";
	public static final String MDS_JSOUP_ORGANIZATION = "mds.jsoup.organization";
	public static final String MDS_JSOUP_ORGANIZATION_COL = "mds.jsoup.organization.col";
	public static final String MDS_JSOUP_STARTDATE_COL = "mds.jsoup.startdate.col";
	public static final String MDS_JSOUP_TTRDEADLINE_COL = "mds.jsoup.ttrdeadline.col";
	public static final String MDS_JSOUP_STATUS_COL = "mds.jsoup.status.col";
	public static final String MDS_JSOUP_FILE = "mds.jsoup.file";
	public static final String MDS_EMAIL_DAILY_TO = "mds.daily.email.to";
	public static final String MDS_HTTP_URL = "mds.http.url";
	public static final String MDS_HTTP_SD_URL = "mds.http.sd.url";
	public static final String MDS_HTTP_UR_URL = "mds.http.ur.url";
	public static final String MDS_EMAIL_DAILY_SUBJECT = "mds.daily.email.subject";
	public static final String MDS_DAILY_REPORT_PATH = "mds.daily.report.path";

	public static final String HTTP_TIMEOUT = "http.timeout";
	public static final String HTTP_MAXPOOL = "http.maxpool";
	public static final String HTTP_MAXPERROUTE = "http.maxperroute";

	public static final String MAIL_HOST = "mail.host";
	public static final String MAIL_PORT = "mail.port";
	public static final String MAIL_FROM = "mail.from";
	public static final String MAIL_TO = "mail.to";
	public static final String MAIL_SLA_MGR = "mail.sla.manager";

	//for mds weekly/monthly report
	public static final String MDS_REPORT_EMAIL_TO = "mds.report.email.to";

	public static final String MDS_CSV_AGENT_DELIMITERS = "mds.csv.agent.delimiters";
	public static final String MDS_CSV_AGENT_FILE = "mds.csv.agent.file";

	public static final String MDS_REPORT_PATH = "mds.report.path";
	
	public static final String MDS_SLA_THRESHOLD = "mds.sla.threshold";
	public static final String MDS_SERVICEDESK_REPORT_PERSON = "mds.servicedesk.report.person";
	public static final String MDS_SERVICEDESK_REPORT_OTHERTEAM = "mds.servicedesk.report.otherteam";

	// DB config
	public static final String PROPERTY_NAME_DB_DRIVER_CLASS = "db.driver";
	public static final String PROPERTY_NAME_DB_PASSWORD = "db.password";
	public static final String PROPERTY_NAME_DB_URL = "db.url";
	public static final String PROPERTY_NAME_DB_USER = "db.username";
	public static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	public static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	public static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	public static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	public static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	public static final String[] PROPERTY_NAME_ENTITY_PACKAGE = { "com.vdi.model" };
	public static final String PROPERTY_NAME_HIBERNATE_ID_NEW_GENERATOR_MAPPINGS = "hibernate.id.new_generator_mappings";

	//hikaricp config
	public static final String HIKARI_CACHEPREPSTMTS="hikari.cacheprepstmts";
	public static final String HIKARI_PREPSTMTCACHESIZE="hikari.prepstmtcachesize";
	public static final String HIKARI_PREPSTMTCACHESQLLIMIT="hikari.prepstmtcachesqllimit";
	public static final String HIKARI_USESERVERPREPSTMTS="hikari.useserverprepstmts";
	public static final String HIKARI_USELOCALSESSIONSTATE="hikari.uselocalsessionstate";
	public static final String HIKARI_REWRITEBATCHEDSTATEMENTS="hikari.rewritebatchedstatements";
	public static final String HIKARI_CACHERESULTSETMETADATA="hikari.cacheresultsetmetadata";
	public static final String HIKARI_CACHESERVERCONFIGURATION="hikari.cacheserverconfiguration";
	public static final String HIKARI_ELIDESETAUTOCOMMITS="hikari.elidesetautocommits";
	public static final String HIKARI_MAINTAINTIMESTATS="hikari.maintaintimestats";
	public static final String HIKARI_SETCONNECTIONTIMEOUT="hikari.setconnectiontimeout";
	public static final String HIKARI_SETLEAKDETECTIONTHRESHOLD="hikari.setleakdetectionthreshold";
	public static final String HIKARI_SETIDLETIMEOUT="hikari.setidletimeout";
	public static final String HIKARI_SETMAXIMUMPOOLSIZE="hikari.setmaximumpoolsize";
	
	// hibernate tweak
	public static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
	public static final String PROPERTY_NAME_HIBERNATE_JDBC_BATCH_VERSIONED_DATA = "hibernate.jdbc.batch_versioned_data";
	public static final String PROPERTY_NAME_HIBERNATE_ORDER_INSERTS = "hibernate.order_inserts";
	public static final String PROPERTY_NAME_HIBERNATE_ORDER_UPDATES = "hibernate.order_updates";
	public static final String PROPERTY_NAME_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
	public static final String PROPERTY_NAME_HIBERNATE_CONNECTION_AUTOCOMMIT = "hibernate.connection.autocommit";

	// constant
	// ITOP Status
	public static final String PENDING = "Pending";
	public static final String ASSIGNED = "Assigned";
	public static final String ESCALATED_TTR = "Escalated TTR";
	public static final String CONSTANT_REPORT_PERIOD_WEEKLY = "weekly";
	public static final String CONSTANT_REPORT_PERIOD_MONTHLY = "monthly";
	public static final String LAST_MONTH = "LAST_MONTH";
	
	//constant day
	public static final String DAY_STR_MONDAY = "MONDAY";


}
