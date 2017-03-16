package de.cron.elements;

import java.time.DayOfWeek;

public class CronSpecificDaysOfWeek extends CronElementSpecificValues<DayOfWeek> implements CronDayOfWeek {

	public CronSpecificDaysOfWeek(DayOfWeek[] daysOfWeek) {
		this.setElements(daysOfWeek);
	}

	@Override
	protected String getElementAsString(DayOfWeek element) {
		return Integer.valueOf(element.getValue()).toString();
	}

}
