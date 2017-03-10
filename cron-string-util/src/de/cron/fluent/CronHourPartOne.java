package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.HourStringRepresentation;

public interface CronHourPartOne {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(CronElementDefinition<HourStringRepresentation>... hours);
	
	CronHourPartTwo from(CronElementDefinition<HourStringRepresentation> hour);

}
