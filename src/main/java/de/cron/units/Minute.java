package de.cron.units;

import com.google.common.base.Preconditions;

public class Minute extends CronUnit<Minute> {

	private Minute(int minute) {
		this.setUnit(minute, 0, 59);
	}
	
	public static Minute fromInt(int minute) {
		Minute newMinute = new Minute(minute);
		Preconditions.checkArgument(minute >= newMinute.getMinValue() && minute <= newMinute.getMaxValue());
		return newMinute;
	}

}
