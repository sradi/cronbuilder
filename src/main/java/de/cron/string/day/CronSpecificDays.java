package de.cron.string.day;

import java.util.Arrays;

public class CronSpecificDays implements CronDay {

	private int[] days;

	public CronSpecificDays(int[] days2) {
		this.days = days2;
	}

	@Override
	public String toString() {
		return Arrays.toString(days);
	}
	
}
