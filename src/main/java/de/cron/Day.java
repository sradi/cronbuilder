package de.cron;

import com.google.common.base.Preconditions;

public class Day {

	private int day;
	
	private Day(int day) {
		this.day = day;
	}
	
	public static Day fromInt(int hour) {
		Preconditions.checkArgument(hour >= 1 && hour <= 31);
		return new Day(hour);
	}

	@Override
	public String toString() {
		return Integer.toString(day);
	}

}
