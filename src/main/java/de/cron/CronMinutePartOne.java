package de.cron;

public interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(int... minutes);
	
	CronMinutePartTwo fromMinute(int minute);

}
