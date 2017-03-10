package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.DayStringRepresentation;

public interface CronDayPartOne {
	
	CronMonthPartOne everyDay();
	
	CronMonthPartOne onTheseDays(CronElementDefinition<DayStringRepresentation>... days);
	
	CronDayPartTwo from(CronElementDefinition<DayStringRepresentation> day);

}
