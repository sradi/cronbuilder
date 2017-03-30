package de.sradi.cronbuilder;

import java.time.LocalDate;

public interface CronHourBasedPeriodPartOne {
	
	CronPeriodLastPart until(int hour, LocalDate until);

}
