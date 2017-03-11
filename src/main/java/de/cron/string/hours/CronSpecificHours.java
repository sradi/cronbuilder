package de.cron.string.hours;

import java.util.Arrays;

import de.cron.util.CronStringUtils;

public class CronSpecificHours implements CronHour {

	private int[] hours;

	public CronSpecificHours(int[] hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return CronStringUtils.removeWhitespacesAndEnclosingBrackets(Arrays.toString(hours));
	}

}
