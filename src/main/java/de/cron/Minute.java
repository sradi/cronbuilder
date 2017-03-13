package de.cron;

import com.google.common.base.Preconditions;

public class Minute {

	private int minute;
	
	private Minute(int minute) {
		this.minute = minute;
	}
	
	public static Minute fromInt(int minute) {
		Preconditions.checkArgument(minute >= 0 && minute <= 59);
		return new Minute(minute);
	}

	@Override
	public String toString() {
		return Integer.toString(minute);
	}
	
}
