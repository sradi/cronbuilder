package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.HourStringRepresentation;

public interface CronMinutePartTwo {
	
	CronHourPartOne until(CronElementDefinition<HourStringRepresentation> hour);

}
