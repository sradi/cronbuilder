package de.cron.string.hours;

import com.google.common.base.Preconditions;

import de.cron.Hour;

public class CronHourRange implements CronHour {

	private Hour from;
	private Hour until;

	public CronHourRange(Hour fromHour, Hour untilHour) {
		Preconditions.checkArgument(fromHour.isBefore(untilHour));
		this.from = fromHour;
		this.until = untilHour;
	}

	@Override
	public String toString() {
		return from + "-" + until;
	}

}
