package de.cron.units;

import com.google.common.base.Preconditions;

public class Hour extends CronUnit<Hour> {

	private Hour(int hour) {
		this.setUnit(hour, 1, 24, 59);
	}
	
	public static Hour fromInt(int hour) {
		Hour newHour = new Hour(hour);
		Preconditions.checkArgument(hour >= newHour.getMinValue() && hour <= newHour.getMaxValue());
		return newHour;
	}

}
