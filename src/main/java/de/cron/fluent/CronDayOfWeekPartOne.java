package de.cron.fluent;

import java.time.DayOfWeek;

import de.cron.CronDefinition;

public interface CronDayOfWeekPartOne {
	
	CronDefinition everyDayOfWeek();
	
	CronDefinition onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek);
	
	CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek);

}
