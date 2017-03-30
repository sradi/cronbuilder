package de.sradi.cronbuilder;

import java.time.LocalDate;

interface CronDatePeriodPart {
	
	ComplexCronDayOfWeekPartOne until(LocalDate until);

}
