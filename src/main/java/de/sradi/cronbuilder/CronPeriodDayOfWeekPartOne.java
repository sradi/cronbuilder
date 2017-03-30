package de.sradi.cronbuilder;

import java.time.DayOfWeek;

interface CronPeriodDayOfWeekPartOne extends CronPeriodLastPart {
	
	CronPeriodLastPart everyDayOfWeek();
	
	CronPeriodLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek);
	
	CronPeriodDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek);

}
