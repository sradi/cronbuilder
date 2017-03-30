package de.cron;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronDayOfWeekRange;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronElementEvery;
import de.cron.elements.CronHour;
import de.cron.elements.CronHourRange;
import de.cron.elements.CronMinute;
import de.cron.elements.CronMinuteRange;
import de.cron.elements.CronMonth;
import de.cron.elements.CronMonthRange;
import de.cron.elements.CronSpecificDays;
import de.cron.elements.CronSpecificDaysOfWeek;
import de.cron.elements.CronSpecificHours;
import de.cron.elements.CronSpecificMinutes;
import de.cron.elements.CronSpecificMonths;
import de.cron.units.Day;
import de.cron.units.Hour;
import de.cron.units.Minute;

public class Cron implements CronMinutePartOne, CronMinutePartTwo, CronHourPartOne, CronHourPartTwo, CronDayAndDatePeriodPartOne, CronDayPartOne,
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
	public CronDayAndDatePeriodPartOne from(int hour, LocalDate from) {
		this.fromHour = Hour.fromInt(hour);
		this.fromDate = from;
		return this;
	}

	@Override
	public ComplexCronLastPart until(int hour, LocalDate until) {
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
	public ComplexCronDayOfWeekPartOne until(LocalDate until) {
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
