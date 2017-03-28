package de.cron;

import java.time.DayOfWeek;
import java.time.LocalDate;

import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronDayOfWeekRange;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.elements.CronSpecificDaysOfWeek;
import de.cron.elements.period.CronPeriodDayPart;
import de.cron.elements.period.CronPeriodHourPart;
import de.cron.elements.period.CronPeriodMonthPart;
import de.cron.elements.period.CronPeriodPart;
import de.cron.units.Day;
import de.cron.units.Hour;

public class CronPeriod implements ComplexCronDayOfWeekPartOne, ComplexCronDayOfWeekPartTwo {

	private DayOfWeek fromDayOfWeek;

	private CronMinute minuteElement;
	private CronHour hourElement;
	private CronPeriodPart cronPeriod;
	private CronDayOfWeek dayOfWeekElement;

	public CronPeriod(CronMinute minuteElement, Hour fromHour, Hour untilHour, LocalDate fromDate, LocalDate untilDate) {
		this.minuteElement = minuteElement;
		this.cronPeriod = new CronPeriodHourPart(
				new CronPeriodDayPart(
						new CronPeriodMonthPart(fromDate.getMonth(), untilDate.getMonth()),
						Day.fromInt(fromDate.getDayOfMonth()),
						Day.fromInt(untilDate.getDayOfMonth())),
				fromHour, untilHour);
	}

	public CronPeriod(CronMinute minuteElement, CronHour hourElement, LocalDate fromDate, LocalDate untilDate) {
		this.minuteElement = minuteElement;
		this.hourElement = hourElement;
		this.cronPeriod = new CronPeriodDayPart(
				new CronPeriodMonthPart(fromDate.getMonth(), untilDate.getMonth()),
				Day.fromInt(fromDate.getDayOfMonth()),
				Day.fromInt(untilDate.getDayOfMonth()));
	}

	@Override
	public ComplexCronLastPart everyDayOfWeek() {
		this.dayOfWeekElement = CronDayOfWeek.EVERY_DAY_OF_THE_WEEK;
		return this;
	}
	
	@Override
	public ComplexCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		this.dayOfWeekElement = new CronSpecificDaysOfWeek(daysOfWeek);
		return this;
	}
	
	@Override
	public ComplexCronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek fromDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
		return this;
	}

	@Override
	public ComplexCronLastPart untilDayOfWeek(DayOfWeek untilDayOfWeek) {
		this.dayOfWeekElement = new CronDayOfWeekRange(fromDayOfWeek, untilDayOfWeek);
		return this;
	}

	@Override
	public CronPeriodExpression get() {
		return new CronPeriodExpression.ComplexCronDefinitionBuilder()
				.se;
	}

}
