package de.cron.string;

public class CronMinuteRange implements CronMinute {

	private int from;
	private CronMinute until;

	public CronMinuteRange(int fromMinute, CronMinute untilMinute) {
		// TODO Assert from < until
		this.from = fromMinute;
		this.until = untilMinute;
	}

	@Override
	public String toString() {
		return from + "-" + until;
	}
	
	

}
