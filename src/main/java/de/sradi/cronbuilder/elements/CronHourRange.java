package de.sradi.cronbuilder.elements;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.units.Hour;

public class CronHourRange extends CronElementRange<Hour> implements CronHour {

	public CronHourRange(Hour fromHour, Hour untilHour) {
		Preconditions.checkArgument(fromHour.isBefore(untilHour));
		this.setRange(fromHour, untilHour);
	}

	@Override
	protected String getElementAsString(Hour element) {
		return element.toString();
	}

}
