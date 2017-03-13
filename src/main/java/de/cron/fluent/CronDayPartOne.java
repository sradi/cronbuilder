package de.cron.fluent;

import de.cron.Day;

public interface CronDayPartOne {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(Day... days);
	
	CronDayPartTwo fromDay(Day day);

}
