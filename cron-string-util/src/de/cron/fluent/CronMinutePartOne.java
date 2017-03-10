package de.cron.fluent;

import de.cron.element.CronElementDefinition;
import de.cron.string.MinuteStringRepresentation;

public interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(CronElementDefinition<MinuteStringRepresentation>... minutes);
	
	CronMinutePartTwo from(CronElementDefinition<MinuteStringRepresentation> minute);

}
