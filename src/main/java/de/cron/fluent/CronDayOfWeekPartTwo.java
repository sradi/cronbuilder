package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.string.dayofweek.CronDayOfWeek;

public interface CronDayOfWeekPartTwo {
	
	CronDefinition untilDayOfWeek(CronDayOfWeek dayOfWeek);

}
