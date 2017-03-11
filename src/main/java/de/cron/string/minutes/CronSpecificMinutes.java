package de.cron.string.minutes;

import java.util.Arrays;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificMinutes extends CronElementSpecificValues<Integer> implements CronMinute {

	private int[] minutes;

	public CronSpecificMinutes(int[] minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return getStringRepresentation(
				Arrays.stream( minutes ).boxed().toArray( Integer[]::new )
			);
	}
	
	
	
}
