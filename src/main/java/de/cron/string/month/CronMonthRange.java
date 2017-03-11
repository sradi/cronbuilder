package de.cron.string.month;

import java.time.Month;

public class CronMonthRange implements CronMonth {

	private Month fromMonth;
	private Month untilMonth;

	public CronMonthRange(Month fromMonth, Month untilMonth) {
		this.fromMonth = fromMonth;
		this.untilMonth = untilMonth;
	}

	@Override
	public String toString() {
		return fromMonth + "-" + untilMonth;
	}

}
