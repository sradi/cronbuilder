package de.cron.string.hours;

import de.cron.Hour;
import de.cron.string.CronElementSpecificValues;

public class CronSpecificHours extends CronElementSpecificValues<Hour> implements CronHour {

	private Hour[] hours;

	public CronSpecificHours(Hour[] hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return getStringRepresentation(hours);
	}

	@Override
	protected String getElementAsString(Hour element) {
		return element.toString();
	}

}
