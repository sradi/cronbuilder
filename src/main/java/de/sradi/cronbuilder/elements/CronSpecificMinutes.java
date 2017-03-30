package de.sradi.cronbuilder.elements;

import de.sradi.cronbuilder.units.Minute;

public class CronSpecificMinutes extends CronElementSpecificValues<Minute> implements CronMinute {

	public CronSpecificMinutes(Minute... minutes) {
		this.setElements(minutes);;
	}

	@Override
	protected String getElementAsString(Minute element) {
		return element.toString();
	}
	
	
	
}
