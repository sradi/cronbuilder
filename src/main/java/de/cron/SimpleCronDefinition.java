package de.cron;

import de.cron.string.day.CronDay;
import de.cron.string.dayofweek.CronDayOfWeek;
import de.cron.string.hours.CronHour;
import de.cron.string.minutes.CronMinute;
import de.cron.string.month.CronMonth;

public class SimpleCronDefinition implements CronDefinition {

	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private CronMinute minute;
	private CronHour hour;
	private CronDay day;
	private CronMonth month;
	private CronDayOfWeek dayOfWeek;
	
	public SimpleCronDefinition(CronMinute minute, CronHour hour, CronDay day, CronMonth month, CronDayOfWeek dayOfWeek) {
		this.minute = minute;
		this.hour = hour;
		this.day = day;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public String toString() {
		StringBuilder cronStringBuilder = new StringBuilder();
		cronStringBuilder
			.append(minute)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(hour)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(day)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(month)
			.append(CRON_ELEMENT_SEPARATOR)
			.append(dayOfWeek);
		return cronStringBuilder.toString();
	}

}
