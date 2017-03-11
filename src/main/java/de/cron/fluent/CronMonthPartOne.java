package de.cron.fluent;

import java.time.Month;

public interface CronMonthPartOne {
	
	CronDayOfWeekPartOne everyMonth();
	
	CronDayOfWeekPartOne inTheseMonths(Month... months);
	
	CronMonthPartTwo fromMonth(Month month);

}
