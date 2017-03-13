package de.cron.string.dayofweek;

import java.time.DayOfWeek;

public class CronDayOfWeekRange implements CronDayOfWeek {

	private DayOfWeek fromDayOfWeek;
	private DayOfWeek untilDayOfWeek;

	public CronDayOfWeekRange(DayOfWeek fromDayOfWeek, DayOfWeek untilDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
		this.untilDayOfWeek = untilDayOfWeek;
	}

	@Override
	public String toString() {
		return fromDayOfWeek + "-" + untilDayOfWeek;
	}

}
