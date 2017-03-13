package de.cron.fluent;

import de.cron.Minute;

public interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(Minute... minutes);
	
	CronMinutePartTwo fromMinute(Minute minute);

}
