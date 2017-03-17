package de.cron;

import java.time.LocalDate;

interface CronHourPartOne extends SimpleCronLastPart {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(int... hours);
	
	CronHourPartTwo fromHour(int hour);

	CronDayAndDatePeriodPartOne from(int hour, LocalDate from);
}
