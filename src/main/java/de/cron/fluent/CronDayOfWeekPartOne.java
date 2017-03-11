package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.string.CronDayOfWeek;

public interface CronDayOfWeekPartOne {
	
	CronDefinition everyDayOfWeek();
	
	CronDefinition onTheseDaysOfTheWeek(CronDayOfWeek... daysOfWeek);
	
	CronDayOfWeekPartTwo fromDayOfWeek(CronDayOfWeek dayOfWeek);

}
