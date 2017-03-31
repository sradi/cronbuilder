package de.sradi.cronbuilder;

import java.time.LocalDate;

interface CronDayPartOne extends SimpleCronLastPart {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne aRandomDay();
	
	CronMonthPartOne onTheseDays(int... days);
	
	CronDayPartTwo fromDay(int day);
	
	CronDatePeriodPart from(LocalDate from);

}
