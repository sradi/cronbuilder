package de.cron.string;

import java.util.Arrays;

public class CronSpecificMinutes implements CronMinute {

	private int[] minutes;

	public CronSpecificMinutes(int[] minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return Arrays.toString(minutes);
	}
	
	
	
}
