package de.cron.string.dayofweek;

import java.time.DayOfWeek;

import com.google.common.base.Preconditions;

public class CronDayOfWeekRange implements CronDayOfWeek {

	private DayOfWeek fromDayOfWeek;
	private DayOfWeek untilDayOfWeek;

	public CronDayOfWeekRange(DayOfWeek fromDayOfWeek, DayOfWeek untilDayOfWeek) {
		Preconditions.checkArgument(!fromDayOfWeek.equals(untilDayOfWeek));
		this.fromDayOfWeek = fromDayOfWeek;
		this.untilDayOfWeek = untilDayOfWeek;
	}

	@Override
	public String toString() {
		return fromDayOfWeek.getValue() + "-" + untilDayOfWeek.getValue();
	}

}
