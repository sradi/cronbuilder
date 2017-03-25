package de.cron;

import java.time.LocalDate;

import de.cron.ComplexCronDefinition.ComplexCronDefinitionBuilder;
import de.cron.SimpleCronDefinition.SimpleCronDefinitionBuilder;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.units.Hour;

public interface ComplexCronDefinition {

	public static class ComplexCronDefinitionBuilder {

		public ComplexCronDefinitionBuilder(ComplexCronDefinition currentComplexCronDefinition) {
			// TODO Auto-generated constructor stub
		}

		public ComplexCronDefinitionBuilder() {
			// TODO Auto-generated constructor stub
		}

		public ComplexCronDefinitionBuilder setDayOfWeekDefinition(CronDayOfWeek everyDayOfTheWeek) {
			// TODO Auto-generated method stub
			return this;
		}

		public ComplexCronDefinition build() {
			// TODO Auto-generated method stub
			return null;
		}

		public ComplexCronDefinitionBuilder setMinuteDefinition(CronMinute minuteDefinition) {
			// TODO Auto-generated method stub
			return this;
		}

		public ComplexCronDefinitionBuilder setFromHour(Hour fromHour) {
			// TODO Auto-generated method stub
			return this;
		}

		public ComplexCronDefinitionBuilder setUntilHour(Hour fromInt) {
			// TODO Auto-generated method stub
			return this;
		}

		public ComplexCronDefinitionBuilder setFromDate(LocalDate fromDate) {
			// TODO Auto-generated method stub
			return this;
		}

		public ComplexCronDefinitionBuilder setUntilDate(LocalDate until) {
			// TODO Auto-generated method stub
			return this;
		}

		public ComplexCronDefinitionBuilder setHourDefinition(CronHour hourDefinition) {
			// TODO Auto-generated method stub
			return this;
		}

	}

}
