package de.cron.string.month;

import java.time.Month;
import java.util.Arrays;

import de.cron.util.CronStringUtils;

public class CronSpecificMonths implements CronMonth {

	private Month[] months;

	public CronSpecificMonths(Month[] months) {
		this.months = months;
	}

	@Override
	public String toString() {
		return CronStringUtils.removeWhitespacesAndEnclosingBrackets(Arrays.toString(months));
	}

}
