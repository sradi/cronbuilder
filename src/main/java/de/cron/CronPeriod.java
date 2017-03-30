package de.cron;

import java.time.DayOfWeek;
import java.time.LocalDate;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronDayOfWeekRange;
import de.cron.elements.CronElementEvery;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.elements.CronSpecificDaysOfWeek;
import de.cron.units.Day;
import de.cron.units.Hour;
import de.cron.units.Minute;

public class CronPeriod implements ComplexCronDayOfWeekPartOne, ComplexCronDayOfWeekPartTwo {

	private DayOfWeek fromDayOfWeek;

	private CronMinute minuteElement;
	private CronHour hourElement;
	private CronDay dayElement;
	private CronDayOfWeek dayOfWeekElement;

	private Minute fromMinute;
	private Minute untilMinute;
	private Hour fromHour;
	private Hour untilHour;
	private LocalDate fromDate;
	private LocalDate untilDate;

	CronPeriod(CronMinute minuteElement, Hour fromHour, Hour untilHour, LocalDate fromDate, LocalDate untilDate) {
		this.minuteElement = minuteElement;
		this.fromHour = fromHour;
		this.untilHour = untilHour;
		this.fromDate = fromDate;
		this.untilDate = untilDate;
	}

	CronPeriod(CronMinute minuteElement, CronHour hourElement, LocalDate fromDate, LocalDate untilDate) {
		this.minuteElement = minuteElement;
		this.hourElement = hourElement;
		this.fromDate = fromDate;
		this.untilDate = untilDate;
	}

	@Override
	public ComplexCronLastPart everyDayOfWeek() {
		this.dayOfWeekElement = CronElementEvery.INSTANCE;
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
		if (this.dayOfWeekElement == null) {
			this.dayOfWeekElement = CronElementEvery.INSTANCE;
		}

		return new CronPeriodExpression.CronPeriodExpressionBuilder()
				.setMinuteDefinition(minuteElement)
				.setHourDefinition(hourElement)
				.setDayDefinition(dayElement)
				.setFromMinute(fromMinute)
				.setUntilMinute(untilMinute)
				.setFromHour(fromHour)
				.setUntilHour(untilHour)
				.setFromDay(Day.fromInt(fromDate.getDayOfMonth()))
				.setUntilDay(Day.fromInt(untilDate.getDayOfMonth()))
				.setFromMonth(fromDate.getMonth())
				.setUntilMonth(untilDate.getMonth())
				.setDayOfWeekDefinition(dayOfWeekElement)
				.build();
	}

}
