package de.cron.elements;

import de.cron.units.Hour;

public class CronSpecificHours extends CronElementSpecificValues<Hour> implements CronHour {

	public CronSpecificHours(Hour[] hours) {
		this.setElements(hours);
	}

	public CronSpecificHours(Hour hour) {
		this.setElements(new Hour[] { hour });
	}

	@Override
	protected String getElementAsString(Hour element) {
		return element.toString();
	}

}
