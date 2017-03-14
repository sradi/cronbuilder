package de.cron.string.dayofweek;

import java.time.DayOfWeek;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificDaysOfWeek extends CronElementSpecificValues<DayOfWeek> implements CronDayOfWeek {

	public CronSpecificDaysOfWeek(DayOfWeek[] daysOfWeek) {
		this.setElements(daysOfWeek);
	}

	@Override
	protected String getElementAsString(DayOfWeek element) {
		return Integer.valueOf(element.getValue()).toString();
	}

}
