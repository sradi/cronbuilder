package de.cron;

import java.time.Month;

import de.cron.fluent.CronDayOfWeekPartOne;
import de.cron.fluent.CronDayOfWeekPartTwo;
import de.cron.fluent.CronDayPartOne;
import de.cron.fluent.CronDayPartTwo;
import de.cron.fluent.CronHourPartOne;
import de.cron.fluent.CronHourPartTwo;
import de.cron.fluent.CronMinutePartOne;
import de.cron.fluent.CronMinutePartTwo;
import de.cron.fluent.CronMonthPartOne;
import de.cron.fluent.CronMonthPartTwo;
import de.cron.string.CronDayOfWeek;
import de.cron.string.day.CronDay;
import de.cron.string.day.CronDayRange;
import de.cron.string.day.CronSpecificDays;
import de.cron.string.hours.CronHour;
import de.cron.string.hours.CronHourRange;
import de.cron.string.hours.CronSpecificHours;
import de.cron.string.minutes.CronMinute;
import de.cron.string.minutes.CronMinuteRange;
import de.cron.string.minutes.CronSpecificMinutes;
import de.cron.string.month.CronMonth;
import de.cron.string.month.CronMonthRange;
import de.cron.string.month.CronSpecificMonths;

public class Cron implements CronMinutePartOne, CronMinutePartTwo, CronHourPartOne, CronHourPartTwo, CronDayPartOne,CronDayPartTwo, CronMonthPartOne, CronMonthPartTwo, CronDayOfWeekPartOne, CronDayOfWeekPartTwo {
	
	private CronMinute minute;
	private int fromMinute;
	private CronHour hour;
	private int fromHour;
	private CronDay day;
	private int fromDay;
	private CronMonth month;
	private Month fromMonth;
	
	public static CronMinutePartOne cron() {
		return null;
	}

	@Override
	public CronHourPartOne everyMinute() {
		this.minute = CronMinute.EVERY_MINUTE;
		return this;
	}
	
	@Override
	public CronHourPartOne inTheseMinutes(int... minutes) {
		this.minute = new CronSpecificMinutes(minutes);
		return this;
	}
	
	@Override
	public CronMinutePartTwo fromMinute(int minute) {
		this.fromMinute = minute;
		return this;
	}
	
	@Override
	public CronHourPartOne untilMinute(int minute) {
		this.minute = new CronMinuteRange(fromMinute, minute);
		return this;
	}

	
	//***************************************************************
	@Override
	public CronDayPartOne everyHour() {
		this.hour = CronHour.EVERY_HOUR;
		return this;
	}
	
	@Override
	public CronDayPartOne inTheseHours(int... hours) {
		this.hour = new CronSpecificHours(hours);
		return this;
	}
	
	@Override
	public CronHourPartTwo fromHour(int hour) {
		this.fromHour = hour;
		return this;
	}
	
	@Override
	public CronDayPartOne untilHour(int hour) {
		this.hour = new CronHourRange(fromHour, hour);
		return this;
	}

	//***************************************************************	
	@Override
	public CronMonthPartOne everyDay() {
		this.day = CronDay.EVERY_DAY;
		return this;
	}
	
	@Override
	public CronMonthPartOne onTheseDays(int... days) {
		this.day = new CronSpecificDays(days);
		return this;
	}
	
	@Override
	public CronDayPartTwo fromDay(int day) {
		this.fromDay = day;
		return this;
	}
	
	@Override
	public CronMonthPartOne untilDay(int day) {
		this.day = new CronDayRange(fromDay, day);
		return this;
	}

	//***************************************************************
	@Override
	public CronDayOfWeekPartOne everyMonth() {
		this.month = CronMonth.EVERY_MONTH;
		return this;
	}
	
	@Override
	public CronDayOfWeekPartOne inTheseMonths(Month... months) {
		this.month = new CronSpecificMonths(months);
		return this;
	}
	
	@Override
	public CronMonthPartTwo fromMonth(Month month) {
		this.fromMonth = month;
		return this;
	}
	
	@Override
	public CronDayOfWeekPartOne untilMonth(Month month) {
		this.month = new CronMonthRange(fromMonth, month);
		return this;
	}	

	//***************************************************************
	@Override
	public CronDefinition everyDayofWeek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CronDefinition onTheseDaysOfTheWeek(CronDayOfWeek... daysOfWeek) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CronDayOfWeekPartTwo fromDayOfWeek(CronDayOfWeek dayOfWeek) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CronDefinition untilDayOfWeek(CronDayOfWeek dayOfWeek) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
