package de.cron.string.hours;

import de.cron.Hour;
import de.cron.string.CronElementSpecificValues;

public class CronSpecificHours extends CronElementSpecificValues<Hour> implements CronHour {

	public CronSpecificHours(Hour[] hours) {
		this.setElements(hours);;
	}

	@Override
	protected String getElementAsString(Hour element) {
		return element.toString();
	}

}
