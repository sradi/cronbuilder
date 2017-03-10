package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.string.CronDayOfWeek;

public interface CronDayOfWeekPartTwo {
	
	CronDefinition until(CronDayOfWeek dayOfWeek);

}
