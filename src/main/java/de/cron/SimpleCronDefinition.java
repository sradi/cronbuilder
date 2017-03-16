package de.cron;

import com.google.common.base.Preconditions;

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
		Preconditions.checkArgument(minute != null);
		Preconditions.checkArgument(hour != null);
		Preconditions.checkArgument(day != null);
		Preconditions.checkArgument(month != null);
		Preconditions.checkArgument(dayOfWeek != null);
		
		this.minuteDefinition = minute;
		this.hourDefinition = hour;
		this.dayDefinition = day;
		this.monthDefinition = month;
		this.dayOfWeekDefinition = dayOfWeek;
	}

	@Override
	public CronMinute getMinuteDefinition() {
		return minuteDefinition;
	}

	@Override
	public CronHour getHourDefinition() {
		return hourDefinition;
	}

	@Override
	public CronDay getDayDefinition() {
		return dayDefinition;
	}

	@Override
	public CronMonth getMonthDefinition() {
		return monthDefinition;
	}

	@Override
	public CronDayOfWeek getDayOfWeekDefinition() {
		return dayOfWeekDefinition;
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
		
		public SimpleCronDefinitionBuilder() {}
		
		public SimpleCronDefinitionBuilder(SimpleCronDefinition baseDefinition) {
			this.minuteDefinition = baseDefinition.minuteDefinition;
			this.hourDefinition = baseDefinition.hourDefinition;
			this.dayDefinition = baseDefinition.dayDefinition;
			this.monthDefinition = baseDefinition.monthDefinition;
			this.dayOfWeekDefinition = baseDefinition.dayOfWeekDefinition;
		}
		
		public SimpleCronDefinitionBuilder setMinuteDefinition(CronMinute minuteDefinition) {
			Preconditions.checkArgument(minuteDefinition != null);
			this.minuteDefinition = minuteDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setHourDefinition(CronHour hourDefinition) {
			Preconditions.checkArgument(hourDefinition != null);
			this.hourDefinition = hourDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setDayDefinition(CronDay dayDefinition) {
			Preconditions.checkArgument(dayDefinition != null);
			this.dayDefinition = dayDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setMonthDefinition(CronMonth monthDefinition) {
			Preconditions.checkArgument(monthDefinition != null);
			this.monthDefinition = monthDefinition;
			return this;
		}

		public SimpleCronDefinitionBuilder setDayOfWeekDefinition(CronDayOfWeek dayOfWeekDefinition) {
			Preconditions.checkArgument(dayOfWeekDefinition != null);
			this.dayOfWeekDefinition = dayOfWeekDefinition;
			return this;
		}
		
		public SimpleCronDefinition build() {
			Preconditions.checkArgument(minuteDefinition != null);
			
			if (this.hourDefinition == null) {
				this.hourDefinition = CronHour.EVERY_HOUR;
			}
			if (this.dayDefinition == null) {
				this.dayDefinition = CronDay.EVERY_DAY;
			}
			if (this.monthDefinition == null) {
				this.monthDefinition = CronMonth.EVERY_MONTH;
			}
			if (this.dayOfWeekDefinition == null) {
				this.dayOfWeekDefinition = CronDayOfWeek.EVERY_DAY_OF_THE_WEEK;
			}
			return new SimpleCronDefinition(minuteDefinition, hourDefinition, dayDefinition, monthDefinition, dayOfWeekDefinition);
		}
		
	}

}
