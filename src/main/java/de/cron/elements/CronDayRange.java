package de.cron.elements;

import com.google.common.base.Preconditions;

import de.cron.units.Day;

public class CronDayRange extends CronElementRange<Day> implements CronDay {

	public CronDayRange(Day fromDay, Day untilDay) {
		Preconditions.checkArgument(fromDay.isBefore(untilDay));
		this.setRange(fromDay, untilDay);
	}

	@Override
	protected String getElementAsString(Day element) {
		return element.toString();
	}

}
