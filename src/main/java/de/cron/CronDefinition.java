package de.cron;

import de.cron.string.day.CronDay;
import de.cron.string.dayofweek.CronDayOfWeek;
import de.cron.string.hours.CronHour;
import de.cron.string.minutes.CronMinute;
import de.cron.string.month.CronMonth;

public interface CronDefinition {
	
	CronMinute getMinuteDefinition();
	
	CronHour getHourDefinition();
	
	CronDay getDayDefinition();
	
	CronMonth getMonthDefinition();
	
	CronDayOfWeek getDayOfWeekDefinition();
	
//	String createString();

}
