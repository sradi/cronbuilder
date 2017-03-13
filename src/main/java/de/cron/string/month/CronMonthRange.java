package de.cron.string.month;

import java.time.Month;

import com.google.common.base.Preconditions;

public class CronMonthRange implements CronMonth {

	private Month fromMonth;
	private Month untilMonth;

	public CronMonthRange(Month fromMonth, Month untilMonth) {
		Preconditions.checkArgument(!fromMonth.equals(untilMonth));
		this.fromMonth = fromMonth;
		this.untilMonth = untilMonth;
	}

	@Override
	public String toString() {
		return fromMonth.getValue() + "-" + untilMonth.getValue();
	}

}
