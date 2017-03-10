package de.cron.fluent;

import de.cron.CronDefinition;
import de.cron.element.CronElementDefinition;
import de.cron.string.DayOfWeekStringRepresentation;

public interface CronDayOfWeekPartTwo {
	
	CronDefinition until(CronElementDefinition<DayOfWeekStringRepresentation> dayOfWeek);

}
