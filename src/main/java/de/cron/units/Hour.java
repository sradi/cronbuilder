package de.cron;

import com.google.common.base.Preconditions;

public class Hour extends CronUnit<Hour> {

	private Hour(int hour) {
		this.setUnit(hour);
	}
	
	public static Hour fromInt(int hour) {
		Preconditions.checkArgument(hour >= 0 && hour <= 24);
		return new Hour(hour);
	}

}
