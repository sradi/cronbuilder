package de.cron.fluent;

import java.time.LocalDate;

public interface CronDayPartOne extends CronLastPart {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(int... days);
	
	CronDayPartTwo fromDay(int day);
	
	CronDatePeriodPart from(LocalDate from);

}
