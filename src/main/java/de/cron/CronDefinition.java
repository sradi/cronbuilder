package de.cron;

import de.cron.elements.day.CronDay;
import de.cron.elements.dayofweek.CronDayOfWeek;
import de.cron.elements.hours.CronHour;
import de.cron.elements.minutes.CronMinute;
import de.cron.elements.month.CronMonth;

public interface CronDefinition {
	
	CronMinute getMinuteDefinition();
	
	CronHour getHourDefinition();
	
	CronDay getDayDefinition();
	
	CronMonth getMonthDefinition();
	
	CronDayOfWeek getDayOfWeekDefinition();
	
//	String createString();

}
