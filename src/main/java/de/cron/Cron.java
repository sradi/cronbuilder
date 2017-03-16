package de.cron;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;

import de.cron.fluent.CronDatePeriodPart;
import de.cron.fluent.CronDayOfWeekPartOne;
import de.cron.fluent.CronDayOfWeekPartTwo;
import de.cron.fluent.CronDayPartOne;
import de.cron.fluent.CronDayPartTwo;
import de.cron.fluent.CronHourPartOne;
import de.cron.fluent.CronHourPartTwo;
import de.cron.fluent.CronLastPart;
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

public class Cron implements CronMinutePartOne, CronMinutePartTwo, CronHourPartOne, CronHourPartTwo, CronDayPartOne,
		CronDayPartTwo, CronDatePeriodPart, CronMonthPartOne, CronMonthPartTwo, CronDayOfWeekPartOne,
		CronDayOfWeekPartTwo, CronLastPart {

	private Minute fromMinute;
	private Hour fromHour;
	private Day fromDay;
	private Month fromMonth;
	private DayOfWeek fromDayOfWeek;

	private LocalDate fromDate;

	private SimpleCronDefinition currentCronDefinition;

	private Cron() {
	}

	public static CronMinutePartOne cron() {
		return new Cron();
	}

	@Override
	public CronHourPartOne everyMinute() {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(CronMinute.EVERY_MINUTE).build();
		return this;
	}

	@Override
	public CronHourPartOne inTheseMinutes(int... minutes) {
		Preconditions.checkArgument(minutes.length > 0);
		
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(new CronSpecificMinutes(
						IntStream.of(minutes).mapToObj(Minute::fromInt).toArray(s -> new Minute[s])))
				.build();
		return this;
	}

	@Override
	public CronMinutePartTwo fromMinute(int minute) {
		this.fromMinute = Minute.fromInt(minute);
		return this;
	}

	@Override
	public CronHourPartOne untilMinute(int minute) {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(new CronMinuteRange(fromMinute, Minute.fromInt(minute)))
				.build();
		return this;
	}

	// ***************************************************************
	@Override
	public CronDayPartOne everyHour() {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setHourDefinition(CronHour.EVERY_HOUR)
				.build();
		return this;
	}

	@Override
	public CronDayPartOne inTheseHours(int... hours) {
		Preconditions.checkArgument(hours.length > 0);
		
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setHourDefinition(new CronSpecificHours(
						IntStream.of(hours).mapToObj(Hour::fromInt).toArray(s -> new Hour[s])))
				.build();
		return this;
	}

	@Override
	public CronHourPartTwo fromHour(int hour) {
		this.fromHour = Hour.fromInt(hour);
		return this;
	}

	@Override
	public CronDayPartOne untilHour(int hour) {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setHourDefinition(new CronHourRange(fromHour, Hour.fromInt(hour)))
				.build();
		return this;
	}

	@Override
	public CronDatePeriodPart from(LocalDate from) {
		this.fromDate = from;
		return this;
	}

	@Override
	public ComplexCronDefinition until(LocalDate until) {
		return new ComplexCronDefinition(currentCronDefinition, fromDate, until);
	}

	// ***************************************************************
	@Override
	public CronMonthPartOne everyDay() {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayDefinition(CronDay.EVERY_DAY)
				.build();
		return this;
	}

	@Override
	public CronMonthPartOne onTheseDays(int... days) {
		Preconditions.checkArgument(days.length > 0);
		
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayDefinition(new CronSpecificDays(IntStream.of(days).mapToObj(Day::fromInt).toArray(s -> new Day[s])))
				.build();
		return this;
	}

	@Override
	public CronDayPartTwo fromDay(int day) {
		this.fromDay = Day.fromInt(day);
		return this;
	}

	@Override
	public CronMonthPartOne untilDay(int day) {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayDefinition(new CronDayRange(fromDay, Day.fromInt(day)))
				.build();
		return this;
	}

	// ***************************************************************
	@Override
	public CronDayOfWeekPartOne everyMonth() {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setMonthDefinition(CronMonth.EVERY_MONTH)
				.build();
		return this;
	}

	@Override
	public CronDayOfWeekPartOne inTheseMonths(Month... months) {
		Preconditions.checkArgument(months.length > 0);
		
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setMonthDefinition(new CronSpecificMonths(months))
				.build();
		return this;
	}

	@Override
	public CronMonthPartTwo fromMonth(Month month) {
		this.fromMonth = month;
		return this;
	}

	@Override
	public CronDayOfWeekPartOne untilMonth(Month month) {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setMonthDefinition(new CronMonthRange(fromMonth, month))
				.build();
		return this;
	}

	// ***************************************************************
	@Override
	public CronLastPart everyDayOfWeek() {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayOfWeekDefinition(CronDayOfWeek.EVERY_DAY_OF_THE_WEEK)
				.build();
		return this;
	}

	@Override
	public CronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		Preconditions.checkArgument(daysOfWeek.length > 0);
		
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayOfWeekDefinition(new CronSpecificDaysOfWeek(daysOfWeek))
				.build();
		return this;
	}

	@Override
	public CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek) {
		this.fromDayOfWeek = dayOfWeek;
		return this;
	}

	@Override
	public CronLastPart untilDayOfWeek(DayOfWeek untilDayOfWeek) {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayOfWeekDefinition(new CronDayOfWeekRange(fromDayOfWeek, untilDayOfWeek))
				.build();
		return this;
	}

	// ***************************************************************

	@Override
	public CronDefinition get() {
		return currentCronDefinition;
	}
}
