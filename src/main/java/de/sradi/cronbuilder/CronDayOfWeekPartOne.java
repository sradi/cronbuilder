package de.sradi.cronbuilder;

import java.time.DayOfWeek;

interface CronDayOfWeekPartOne extends SimpleCronLastPart {
	
	SimpleCronLastPart everyDayOfWeek();
	
	SimpleCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek);
	
	CronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek);

}
