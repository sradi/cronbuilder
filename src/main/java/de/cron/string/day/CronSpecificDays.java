package de.cron.string.day;

import de.cron.Day;
import de.cron.string.CronElementSpecificValues;

public class CronSpecificDays extends CronElementSpecificValues<Day> implements CronDay {

	private Day[] days;

	public CronSpecificDays(Day[] days2) {
		this.days = days2;
	}

	@Override
	public String toString() {
		return getStringRepresentation(days);
	}

	@Override
	protected String getElementAsString(Day element) {
		return element.toString();
	}
	
}
