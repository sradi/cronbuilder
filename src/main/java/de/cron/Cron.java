package de.cron;

import java.time.DayOfWeek;
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
import de.cron.string.day.CronDay;
import de.cron.string.day.CronDayRange;
import de.cron.string.day.CronSpecificDays;
import de.cron.string.dayofweek.CronDayOfWeek;
import de.cron.string.dayofweek.CronDayOfWeekRange;
import de.cron.string.dayofweek.CronSpecificDaysOfWeek;
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
	
	private CronDefinition cronDefinition;
	private Minute fromMinute;
	private int fromHour;
	private int fromDay;
	private Month fromMonth;

	private DayOfWeek fromDayOfWeek;
	
	private Cron() {
		this.cronDefinition = new CronDefinition();
	}
	
	public static CronMinutePartOne cron() {
		return new Cron();
	}

	@Override
	public CronHourPartOne everyMinute() {
		this.cronDefinition.setMinute(CronMinute.EVERY_MINUTE);
		return this;
	}
	
	@Override
	public CronHourPartOne inTheseMinutes(Minute... minutes) {
		this.cronDefinition.setMinute(new CronSpecificMinutes(minutes));
		return this;
	}
	
	@Override
	public CronMinutePartTwo fromMinute(Minute minute) {
		this.fromMinute = minute;
		return this;
	}
	
	@Override
	public CronHourPartOne untilMinute(Minute minute) {
		this.cronDefinition.setMinute(new CronMinuteRange(fromMinute, minute));
		return this;
	}

	
	//***************************************************************
	@Override
	public CronDayPartOne everyHour() {
		this.cronDefinition.setHour(CronHour.EVERY_HOUR);
		return this;
	}
	
	@Override
	public CronDayPartOne inTheseHours(int... hours) {
		this.cronDefinition.setHour(new CronSpecificHours(hours));
		return this;
	}
	
	@Override
	public CronHourPartTwo fromHour(int hour) {
		this.fromHour = hour;
		return this;
	}
	
	@Override
	public CronDayPartOne untilHour(int hour) {
		this.cronDefinition.setHour(new CronHourRange(fromHour, hour));
		return this;
	}

	//***************************************************************	
	@Override
	public CronMonthPartOne everyDay() {
		this.cronDefinition.setDay(CronDay.EVERY_DAY);
		return this;
	}
	
	@Override
	public CronMonthPartOne onTheseDays(int... days) {
		this.cronDefinition.setDay(new CronSpecificDays(days));
		return this;
	}
	
	@Override
	public CronDayPartTwo fromDay(int day) {
		this.fromDay = day;
		return this;
	}
	
	@Override
	public CronMonthPartOne untilDay(int day) {
		this.cronDefinition.setDay(new CronDayRange(fromDay, day));
		return this;
	}

	//***************************************************************
	@Override
	public CronDayOfWeekPartOne everyMonth() {
		this.cronDefinition.setMonth(CronMonth.EVERY_MONTH);
		return this;
	}
	
	@Override
	public CronDayOfWeekPartOne inTheseMonths(Month... months) {
		this.cronDefinition.setMonth(new CronSpecificMonths(months));
		return this;
	}
	
	@Override
	public CronMonthPartTwo fromMonth(Month month) {
		this.fromMonth = month;
		return this;
	}
	
	@Override
	public CronDayOfWeekPartOne untilMonth(Month month) {
		this.cronDefinition.setMonth(new CronMonthRange(fromMonth, month));
		return this;
	}	

	//***************************************************************
	@Override
	public CronDefinition everyDayOfWeek() {
		this.cronDefinition.setDayOfWeek(CronDayOfWeek.EVERY_DAY_OF_THE_WEEK);
		return this.cronDefinition;
	}

	@Override
	public CronDefinition onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		this.cronDefinition.setDayOfWeek(new CronSpecificDaysOfWeek(daysOfWeek));
		return this.cronDefinition;
	}

	@Override
	public CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek) {
		this.fromDayOfWeek = dayOfWeek;
		return this;
	}

	@Override
	public CronDefinition untilDayOfWeek(CronDayOfWeek dayOfWeek) {
		this.cronDefinition.setDayOfWeek(new CronDayOfWeekRange(fromDayOfWeek, dayOfWeek));
		return this.cronDefinition;
	}
	
}
