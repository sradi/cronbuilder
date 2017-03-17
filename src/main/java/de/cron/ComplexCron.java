package de.cron;

import java.time.DayOfWeek;

public class ComplexCron implements ComplexCronDayOfWeekPartOne, ComplexCronDayOfWeekPartTwo {

	private ComplexCronDefinition currentComplexCronDefinition;

	public ComplexCron(ComplexCronDefinition complexCronDefinition) {
		this.currentComplexCronDefinition = complexCronDefinition;
	}
	
	@Override
	public ComplexCronLastPart everyDayOfWeek() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ComplexCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public ComplexCronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek dayOfWeek) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ComplexCronLastPart untilDayOfWeek(DayOfWeek dayOfWeek) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ComplexCronDefinition get() {
		return currentComplexCronDefinition;
	}

}
