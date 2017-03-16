package de.cron.elements.day;

import de.cron.elements.CronElementSpecificValues;
import de.cron.units.Day;

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
