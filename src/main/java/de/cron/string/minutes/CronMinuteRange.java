package de.cron.string.minutes;

public class CronMinuteRange implements CronMinute {

	private int from;
	private int until;

	public CronMinuteRange(int fromMinute, int minute) {
		// TODO Assert from < until
		this.from = fromMinute;
		this.until = minute;
	}

	@Override
	public String toString() {
		return from + "-" + until;
	}
	
	

}
