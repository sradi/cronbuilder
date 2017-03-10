package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.DayStringRepresentation;

public interface CronHourPartTwo {
	
	CronDayPartOne until(CronElementDefinition<DayStringRepresentation> day);

}
