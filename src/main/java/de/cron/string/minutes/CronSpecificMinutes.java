package de.cron.string.minutes;

import de.cron.Minute;
import de.cron.string.CronElementSpecificValues;

public class CronSpecificMinutes extends CronElementSpecificValues<Minute> implements CronMinute {

	private Minute[] minutes;

	public CronSpecificMinutes(Minute[] minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return getStringRepresentation(minutes);
	}

	@Override
	protected String getElementAsString(Minute element) {
		return element.toString();
	}
	
	
	
}
