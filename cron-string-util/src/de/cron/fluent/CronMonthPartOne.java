package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.MonthStringRepresentation;

public interface CronMonthPartOne {
	
	CronDayOfWeekPartOne everyMonth();
	
	CronDayOfWeekPartOne inTheseMonths(CronElementDefinition<MonthStringRepresentation>... months);
	
	CronMonthPartTwo from(CronElementDefinition<MonthStringRepresentation> month);

}
