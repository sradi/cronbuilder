package de.cron.string.day;

import java.util.Arrays;

import de.cron.util.CronStringUtils;

public class CronSpecificDays implements CronDay {

	private int[] days;

	public CronSpecificDays(int[] days2) {
		this.days = days2;
	}

	@Override
	public String toString() {
		return CronStringUtils.removeWhitespacesAndEnclosingBrackets(Arrays.toString(days));
	}
	
}
