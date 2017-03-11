package de.cron.string.hours;

public class CronHourRange implements CronHour {

	private int from;
	private int until;

	public CronHourRange(int fromHour, int untilHour) {
		// TODO assert from < until
		this.from = fromHour;
		this.until = untilHour;
	}

	@Override
	public String toString() {
		return from + "-" + until;
	}

}
