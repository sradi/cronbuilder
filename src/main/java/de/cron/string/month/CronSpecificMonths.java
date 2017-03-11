package de.cron.string.month;

import java.time.Month;
import java.util.Arrays;

public class CronSpecificMonths implements CronMonth {

	private Month[] months;

	public CronSpecificMonths(Month[] months) {
		this.months = months;
	}

	@Override
	public String toString() {
		return Arrays.toString(months);
	}

}
