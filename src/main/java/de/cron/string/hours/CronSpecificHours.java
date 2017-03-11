package de.cron.string.hours;

import java.util.Arrays;

public class CronSpecificHours implements CronHour {

	private int[] hours;

	public CronSpecificHours(int[] hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return Arrays.toString(hours);
	}

}
