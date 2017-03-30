package de.sradi.cronbuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.elements.CronDay;
import de.sradi.cronbuilder.elements.CronDayOfWeek;
import de.sradi.cronbuilder.elements.CronDayOfWeekRange;
import de.sradi.cronbuilder.elements.CronDayRange;
import de.sradi.cronbuilder.elements.CronElementEvery;
import de.sradi.cronbuilder.elements.CronHour;
import de.sradi.cronbuilder.elements.CronHourRange;
import de.sradi.cronbuilder.elements.CronMinute;
import de.sradi.cronbuilder.elements.CronMinuteRange;
import de.sradi.cronbuilder.elements.CronMonth;
import de.sradi.cronbuilder.elements.CronMonthRange;
import de.sradi.cronbuilder.elements.CronSpecificDays;
import de.sradi.cronbuilder.elements.CronSpecificDaysOfWeek;
import de.sradi.cronbuilder.elements.CronSpecificHours;
import de.sradi.cronbuilder.elements.CronSpecificMinutes;
import de.sradi.cronbuilder.elements.CronSpecificMonths;
import de.sradi.cronbuilder.units.Day;
import de.sradi.cronbuilder.units.Hour;
import de.sradi.cronbuilder.units.Minute;

public class Cron implements CronMinutePartOne, CronMinutePartTwo, CronMinuteBasedPeriodPartOne, CronHourPartOne, CronHourPartTwo, CronHourBasedPeriodPartOne, CronDayPartOne,
		CronDayPartTwo, CronDatePeriodPart, CronMonthPartOne, CronMonthPartTwo, CronDayOfWeekPartOne,
		CronDayOfWeekPartTwo {

	private Minute fromMinute;
	private Hour fromHour;
	private Day fromDay;
	private Month fromMonth;
	private DayOfWeek fromDayOfWeek;

	private LocalDate fromDate;
	
	private CronMinute minuteElement;
	private CronHour hourElement;
	private CronDay dayElement;
	private CronMonth monthElement;
	private CronDayOfWeek dayOfWeekElement;

	private Cron() {
	}

	public static CronMinutePartOne cron() {
		return new Cron();
	}
	
	@Override
	public CronMinuteBasedPeriodPartOne from(int minute, int hour, LocalDate from) {
		this.fromMinute = Minute.fromInt(minute);
		this.fromHour = Hour.fromInt(hour);
		this.fromDate = from;
		return this;
	}

	@Override
	public CronPeriodDayOfWeekPartOne until(int minute, int hour, LocalDate until) {
		return new CronPeriod(
				fromMinute,
				Minute.fromInt(minute),
				fromHour,
				Hour.fromInt(hour),
				fromDate,
				until);
	}

	@Override
	public CronHourPartOne everyMinute() {
		this.minuteElement = CronElementEvery.INSTANCE;
		return this;
	}

	@Override
	public CronHourPartOne inTheseMinutes(int... minutes) {
		Preconditions.checkArgument(minutes.length > 0);
		
		this.minuteElement = new CronSpecificMinutes(
						IntStream.of(minutes).mapToObj(Minute::fromInt).toArray(s -> new Minute[s]));
		return this;
	}

	@Override
	public CronMinutePartTwo fromMinute(int minute) {
		this.fromMinute = Minute.fromInt(minute);
		return this;
	}

	@Override
	public CronHourPartOne untilMinute(int minute) {
		this.minuteElement = new CronMinuteRange(fromMinute, Minute.fromInt(minute));
		return this;
	}
	
	@Override
	public CronHourBasedPeriodPartOne from(int hour, LocalDate from) {
		this.fromHour = Hour.fromInt(hour);
		this.fromDate = from;
		return this;
	}

	@Override
	public CronPeriodDayOfWeekPartOne until(int hour, LocalDate until) {
		return new CronPeriod(
				minuteElement,
				fromHour,
				Hour.fromInt(hour),
				fromDate,
				until);
	}

	// ***************************************************************
	@Override
	public CronDayPartOne everyHour() {
		this.hourElement = CronElementEvery.INSTANCE;
		return this;
	}

	@Override
	public CronDayPartOne inTheseHours(int... hours) {
		Preconditions.checkArgument(hours.length > 0);
		
		this.hourElement = new CronSpecificHours(
						IntStream.of(hours).mapToObj(Hour::fromInt).toArray(s -> new Hour[s]));
		return this;
	}

	@Override
	public CronHourPartTwo fromHour(int hour) {
		this.fromHour = Hour.fromInt(hour);
		return this;
	}

	@Override
	public CronDayPartOne untilHour(int hour) {
		this.hourElement = new CronHourRange(fromHour, Hour.fromInt(hour));
		return this;
	}

	@Override
	public CronDatePeriodPart from(LocalDate from) {
		this.fromDate = from;
		return this;
	}

	@Override
	public CronPeriodDayOfWeekPartOne until(LocalDate until) {
		return new CronPeriod(
				minuteElement,
				hourElement,
				fromDate,
				until);
	}

	// ***************************************************************
	@Override
	public CronMonthPartOne everyDay() {
		this.dayElement = CronElementEvery.INSTANCE;
		return this;
	}

	@Override
	public CronMonthPartOne onTheseDays(int... days) {
		Preconditions.checkArgument(days.length > 0);
		
		this.dayElement = new CronSpecificDays(IntStream.of(days).mapToObj(Day::fromInt).toArray(s -> new Day[s]));
		return this;
	}

	@Override
	public CronDayPartTwo fromDay(int day) {
		this.fromDay = Day.fromInt(day);
		return this;
	}

	@Override
	public CronMonthPartOne untilDay(int day) {
		this.dayElement = new CronDayRange(fromDay, Day.fromInt(day));
		return this;
	}

	// ***************************************************************
	@Override
	public CronDayOfWeekPartOne everyMonth() {
		this.monthElement = CronElementEvery.INSTANCE;
		return this;
	}

	@Override
	public CronDayOfWeekPartOne inTheseMonths(Month... months) {
		Preconditions.checkArgument(months.length > 0);
		
		this.monthElement = new CronSpecificMonths(months);
		return this;
	}

	@Override
	public CronMonthPartTwo fromMonth(Month month) {
		this.fromMonth = month;
		return this;
	}

	@Override
	public CronDayOfWeekPartOne untilMonth(Month month) {
		this.monthElement = new CronMonthRange(fromMonth, month);
		return this;
	}

	// ***************************************************************
	@Override
	public SimpleCronLastPart everyDayOfWeek() {
		this.dayOfWeekElement = CronElementEvery.INSTANCE;
		return this;
	}

	@Override
	public SimpleCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		Preconditions.checkArgument(daysOfWeek.length > 0);
		
		this.dayOfWeekElement = new CronSpecificDaysOfWeek(daysOfWeek);
		return this;
	}

	@Override
	public CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek) {
		this.fromDayOfWeek = dayOfWeek;
		return this;
	}

	@Override
	public SimpleCronLastPart untilDayOfWeek(DayOfWeek untilDayOfWeek) {
		this.dayOfWeekElement = new CronDayOfWeekRange(fromDayOfWeek, untilDayOfWeek);
		return this;
	}

	// ***************************************************************

	@Override
	public CronExpression get() {
		return new CronExpression.CronExpressionBuilder()
				.setMinuteDefinition(minuteElement)
				.setHourDefinition(hourElement)
				.setDayDefinition(dayElement)
				.setMonthDefinition(monthElement)
				.setDayOfWeekDefinition(dayOfWeekElement)
				.build();
	}

}
