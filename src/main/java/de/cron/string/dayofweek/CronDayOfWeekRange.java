package de.cron.string.dayofweek;

import java.time.DayOfWeek;

public class CronDayOfWeekRange implements CronDayOfWeek {

	private DayOfWeek fromDayOfWeek;
	private CronDayOfWeek untilDayOfWeek;

	public CronDayOfWeekRange(DayOfWeek fromDayOfWeek, CronDayOfWeek untilDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
		this.untilDayOfWeek = untilDayOfWeek;
	}

	@Override
	public String toString() {
		return fromDayOfWeek + "-" + untilDayOfWeek;
	}

}
