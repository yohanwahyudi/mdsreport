package test.test;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.vdi.tools.TimeStatic;
import com.vdi.tools.TimeTools;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.sql.Time;

public class Test {

	public static void main(String args[]) {
		System.out.println("current week: " + getCurrentWeek());
		System.out.println("current month: " + getCurrentMonthString());
		System.out.println("current week of year: " + getCurrentWeekYear());
		System.out.println();

		LocalDate today = LocalDate.now();

		LocalDate monday = today.with(previousOrSame(MONDAY));
		LocalDate sunday = today.with(nextOrSame(SUNDAY));

		System.out.println("Today: " + today);
		System.out.println("Monday of the Week: " + monday);
		System.out.println("Sunday of the Week: " + sunday);

		LocalDate twelves = LocalDate.of(2018, 6, 21);
		int week = twelves.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int weekYear = twelves.get(IsoFields.WEEK_BASED_YEAR);
		System.out.println();
		System.out.println(week);
		System.out.println(weekYear);

		LocalDate test = LocalDate.now();
		int weekTest = twelves.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		int weekYearTest = twelves.get(IsoFields.WEEK_BASED_YEAR);
		System.out.println("test " + weekTest);
		System.out.println(".." + weekYearTest);

		int a = 9;
		int b = 90;

		System.out.println(a == b);

		// System.out.println(TimeStatic.currentWeekYear);
		System.out.println(getCurrentMonthString());

		System.out.println("--- " + (new DateFormatSymbols()).getMonths()[10 - 1]);
		System.out.println(getLastWeekOfMonth(8, 2018));
		getlastweekofmonthwithcalendar();
		getlastweekofmonth();
		getCurrentWeekMonth();
		
		TimeTools timeTools = new TimeTools();
		
		System.out.println("weekmonth: "+timeTools.getCurrentWeekMonth());
		System.out.println(timeTools.getEndWeekOfMonth(2018, 8));
		System.out.println("now localdate: "+timeTools.getCurrentLocalDate().minusMonths(1));
		
		List<String> field = new ArrayList<String>();
		field.add("Total");
		field.add("Open");
		field.add("Resolve/ Close");
		
		System.out.println("field: "+field);
		
		System.out.println(timeTools.getCurrentMonth());
		
	}

	private static int getLastWeekOfMonth(int month, int year) {
		Calendar lastDayOfMonth = new GregorianCalendar();
		// Set the date to the day before the 1:st day of the next month
		lastDayOfMonth.set(year, month + 1, 0);
		return lastDayOfMonth.get(Calendar.WEEK_OF_YEAR);
	}
	
	private static LocalDate getNow() {
		LocalDate date = LocalDate.now();
		
		return date;
	}
	
	private static String getNowStr() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEEE");
//		return sdf.format(new java.util.Date());
		LocalDate localDate = LocalDate.now();		
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());
		
		System.out.println(utilDate);
		
		return sdf.format(utilDate);
		
	}

	private static int getCurrentWeek() {
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfMonth());
	}

	private static int getCurrentMonth() {
		int month;
		GregorianCalendar date = new GregorianCalendar();
		month = date.get(Calendar.MONTH);
		month = month + 1;

		return month;
	}

	private static String getCurrentMonthString() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

		return sdf.format(date);
	}

	private static int getCurrentWeekYear() {
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfWeekBasedYear());

		// Calendar cal = Calendar.getInstance();
		// System.out.println(cal.getMinimalDaysInFirstWeek());
		//
		// return cal.get(Calendar.WEEK_OF_YEAR);
		//
	}

	private static void getlastweekofmonthwithcalendar() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 9,0);

		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		System.out.println(";; "+calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println(calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));
		
	    int lastDate = calendar.getActualMaximum(Calendar.DATE);	    

	    System.out.println("Date     : " + calendar.getTime());
	    System.out.println("Last Date: " + lastDate);
	    
	    Instant instant = calendar.toInstant();
	    ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
	    
	    LocalDate date = zonedDateTime.toLocalDate();
	    LocalTime time = zonedDateTime.toLocalTime();
	    
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		
		System.out.println("localdate: "+date);
		System.out.println("localtime: "+time);
		
		System.out.println(date.get(weekFields.ISO.weekOfMonth()));
	}
	
	private static void getlastweekofmonth() {
		
		System.out.println("==========================");
		
		DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY ;
		System.out.println(firstDayOfWeek);

		ZoneId z = ZoneId.of(ZoneId.systemDefault().getId()) ;
		YearMonth ym = YearMonth.of(2018, 7);
		System.out.println("z: "+z);
		System.out.println("ym: "+ym);
		
		LocalDate monthStart = ym.atDay( 1 ) ;
		LocalDate monthStop = ym.atEndOfMonth() ;
		System.out.println("monthStart "+monthStart);
		System.out.println("monthStop "+monthStop);
		
		LocalDate calendarStart = monthStart.with( TemporalAdjusters.previousOrSame( firstDayOfWeek ) ) ;
		LocalDate calendarStop = monthStop.with( TemporalAdjusters.next( firstDayOfWeek ) ) ;
		System.out.println("calendarStart "+calendarStart);
		System.out.println("calendarStop "+calendarStop);
		
		long weeks = ChronoUnit.WEEKS.between( calendarStart , calendarStop ) ;

		System.out.println(weeks);
		
		monthStop = ym.atDay(4);
		calendarStop = monthStop.with( TemporalAdjusters.next( firstDayOfWeek ) ) ;
		System.out.println(ChronoUnit.WEEKS.between(calendarStart, calendarStop));
		
	}
	
	private static void getCurrentWeekMonth() {
		
		LocalDate date = LocalDate.now(ZoneId.of("GMT+7"));
		date.with(DayOfWeek.MONDAY);
	    WeekFields weekFields = WeekFields.of(Locale.getDefault());
	    System.out.println(Locale.getDefault());
//	    return date.get(weekFields.weekOfWeekBasedYear());
	    System.out.println(date.get(weekFields.weekOfMonth()));
//	    
//	    LocalDate now = LocalDate.now();
//	    TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
//	    System.out.println(now.with(fieldISO, 1)); // 2015-02-09 (Monday)
//
//	    TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
//	    System.out.println(now.with(fieldUS, 1)); // 2015-02-08 (Sunday)
//		
//	    LocalDate ld = LocalDate.of(2017, 8, 18); // Friday as original date
//
//	    System.out.println(
//	        ld.with(DayOfWeek.SUNDAY)); // 2017-08-20 (2 days later according to ISO)
//
//	    // Now let's again set the date to Sunday, but this time in a localized way...
//	    // the method dayOfWeek() uses localized numbering (Sunday = 1 in US and = 7 in France)
//
//	    System.out.println(ld.with(WeekFields.of(Locale.US).dayOfWeek(), 1L)); // 2017-08-13
//	    System.out.println(ld.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 7L)); // 2017-08-20

	}
	
	private static ZoneId getZoneID() {
		return ZoneId.of("GMT+7");
	}

}


