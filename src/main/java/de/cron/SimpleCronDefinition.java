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
	
	private SimpleCronDefinition(CronMinute minute, CronHour hour, CronDay day, CronMonth month, CronDayOfWeek dayOfWeek) {
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
	
	public static class SimpleCronDefinitionBuilder {
		
		private CronMinute minuteDefinition;
		private CronHour hourDefinition;
		private CronDay dayDefinition;
		private CronMonth monthDefinition;
		private CronDayOfWeek dayOfWeekDefinition;
		
		public SimpleCronDefinitionBuilder setMinuteDefinition(CronMinute minuteDefinition) {
			this.minuteDefinition = minuteDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setHourDefinition(CronHour hourDefinition) {
			this.hourDefinition = hourDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setDayDefinition(CronDay dayDefinition) {
			this.dayDefinition = dayDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setMonthDefinition(CronMonth monthDefinition) {
			this.monthDefinition = monthDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setDayOfWeekDefinition(CronDayOfWeek dayOfWeekDefinition) {
			this.dayOfWeekDefinition = dayOfWeekDefinition;
			return this;
		}
		
		public SimpleCronDefinition build() {
			return new SimpleCronDefinition(minuteDefinition, hourDefinition, dayDefinition, monthDefinition, dayOfWeekDefinition);
		}
		
	}

}
