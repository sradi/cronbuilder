package de.cron.string.day;

import java.util.Arrays;

public class CronSpecificDays implements CronDay {

	private CronDay[] days;

	public CronSpecificDays(CronDay[] days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return Arrays.toString(days);
	}
	
}
