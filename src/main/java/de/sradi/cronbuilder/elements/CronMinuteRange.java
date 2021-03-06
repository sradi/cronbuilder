package de.sradi.cronbuilder.elements;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.units.Minute;

public class CronMinuteRange extends CronElementRange<Minute> implements CronMinute {

	public CronMinuteRange(Minute fromMinute, Minute untilMinute) {
		Preconditions.checkArgument(fromMinute.isBefore(untilMinute));
		this.setRange(fromMinute, untilMinute);
	}

	@Override
	protected String getElementAsString(Minute element) {
		return element.toString();
	}
	
	

}
