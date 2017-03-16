package de.cron;

import java.time.LocalDate;

public interface CronDatePeriodPart {
	
	ComplexCronDefinition until(LocalDate until);

}
