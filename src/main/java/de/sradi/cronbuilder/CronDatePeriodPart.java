package de.sradi.cronbuilder;

import java.time.LocalDate;

interface CronDatePeriodPart {
	
	CronPeriodDayOfWeekPartOne until(LocalDate until);

}
