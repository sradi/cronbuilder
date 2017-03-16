package de.cron;

import java.time.LocalDate;

interface CronHourPartOne extends CronLastPart {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(int... hours);
	
	CronHourPartTwo fromHour(int hour);

	CronDayAndDatePeriodPart from(int hour, LocalDate from);
}
