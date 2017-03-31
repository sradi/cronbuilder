package de.sradi.cronbuilder;

import java.time.LocalDate;

interface CronMinutePartOne {
	
	CronHourPartOne everyMinute();
	
	CronHourPartOne aRandomMinute();
	
	CronHourPartOne inTheseMinutes(int... minutes);
	
	CronMinutePartTwo fromMinute(int minute);
	
	CronMinuteBasedPeriodPartOne from(int minute, int hour, LocalDate from);

}
