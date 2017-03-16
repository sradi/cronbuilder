package de.cron;

import java.time.LocalDate;

interface CronDayPartOne extends CronLastPart {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(int... days);
	
	CronDayPartTwo fromDay(int day);
	
	CronDatePeriodPart from(LocalDate from);

}
