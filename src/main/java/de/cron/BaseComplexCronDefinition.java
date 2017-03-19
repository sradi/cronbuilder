package de.cron;

import java.time.LocalDate;

import com.google.common.base.Preconditions;

import de.cron.DayLevelComplexCronDefinition.ComplexCronDefinitionBuilder;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.units.Hour;

public class BaseComplexCronDefinition {
	
	protected ComplexCronDefinition cronDefinition;

	public BaseComplexCronDefinition(ComplexCronDefinition cronDefinition) {
		this.cronDefinition = cronDefinition;
	}
	
	public static class ComplexCronDefinitionBuilder {
		private CronMinute minuteDefinition;
		private CronHour hourDefinition;
		private Hour fromHour;
		private Hour untilHour;

		private LocalDate fromDate;
		private LocalDate untilDate;
		private CronDayOfWeek dayOfWeekDefinition;
		
		public ComplexCronDefinitionBuilder() {
		}
		
		public ComplexCronDefinitionBuilder(DayLevelComplexCronDefinition baseDefinition) {
			Preconditions.checkArgument(baseDefinition != null);
			
			this.minuteDefinition = baseDefinition.baseCronDefinition.getMinuteDefinition();
			this.hourDefinition = baseDefinition.baseCronDefinition.getHourDefinition();
			this.fromHour = baseDefinition.fromHour;
			this.untilHour = baseDefinition.untilHour;
			this.fromDate = baseDefinition.fromDate;
			this.untilDate = baseDefinition.untilDate;
			this.dayOfWeekDefinition = baseDefinition.baseCronDefinition.getDayOfWeekDefinition();
		}
		
		public ComplexCronDefinitionBuilder setMinuteDefinition(CronMinute minuteDefinition) {
			Preconditions.checkArgument(minuteDefinition != null);
			this.minuteDefinition = minuteDefinition;
			return this;
		}
		
		public ComplexCronDefinitionBuilder setHourDefinition(CronHour hourDefinition) {
			Preconditions.checkArgument(hourDefinition != null);
			this.hourDefinition = hourDefinition;
			return this;
		}

		public ComplexCronDefinitionBuilder setFromDate(LocalDate fromDate) {
			Preconditions.checkArgument(fromDate != null);
			this.fromDate = fromDate;
			return this;
		}
		
		public ComplexCronDefinitionBuilder setUntilDate(LocalDate untilDate) {
			Preconditions.checkArgument(untilDate != null);
			this.untilDate = untilDate;
			return this;
		}
		
		public ComplexCronDefinitionBuilder setFromHour(Hour fromHour) {
			Preconditions.checkArgument(fromHour != null);
			this.fromHour = fromHour;
			return this;
		}
		
		public ComplexCronDefinitionBuilder setUntilHour(Hour untilHour) {
			Preconditions.checkArgument(untilHour != null);
			this.untilHour = untilHour;
			return this;
		}

		public ComplexCronDefinitionBuilder setDayOfWeekDefinition(CronDayOfWeek dayOfWeekDefinition) {
			Preconditions.checkArgument(dayOfWeekDefinition != null);
			this.dayOfWeekDefinition = dayOfWeekDefinition;
			return this;
		}
		
		public DayLevelComplexCronDefinition build() {
			Preconditions.checkState(fromDate != null);
			Preconditions.checkState(untilDate != null);
			ensureEitherHourBasedPeriodOrHourDefinition();
			if (this.dayOfWeekDefinition == null) {
				this.dayOfWeekDefinition = CronDayOfWeek.EVERY_DAY_OF_THE_WEEK;
			}
			
			if ((fromHour == null) || (untilHour == null)) {
				return new DayLevelComplexCronDefinition(minuteDefinition, hourDefinition, fromDate, untilDate, dayOfWeekDefinition);
			} else {
				return new DayLevelComplexCronDefinition(minuteDefinition, fromHour, untilHour, fromDate, untilDate, dayOfWeekDefinition);
			}
		}

		private void ensureEitherHourBasedPeriodOrHourDefinition() {
			if (hourDefinition != null) {
				Preconditions.checkState((fromHour == null) && (untilHour == null));
			} else {
				Preconditions.checkState((fromHour != null) && (untilHour != null));
			}
		}
		
	}

}
