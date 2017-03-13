package de.cron.fluent;

import java.time.DayOfWeek;

import de.cron.CronDefinition;

public interface CronDayOfWeekPartTwo {
	
	CronDefinition untilDayOfWeek(DayOfWeek dayOfWeek);

}
