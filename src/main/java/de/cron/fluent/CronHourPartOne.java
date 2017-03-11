package de.cron.fluent;

public interface CronHourPartOne {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(int... hours);
	
	CronHourPartTwo fromHour(int hour);

}
