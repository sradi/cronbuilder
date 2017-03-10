package de.cron.fluent;

import de.cron.string.CronHour;

public interface CronHourPartOne {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(CronHour... hours);
	
	CronHourPartTwo from(CronHour hour);

}
