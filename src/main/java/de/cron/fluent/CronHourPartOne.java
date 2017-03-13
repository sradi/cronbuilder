package de.cron.fluent;

import de.cron.Hour;

public interface CronHourPartOne {
	
	CronDayPartOne everyHour();
	
	CronDayPartOne inTheseHours(Hour... hours);
	
	CronHourPartTwo fromHour(Hour hour);

}
