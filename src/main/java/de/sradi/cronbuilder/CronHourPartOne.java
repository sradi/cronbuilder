package de.sradi.cronbuilder;

import java.time.LocalDate;

interface CronHourPartOne extends SimpleCronLastPart {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(int... hours);
	
	CronHourPartTwo fromHour(int hour);

	CronHourBasedPeriodPartOne from(int hour, LocalDate from);
}
