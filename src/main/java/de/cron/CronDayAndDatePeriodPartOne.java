package de.cron;

import java.time.LocalDate;

public interface CronDayAndDatePeriodPartOne {
	
	ComplexCronLastPart until(int hour, LocalDate until);

}
