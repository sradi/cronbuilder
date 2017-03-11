package de.cron.string.day;

public class CronDayRange implements CronDay {

	private CronDay fromDay;
	private CronDay untilDay;

	public CronDayRange(CronDay fromDay, CronDay untilDay) {
		this.fromDay = fromDay;
		this.untilDay = untilDay;
	}

	@Override
	public String toString() {
		return fromDay + "-" + untilDay;
	}

}
