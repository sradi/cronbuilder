package de.cron.elements.minutes;

import de.cron.Minute;
import de.cron.elements.CronElementSpecificValues;

public class CronSpecificMinutes extends CronElementSpecificValues<Minute> implements CronMinute {

	public CronSpecificMinutes(Minute[] minutes) {
		this.setElements(minutes);;
	}

	@Override
	protected String getElementAsString(Minute element) {
		return element.toString();
	}
	
	
	
}
