package com.vdi.tools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class TimeStatic {

	public static final int currentWeekYear;
	public static final int currentWeekMonth;
	public static final int currentMonth;
	public static final int currentYear;
	
	public static final String currentMonthStr;
	public static final String prevMonthStr;

	static {
		currentWeekYear = getCurrentWeekYear();
		currentWeekMonth = getCurrentWeekMonth();
		currentMonth = getCurrentMonth();
		currentYear = getCurrentYear();
		
		currentMonthStr = getCurrentMonthString();
		prevMonthStr = getPrevMonthString();
	}

	private static int getCurrentWeekYear() {

		LocalDate date = LocalDate.now();
	
		ZonedDateTime zoneDate = date.atStartOfDay(getZoneID());
		
		return zoneDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
	}
	
	private static int getCurrentWeekMonth() {

		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		
		return date.get(weekFields.weekOfMonth());

	}
	
	private static int getCurrentMonth() {

		LocalDate date = LocalDate.now();
		
		return date.getMonthValue();
	}
	
	private static String getCurrentMonthString() {

		LocalDate localDate = LocalDate.now();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of("GMT+7")).toInstant());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		
		return sdf.format(utilDate);
	}
	
	private static String getPrevMonthString() {
		
		LocalDate localDate = LocalDate.now();
		LocalDate earlier = localDate.minusMonths(1);
		
		Date utilDate = Date.from(earlier.atStartOfDay(ZoneId.of("GMT+7")).toInstant());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		
		return sdf.format(utilDate);
	}
	
	private static int getCurrentYear() {

		LocalDate date = LocalDate.now();
		
		return date.getYear();
	}
	
	private static ZoneId getZoneID() {
		return ZoneId.of("GMT+7");
	}
	

}
