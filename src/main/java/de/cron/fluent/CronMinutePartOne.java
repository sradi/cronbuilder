package de.cron.fluent;

import de.cron.string.minutes.CronMinute;

public interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(int... minutes);
	
	CronMinutePartTwo fromMinute(int minute);

}
