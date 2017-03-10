package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.MonthStringRepresentation;

public interface CronMonthPartTwo {
	
	CronDayOfWeekPartOne until(CronElementDefinition<MonthStringRepresentation> month);

}
