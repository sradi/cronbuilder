package de.cron.fluent;

import java.time.LocalDate;

public interface CronDayPartOne {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(int... days);
	
	CronDayPartTwo fromDay(int day);
	
	CronDatePeriodPart from(LocalDate from);

}
