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

import org.springframework.stereotype.Component;

@Component("timeTools")
public class TimeTools {

	public TimeTools() {
		
	}
	
	public LocalDate getCurrentLocalDate() {
		
		LocalDate localDate = LocalDate.now();
		return localDate;
		
	}
	
	public LocalDate getPrevMonthLocalDate() {
		
		LocalDate current = getCurrentLocalDate();
		return current.minusMonths(1);
		
	}
	
	public int getCurrentDate() {

		LocalDate localDate = getCurrentLocalDate();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());

		SimpleDateFormat sdf = new SimpleDateFormat("dd");

		return Integer.parseInt(sdf.format(utilDate));

	}
	
	public String getCurrentDateStr() {

		LocalDate localDate = getCurrentLocalDate();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

		return sdf.format(utilDate);

	}

	public int getCurrentWeekYear() {

		LocalDate date = getCurrentLocalDate();

		ZonedDateTime zoneDate = date.atStartOfDay(getZoneID());

		return zoneDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
	}

	public int getCurrentWeekMonth() {

		LocalDate date = getCurrentLocalDate();
		date = date.with(DayOfWeek.MONDAY);
		
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfMonth());

	}

	public int getCurrentWeekMonthIso(int year, int month) {

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

	public int getCurrentWeekMonthIso(int year, int month, int date) {

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

	public int getLastDateofMonth(int year, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 0);

		int lastDate = calendar.getActualMaximum(Calendar.DATE);

		return lastDate;
	}

	public int getEndWeekOfMonth(int year, int month) {

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

	public int getCurrentMonth() {

		LocalDate date = getCurrentLocalDate();

		return date.getMonthValue();
	}

	public String getCurrentMonthString() {

		LocalDate localDate = getCurrentLocalDate();
		Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.of(getZoneID().getId())).toInstant());

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

		return sdf.format(utilDate);
	}

	public String getPrevMonthString() {

		LocalDate localDate = getCurrentLocalDate();
		LocalDate earlier = localDate.minusMonths(1);

		Date utilDate = Date.from(earlier.atStartOfDay(ZoneId.of("GMT+7")).toInstant());

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

		return sdf.format(utilDate);
	}

	public String getIntToMonthString(int month) {

		return new DateFormatSymbols().getMonths()[month - 1];

	}

	public int getCurrentYear() {
		LocalDate date = getCurrentLocalDate();
		return date.getYear();
	}

	public ZoneId getZoneID() {
		return ZoneId.of("GMT+7");
	}


}
