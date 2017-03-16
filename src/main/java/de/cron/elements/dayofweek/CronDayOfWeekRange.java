package de.cron.elements.dayofweek;

import java.time.DayOfWeek;

import com.google.common.base.Preconditions;

import de.cron.elements.CronElementRange;

public class CronDayOfWeekRange extends CronElementRange<DayOfWeek> implements CronDayOfWeek {

	public CronDayOfWeekRange(DayOfWeek fromDayOfWeek, DayOfWeek untilDayOfWeek) {
		Preconditions.checkArgument(!fromDayOfWeek.equals(untilDayOfWeek));
		this.setRange(fromDayOfWeek, untilDayOfWeek);
	}

	@Override
	protected String getElementAsString(DayOfWeek element) {
		return Integer.toString(element.getValue());
	}

}
