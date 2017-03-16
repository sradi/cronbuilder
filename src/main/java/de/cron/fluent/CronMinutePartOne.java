package de.cron.fluent;

public interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(int... minutes);
	
	CronMinutePartTwo fromMinute(int minute);

}
