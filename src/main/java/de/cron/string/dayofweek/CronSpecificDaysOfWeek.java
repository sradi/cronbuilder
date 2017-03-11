package de.cron.string.dayofweek;

import java.time.DayOfWeek;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificDaysOfWeek extends CronElementSpecificValues<DayOfWeek> implements CronDayOfWeek {

	private DayOfWeek[] daysOfWeek;

	public CronSpecificDaysOfWeek(DayOfWeek[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	@Override
	public String toString() {
		return getStringRepresentation(daysOfWeek);
	}

}
