package de.cron.fluent;

import de.cron.string.CronDay;

public interface CronDayPartOne {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(CronDay... days);
	
	CronDayPartTwo from(CronDay day);

}
