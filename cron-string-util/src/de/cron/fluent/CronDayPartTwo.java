package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.DayStringRepresentation;

public interface CronDayPartTwo {
	
	CronMonthPartOne until(CronElementDefinition<DayStringRepresentation> day);

}
