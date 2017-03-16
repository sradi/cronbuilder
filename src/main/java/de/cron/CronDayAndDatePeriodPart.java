package de.cron;

import java.time.LocalDate;

public interface CronDayAndDatePeriodPart {
	
	ComplexCronDefinition until(int hour, LocalDate until);

}
