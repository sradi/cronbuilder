package de.cron;

import java.time.LocalDate;

interface CronDayPartOne extends SimpleCronLastPart {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(int... days);
	
	CronDayPartTwo fromDay(int day);
	
	CronDatePeriodPart from(LocalDate from);

}