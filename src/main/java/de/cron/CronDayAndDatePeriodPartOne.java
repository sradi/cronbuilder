package de.cron;

import java.time.LocalDate;

public interface CronDayAndDatePeriodPartOne {
	
	ComplexCronDefinition until(int hour, LocalDate until);

}
