package de.cron;

import java.time.LocalDate;

interface CronDatePeriodPart {
	
	ComplexCronDefinition until(LocalDate until);

}
