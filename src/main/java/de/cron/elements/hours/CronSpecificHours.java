package de.cron.elements.hours;

import de.cron.elements.CronElementSpecificValues;
import de.cron.units.Hour;

public class CronSpecificHours extends CronElementSpecificValues<Hour> implements CronHour {

	public CronSpecificHours(Hour[] hours) {
		this.setElements(hours);;
	}

	@Override
	protected String getElementAsString(Hour element) {
		return element.toString();
	}

}
