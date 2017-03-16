package de.cron;

import java.time.Month;

public interface CronMonthPartOne extends CronLastPart {
	
	CronDayOfWeekPartOne everyMonth();
	
	CronDayOfWeekPartOne inTheseMonths(Month... months);
	
	CronMonthPartTwo fromMonth(Month month);

}
