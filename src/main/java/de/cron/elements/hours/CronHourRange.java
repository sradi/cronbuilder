package de.cron.elements.hours;

import com.google.common.base.Preconditions;

import de.cron.Hour;
import de.cron.elements.CronElementRange;

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
