package de.cron.string.minutes;

import de.cron.Minute;

public class CronMinuteRange implements CronMinute {

	private Minute from;
	private Minute until;

	public CronMinuteRange(Minute fromMinute, Minute minute) {
		this.from = fromMinute;
		this.until = minute;
	}

	@Override
	public String toString() {
		return from + "-" + until;
	}
	
	

}
