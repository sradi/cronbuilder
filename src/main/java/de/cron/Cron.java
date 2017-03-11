package de.cron;

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
import de.cron.string.CronDay;
import de.cron.string.CronDayOfWeek;
import de.cron.string.CronMonth;
import de.cron.string.hours.CronHour;
import de.cron.string.hours.CronHourRange;
import de.cron.string.hours.CronSpecificHours;
import de.cron.string.minutes.CronMinute;
import de.cron.string.minutes.CronMinuteRange;
import de.cron.string.minutes.CronSpecificMinutes;

public class Cron implements CronMinutePartOne, CronMinutePartTwo, CronHourPartOne, CronHourPartTwo, CronDayPartOne,CronDayPartTwo, CronMonthPartOne, CronMonthPartTwo, CronDayOfWeekPartOne, CronDayOfWeekPartTwo {
	
	private CronMinute minute;
	private int fromMinute;
	private CronHour hour;
	private int fromHour;
	
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
	public CronHourPartOne untilMinute(CronMinute minute) {
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CronMonthPartOne onTheseDays(CronDay... days) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CronDayPartTwo fromDay(CronDay day) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CronMonthPartOne untilDay(CronDay day) {
		// TODO Auto-generated method stub
		return null;
	}

	//***************************************************************
	@Override
	public CronDayOfWeekPartOne everyMonth() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CronDayOfWeekPartOne inTheseMonths(CronMonth... months) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CronMonthPartTwo fromMonth(CronMonth month) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CronDayOfWeekPartOne untilMonth(CronMonth month) {
		// TODO Auto-generated method stub
		return null;
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
