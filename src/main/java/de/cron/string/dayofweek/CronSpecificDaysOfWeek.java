package de.cron.string.dayofweek;

import java.time.DayOfWeek;
import java.util.Arrays;

import de.cron.util.CronStringUtils;

public class CronSpecificDaysOfWeek implements CronDayOfWeek {

	private DayOfWeek[] daysOfWeek;

	public CronSpecificDaysOfWeek(DayOfWeek[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	@Override
	public String toString() {
		return CronStringUtils.removeWhitespacesAndEnclosingBrackets(Arrays.toString(daysOfWeek));
	}

}
