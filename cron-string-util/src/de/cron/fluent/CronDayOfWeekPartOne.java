package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.string.CronMonth;

public interface CronDayOfWeekPartOne {
	
	CronDefinition everyDayofWeek();
	
	CronDefinition onTheseDaysOfTheWeek(CronMonth... daysOfWeek);
	
	CronDayOfWeekPartTwo from(CronMonth dayOfWeek);

}
