package de.cron.elements;

import java.time.Month;

import com.google.common.base.Preconditions;

public class CronMonthRange extends CronElementRange<Month> implements CronMonth {

	public CronMonthRange(Month fromMonth, Month untilMonth) {
		Preconditions.checkArgument(!fromMonth.equals(untilMonth));
		this.setRange(fromMonth, untilMonth);
	}

	@Override
	protected String getElementAsString(Month element) {
		return Integer.toString(element.getValue());
	}

}
