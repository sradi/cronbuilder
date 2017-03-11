package de.cron.fluent;

import de.cron.string.CronMonth;

public interface CronMonthPartOne {
	
	CronDayOfWeekPartOne everyMonth();
	
	CronDayOfWeekPartOne inTheseMonths(CronMonth... months);
	
	CronMonthPartTwo fromMonth(CronMonth month);

}
