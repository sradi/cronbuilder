package de.sradi.cronbuilder;

import java.time.LocalDate;

public interface CronDayAndDatePeriodPartOne {
	
	ComplexCronLastPart until(int hour, LocalDate until);

}
