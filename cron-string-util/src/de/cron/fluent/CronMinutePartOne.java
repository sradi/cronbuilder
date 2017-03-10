package de.cron.fluent;

import de.cron.string.CronMinute;

public interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(CronMinute... minutes);
	
	CronMinutePartTwo fromMinute(CronMinute minute);

}
