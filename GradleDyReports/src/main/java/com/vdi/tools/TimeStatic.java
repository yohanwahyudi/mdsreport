package com.vdi.tools;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeStatic {

	public static final int currentWeekYear;
	public static final int currentWeekMonth;
	public static final int currentMonth;
	public static final int currentYear;

	public static final String currentDateStr;
	public static final String currentMonthStr;
	public static final String prevMonthStr;

	static {
		currentWeekYear = getCurrentWeekYear();
		currentWeekMonth = getCurrentWeekMonth();
		currentMonth = getCurrentMonth();
		currentYear = getCurrentYear();

		currentDateStr = getCurrentDateStr();
		currentMonthStr = getCurrentMonthString();
		prevMonthStr = getPrevMonthString();
	}

	public TimeStatic() {

	}

	private static int getCurrentDate() {

		LocalDate localDate = LocalDate.now();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());

		SimpleDateFormat sdf = new SimpleDateFormat("dd");

		return Integer.parseInt(sdf.format(utilDate));

	}
	
	private static String getCurrentDateStr() {

		LocalDate localDate = LocalDate.now();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

		return sdf.format(utilDate);

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

	public static int getCurrentWeekMonthIso(int year, int month) {

		DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

		// ZoneId z = ZoneId.of(getZoneID().getId()) ;
		YearMonth ym = YearMonth.of(year, month);

		LocalDate monthStart = ym.atDay(1);
		LocalDate monthStop = ym.atDay(getCurrentDate());

		LocalDate calendarStart = monthStart.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
		LocalDate calendarStop = monthStop.with(TemporalAdjusters.next(firstDayOfWeek));

		Long weeks = ChronoUnit.WEEKS.between(calendarStart, calendarStop);

		return weeks.intValue();

	}

	public static int getCurrentWeekMonthIso(int year, int month, int date) {

		DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

		// ZoneId z = ZoneId.of(getZoneID().getId()) ;
		YearMonth ym = YearMonth.of(year, month);

		LocalDate monthStart = ym.atDay(1);
		LocalDate monthStop = ym.atDay(date);

		LocalDate calendarStart = monthStart.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
		LocalDate calendarStop = monthStop.with(TemporalAdjusters.next(firstDayOfWeek));

		Long weeks = ChronoUnit.WEEKS.between(calendarStart, calendarStop);

		return weeks.intValue();

	}

	public static int getLastDateofMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 0);

		int lastDate = calendar.getActualMaximum(Calendar.DATE);

		return lastDate;
	}

	public static int getEndWeekOfMonth(int year, int month) {

		DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

		// ZoneId z = ZoneId.of(getZoneID().getId()) ;
		YearMonth ym = YearMonth.of(year, month);

		LocalDate monthStart = ym.atDay(1);
		LocalDate monthStop = ym.atEndOfMonth();

		LocalDate calendarStart = monthStart.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
		LocalDate calendarStop = monthStop.with(TemporalAdjusters.next(firstDayOfWeek));

		Long weeks = ChronoUnit.WEEKS.between(calendarStart, calendarStop);

		return weeks.intValue();

	}

	private static int getCurrentMonth() {

		LocalDate date = LocalDate.now();

		return date.getMonthValue();
	}

	private static String getCurrentMonthString() {

		LocalDate localDate = LocalDate.now();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());

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

	public static String getIntToMonthString(int month) {

		return new DateFormatSymbols().getMonths()[month - 1];

	}

	private static int getCurrentYear() {
		LocalDate date = LocalDate.now();
		return date.getYear();
	}

	private static ZoneId getZoneID() {
		return ZoneId.of("GMT+7");
	}

}
