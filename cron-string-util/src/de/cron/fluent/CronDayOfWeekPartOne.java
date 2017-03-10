package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.string.CronDayOfWeek;

public interface CronDayOfWeekPartOne {
	
	CronDefinition everyDayofWeek();
	
	CronDefinition onTheseDaysOfTheWeek(CronDayOfWeek... daysOfWeek);
	
	CronDayOfWeekPartTwo from(CronDayOfWeek dayOfWeek);

}
