package de.cron.fluent;

import java.time.DayOfWeek;

public interface CronDayOfWeekPartOne extends CronLastPart {
	
	CronLastPart everyDayOfWeek();
	
	CronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek);
	
	CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek);

}
