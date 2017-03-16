package de.cron;

import java.time.DayOfWeek;
import java.time.Month;

import com.google.common.base.Preconditions;

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
	
	private Minute fromMinute;
	private Hour fromHour;
	private Day fromDay;
	private Month fromMonth;
	private DayOfWeek fromDayOfWeek;
	
	private CronMinute minuteDefinition;
	private CronHour hourDefinition;
	private CronDay dayDefinition;
	private CronMonth monthDefinition;
	private CronDayOfWeek dayOfWeekDefinition;
	
	private Cron() {
	}
	
	public static CronMinutePartOne cron() {
		return new Cron();
	}

	@Override
	public CronHourPartOne everyMinute() {
		this.minuteDefinition = CronMinute.EVERY_MINUTE;
		return this;
	}
	
	@Override
	public CronHourPartOne inTheseMinutes(Minute... minutes) {
		Preconditions.checkArgument(minutes.length > 0);
		this.minuteDefinition = new CronSpecificMinutes(minutes);
		return this;
	}
	
	@Override
	public CronMinutePartTwo fromMinute(Minute minute) {
		this.fromMinute = minute;
		return this;
	}
	
	@Override
	public CronHourPartOne untilMinute(Minute minute) {
		this.minuteDefinition = new CronMinuteRange(fromMinute, minute);
		return this;
	}

	
	//***************************************************************
	@Override
	public CronDayPartOne everyHour() {
		this.hourDefinition = CronHour.EVERY_HOUR;
		return this;
	}
	
	@Override
	public CronDayPartOne inTheseHours(Hour... hours) {
		Preconditions.checkArgument(hours.length > 0);
		this.hourDefinition = new CronSpecificHours(hours);
		return this;
	}
	
	@Override
	public CronHourPartTwo fromHour(Hour hour) {
		this.fromHour = hour;
		return this;
	}
	
	@Override
	public CronDayPartOne untilHour(Hour hour) {
		this.hourDefinition = new CronHourRange(fromHour, hour);
		return this;
	}

	//***************************************************************	
	@Override
	public CronMonthPartOne everyDay() {
		this.dayDefinition = CronDay.EVERY_DAY;
		return this;
	}
	
	@Override
	public CronMonthPartOne onTheseDays(Day... days) {
		Preconditions.checkArgument(days.length > 0);
		this.dayDefinition = new CronSpecificDays(days);
		return this;
	}
	
	@Override
	public CronDayPartTwo fromDay(Day day) {
		this.fromDay = day;
		return this;
	}
	
	@Override
	public CronMonthPartOne untilDay(Day day) {
		this.dayDefinition = new CronDayRange(fromDay, day);
		return this;
	}

	//***************************************************************
	@Override
	public CronDayOfWeekPartOne everyMonth() {
		this.monthDefinition = CronMonth.EVERY_MONTH;
		return this;
	}
	
	@Override
	public CronDayOfWeekPartOne inTheseMonths(Month... months) {
		Preconditions.checkArgument(months.length > 0);
		this.monthDefinition = new CronSpecificMonths(months);
		return this;
	}
	
	@Override
	public CronMonthPartTwo fromMonth(Month month) {
		this.fromMonth = month;
		return this;
	}
	
	@Override
	public CronDayOfWeekPartOne untilMonth(Month month) {
		this.monthDefinition = new CronMonthRange(fromMonth, month);
		return this;
	}	

	//***************************************************************
	@Override
	public CronDefinition everyDayOfWeek() {
		this.dayOfWeekDefinition = CronDayOfWeek.EVERY_DAY_OF_THE_WEEK;
		return new SimpleCronDefinition(minuteDefinition, hourDefinition, dayDefinition, monthDefinition, dayOfWeekDefinition);
	}

	@Override
	public CronDefinition onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		Preconditions.checkArgument(daysOfWeek.length > 0);
		this.dayOfWeekDefinition = new CronSpecificDaysOfWeek(daysOfWeek);
		return new SimpleCronDefinition(minuteDefinition, hourDefinition, dayDefinition, monthDefinition, dayOfWeekDefinition);
	}

	@Override
	public CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek) {
		this.fromDayOfWeek = dayOfWeek;
		return this;
	}

	@Override
	public CronDefinition untilDayOfWeek(DayOfWeek untilDayOfWeek) {
		this.dayOfWeekDefinition = new CronDayOfWeekRange(fromDayOfWeek, untilDayOfWeek);
		return new SimpleCronDefinition(minuteDefinition, hourDefinition, dayDefinition, monthDefinition, dayOfWeekDefinition);
	}
	
}
