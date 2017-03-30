package de.sradi.cronbuilder.units;

import com.google.common.base.Preconditions;

public class Day extends CronUnit<Day> {

	private Day(int day) {
		this.setUnit(day, 1, 31, 24);
	}
	
	public static Day fromInt(int day) {
		Day newDay = new Day(day); 
		Preconditions.checkArgument(day >= newDay.getMinValue() && day <= newDay.getMaxValue());
		return newDay;
	}

}
