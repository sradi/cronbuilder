package de.cron.elements;

import de.cron.units.Day;

public class CronSpecificDays extends CronElementSpecificValues<Day> implements CronDay {

	public CronSpecificDays(Day... days) {
		this.setElements(days);;
	}

	@Override
	protected String getElementAsString(Day element) {
		return element.toString();
	}
	
}
