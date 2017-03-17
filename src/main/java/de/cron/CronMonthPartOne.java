package de.cron;

import java.time.Month;

interface CronMonthPartOne extends SimpleCronLastPart {
	
	CronDayOfWeekPartOne everyMonth();
	
	CronDayOfWeekPartOne inTheseMonths(Month... months);
	
	CronMonthPartTwo fromMonth(Month month);

}
