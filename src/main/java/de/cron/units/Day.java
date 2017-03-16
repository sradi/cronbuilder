package de.cron.units;

import com.google.common.base.Preconditions;

public class Day extends CronUnit<Day> {

	private Day(int day) {
		this.setUnit(day);
	}
	
	public static Day fromInt(int day) {
		Preconditions.checkArgument(day >= 1 && day <= 31);
		return new Day(day);
	}

}
