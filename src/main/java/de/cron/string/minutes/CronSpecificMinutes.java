package de.cron.string.minutes;

import de.cron.Minute;
import de.cron.string.CronElementSpecificValues;

public class CronSpecificMinutes extends CronElementSpecificValues<Minute> implements CronMinute {

	public CronSpecificMinutes(Minute[] minutes) {
		this.setElements(minutes);;
	}

	@Override
	protected String getElementAsString(Minute element) {
		return element.toString();
	}
	
	
	
}
