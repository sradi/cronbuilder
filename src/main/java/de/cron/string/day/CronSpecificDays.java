package de.cron.string.day;

import java.util.Arrays;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificDays extends CronElementSpecificValues<Integer> implements CronDay {

	private int[] days;

	public CronSpecificDays(int[] days2) {
		this.days = days2;
	}

	@Override
	public String toString() {
		return getStringRepresentation(
				Arrays.stream( days ).boxed().toArray( Integer[]::new )
			);
	}
	
}
