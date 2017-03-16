package de.cron;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.elements.CronMonth;

interface CronDefinition {
	
	CronMinute getMinuteDefinition();
	
	CronHour getHourDefinition();
	
	CronDay getDayDefinition();
	
	CronMonth getMonthDefinition();
	
	CronDayOfWeek getDayOfWeekDefinition();
	
//	String createString();

}
