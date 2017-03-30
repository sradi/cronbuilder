package de.sradi.cronbuilder;

import java.time.DayOfWeek;

interface ComplexCronDayOfWeekPartOne extends ComplexCronLastPart {
	
	ComplexCronLastPart everyDayOfWeek();
	
	ComplexCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek);
	
	ComplexCronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek);

}
