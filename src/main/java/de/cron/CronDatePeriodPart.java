package de.cron;

import java.time.LocalDate;

interface CronDatePeriodPart {
	
	ComplexCronDayOfWeekPartOne until(LocalDate until);

}
