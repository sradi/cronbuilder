package de.cron;

interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne inTheseMinutes(int... minutes);
	
	CronMinutePartTwo fromMinute(int minute);

}
