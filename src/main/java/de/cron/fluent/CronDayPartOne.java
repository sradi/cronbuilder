package de.cron.fluent;

public interface CronDayPartOne {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(int... days);
	
	CronDayPartTwo fromDay(int day);

}
