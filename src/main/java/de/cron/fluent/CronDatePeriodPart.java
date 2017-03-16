package de.cron.fluent;

import java.time.LocalDate;

import de.cron.ComplexCronDefinition;

public interface CronDatePeriodPart {
	
	ComplexCronDefinition until(LocalDate until);

}
