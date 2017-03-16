package de.cron.string.day;

import de.cron.Day;
import de.cron.string.CronElementSpecificValues;

public class CronSpecificDays extends CronElementSpecificValues<Day> implements CronDay {

	public CronSpecificDays(Day[] days) {
		this.setElements(days);;
	}

	public CronSpecificDays(Day day) {
		this.setElements(new Day[] { day });
	}

	@Override
	protected String getElementAsString(Day element) {
		return element.toString();
	}
	
}
