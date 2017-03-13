package de.cron.string.hours;

import de.cron.Hour;

public class CronHourRange implements CronHour {

	private Hour from;
	private Hour until;

	public CronHourRange(Hour fromHour, Hour untilHour) {
		// TODO assert from < until
		this.from = fromHour;
		this.until = untilHour;
	}

	@Override
	public String toString() {
		return from + "-" + until;
	}

}
