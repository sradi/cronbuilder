package de.cron;

import com.google.common.base.Preconditions;

public class Hour {

	private int hour;
	
	private Hour(int hour) {
		this.hour = hour;
	}
	
	public static Hour fromInt(int hour) {
		Preconditions.checkArgument(hour >= 0 && hour <= 24);
		return new Hour(hour);
	}

	@Override
	public String toString() {
		return Integer.toString(hour);
	}

}
