package de.cron;

import com.google.common.base.Preconditions;

public class Minute extends CronUnit<Minute> {

	private Minute(int minute) {
		this.setUnit(minute);
	}
	
	public static Minute fromInt(int minute) {
		Preconditions.checkArgument(minute >= 0 && minute <= 59);
		return new Minute(minute);
	}

}
