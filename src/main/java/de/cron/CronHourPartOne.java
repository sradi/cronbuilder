package de.cron;

public interface CronHourPartOne extends CronLastPart {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(int... hours);
	
	CronHourPartTwo fromHour(int hour);

}
