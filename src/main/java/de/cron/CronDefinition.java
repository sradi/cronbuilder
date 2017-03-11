package de.cron;

import de.cron.string.day.CronDay;
import de.cron.string.dayofweek.CronDayOfWeek;
import de.cron.string.hours.CronHour;
import de.cron.string.minutes.CronMinute;
import de.cron.string.month.CronMonth;

public class CronDefinition {

	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private CronMinute minute;
	private CronHour hour;
	private CronDay day;
	private CronMonth month;
	private CronDayOfWeek dayOfWeek;

	void setMinute(CronMinute minute) {
		this.minute = minute;
	}

	void setHour(CronHour hour) {
		this.hour = hour;
	}

	void setDay(CronDay day) {
		this.day = day;
	}

	void setMonth(CronMonth month) {
		this.month = month;
	}

	void setDayOfWeek(CronDayOfWeek dayOfWeek) {
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
