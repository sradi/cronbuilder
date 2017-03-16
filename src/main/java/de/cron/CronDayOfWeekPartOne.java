package de.cron;

import java.time.DayOfWeek;

interface CronDayOfWeekPartOne extends CronLastPart {
	
	CronLastPart everyDayOfWeek();
	
	CronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek);
	
	CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek);

}
