package de.cron.string.day;

import de.cron.Day;

public class CronDayRange implements CronDay {

	private Day fromDay;
	private Day untilDay;

	public CronDayRange(Day fromDay, Day untilDay) {
		this.fromDay = fromDay;
		this.untilDay = untilDay;
	}

	@Override
	public String toString() {
		return fromDay + "-" + untilDay;
	}

}
