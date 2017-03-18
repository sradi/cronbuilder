package de.cron;

import java.time.DayOfWeek;

import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronDayOfWeekRange;
import de.cron.elements.CronSpecificDaysOfWeek;

public class ComplexCron implements ComplexCronDayOfWeekPartOne, ComplexCronDayOfWeekPartTwo {

	private DayLevelComplexCronDefinition currentComplexCronDefinition;
	private DayOfWeek fromDayOfWeek;

	public ComplexCron(DayLevelComplexCronDefinition complexCronDefinition) {
		this.currentComplexCronDefinition = complexCronDefinition;
	}
	
	@Override
	public ComplexCronLastPart everyDayOfWeek() {
		this.currentComplexCronDefinition = new DayLevelComplexCronDefinition.ComplexCronDefinitionBuilder(currentComplexCronDefinition)
				.setDayOfWeekDefinition(CronDayOfWeek.EVERY_DAY_OF_THE_WEEK)
				.build();
		return this;
	}
	
	@Override
	public ComplexCronLastPart onTheseDaysOfTheWeek(DayOfWeek... daysOfWeek) {
		this.currentComplexCronDefinition = new DayLevelComplexCronDefinition.ComplexCronDefinitionBuilder(currentComplexCronDefinition)
				.setDayOfWeekDefinition(new CronSpecificDaysOfWeek(daysOfWeek))
				.build();
		return this;
	}
	
	@Override
	public ComplexCronDayOfWeekPartTwo fromDayOfWeek(DayOfWeek fromDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
		return this;
	}

	@Override
	public ComplexCronLastPart untilDayOfWeek(DayOfWeek untilDayOfWeek) {
		this.currentComplexCronDefinition = new DayLevelComplexCronDefinition.ComplexCronDefinitionBuilder(currentComplexCronDefinition)
				.setDayOfWeekDefinition(new CronDayOfWeekRange(fromDayOfWeek, untilDayOfWeek))
				.build();
		return this;
	}

	@Override
	public DayLevelComplexCronDefinition get() {
		return currentComplexCronDefinition;
	}

}
