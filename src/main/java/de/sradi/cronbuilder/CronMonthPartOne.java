package de.sradi.cronbuilder;

import java.time.Month;

interface CronMonthPartOne extends SimpleCronLastPart {
	
	CronDayOfWeekPartOne everyMonth();
	
	CronDayOfWeekPartOne aRandomMonth();
	
	CronDayOfWeekPartOne inTheseMonths(Month... months);
	
	CronMonthPartTwo fromMonth(Month month);

}
