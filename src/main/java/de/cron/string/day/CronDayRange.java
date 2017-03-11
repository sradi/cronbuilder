package de.cron.string.day;

public class CronDayRange implements CronDay {

	private int fromDay;
	private int untilDay;

	public CronDayRange(int fromDay2, int untilDay) {
		this.fromDay = fromDay2;
		this.untilDay = untilDay;
	}

	@Override
	public String toString() {
		return fromDay + "-" + untilDay;
	}

}
