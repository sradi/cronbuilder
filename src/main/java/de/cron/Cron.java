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
	
	@Override
	public CronDayAndDatePeriodPartOne from(int hour, LocalDate from) {
		this.fromHour = Hour.fromInt(hour);
		this.fromDate = from;
		return this;
	}

	@Override
	public ComplexCronLastPart until(int hour, LocalDate until) {
		return new ComplexCron(new ComplexCronDefinition.ComplexCronDefinitionBuilder()
				.setMinuteDefinition(currentCronDefinition.getMinuteDefinition())
				.setFromHour(fromHour)
				.setUntilHour(Hour.fromInt(hour))
				.setFromDate(fromDate)
				.setUntilDate(until)
				.build());
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
	public ComplexCronDayOfWeekPartOne until(LocalDate until) {
		return new ComplexCron(new ComplexCronDefinition.ComplexCronDefinitionBuilder()
				.setMinuteDefinition(currentCronDefinition.getMinuteDefinition())
				.setHourDefinition(currentCronDefinition.getHourDefinition())
				.setFromDate(fromDate)
				.setUntilDate(until)
				.build());
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
	public SimpleCronLastPart everyDayOfWeek() {
		this.currentCronDefinition = new SimpleCronDefinition.SimpleCronDefinitionBuilder(currentCronDefinition)
				.setDayOfWeekDefinition(CronDayOfWeek.EVERY_DAY_OF_THE_WEEK)
				.build();
		return this;
	}

	@Override
	public SimpleCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
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
	public SimpleCronLastPart untilDayOfWeek(DayOfWeek untilDayOfWeek) {
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
