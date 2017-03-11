package de.cron.string.minutes;

import java.util.Arrays;

import de.cron.util.CronStringUtils;

public class CronSpecificMinutes implements CronMinute {

	private int[] minutes;

	public CronSpecificMinutes(int[] minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return CronStringUtils.removeWhitespacesAndEnclosingBrackets(Arrays.toString(minutes));
	}
	
	
	
}
