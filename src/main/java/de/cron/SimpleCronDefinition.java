package de.cron;

import de.cron.string.day.CronDay;
import de.cron.string.dayofweek.CronDayOfWeek;
import de.cron.string.hours.CronHour;
import de.cron.string.minutes.CronMinute;
import de.cron.string.month.CronMonth;

public class SimpleCronDefinition implements CronDefinition {

	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private CronMinute minuteDefinition;
	private CronHour hourDefinition;
	private CronDay dayDefinition;
	private CronMonth monthDefinition;
	private CronDayOfWeek dayOfWeekDefinition;
	
	public SimpleCronDefinition(CronMinute minute, CronHour hour, CronDay day, CronMonth month, CronDayOfWeek dayOfWeek) {
		this.minuteDefinition = minute;
		this.hourDefinition = hour;
		this.dayDefinition = day;
		this.monthDefinition = month;
		this.dayOfWeekDefinition = dayOfWeek;
	}

	@Override
	public String toString() {
		StringBuilder cronStringBuilder = new StringBuilder();
		cronStringBuilder
			.append(minuteDefinition)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(hourDefinition)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(dayDefinition)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(monthDefinition)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(dayOfWeekDefinition);
		return cronStringBuilder.toString();
	}

}
