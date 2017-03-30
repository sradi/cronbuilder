package de.sradi.cronbuilder;

import java.time.LocalDate;

public interface CronMinuteBasedPeriodPartOne {
	
	CronPeriodDayOfWeekPartOne until(int minute, int hour, LocalDate until);

}
