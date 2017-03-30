package de.sradi.cronbuilder;

import java.time.LocalDate;

public interface CronHourBasedPeriodPartOne {
	
	CronPeriodDayOfWeekPartOne until(int hour, LocalDate until);

}
