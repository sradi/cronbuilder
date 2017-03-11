package de.cron.string.month;

import java.time.Month;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificMonths extends CronElementSpecificValues<Month> implements CronMonth {

	private Month[] months;

	public CronSpecificMonths(Month[] months) {
		this.months = months;
	}

	@Override
	public String toString() {
		return getStringRepresentation(months);
	}

}
