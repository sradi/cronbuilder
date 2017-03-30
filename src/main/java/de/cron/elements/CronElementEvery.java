package de.cron.elements;

public class CronElementEvery implements CronMinute, CronHour, CronDay, CronMonth, CronDayOfWeek {
	
	public static final CronElementEvery INSTANCE = new CronElementEvery();
	
	private CronElementEvery() {}

	@Override
	public String toString() {
		return "*";
	}

}
