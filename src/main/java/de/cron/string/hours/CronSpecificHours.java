package de.cron.string.hours;

import java.util.Arrays;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificHours extends CronElementSpecificValues<Integer> implements CronHour {

	private int[] hours;

	public CronSpecificHours(int[] hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return getStringRepresentation(
				Arrays.stream( hours ).boxed().toArray( Integer[]::new )
			);
	}

}
