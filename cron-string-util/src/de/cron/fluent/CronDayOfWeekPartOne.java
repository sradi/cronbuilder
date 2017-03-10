package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.element.CronElementDefinition;
import de.cron.string.MonthStringRepresentation;

public interface CronDayOfWeekPartOne {
	
	CronDefinition everyDayofWeek();
	
	CronDefinition onTheseDaysOfTheWeek(CronElementDefinition<MonthStringRepresentation>... daysOfWeek);
	
	CronDayOfWeekPartTwo from(CronElementDefinition<MonthStringRepresentation> dayOfWeek);

}
