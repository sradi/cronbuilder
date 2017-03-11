package de.cron.fluent;

import de.cron.string.day.CronDay;

public interface CronDayPartOne {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(CronDay... days);
	
	CronDayPartTwo fromDay(CronDay day);

}
