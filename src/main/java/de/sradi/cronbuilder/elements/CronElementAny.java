package de.sradi.cronbuilder.elements;

public class CronElementAny implements CronMinute, CronHour, CronDay, CronMonth, CronDayOfWeek {
	
	public static final CronElementAny INSTANCE = new CronElementAny();
	
	private CronElementAny() {}

	@Override
	public String toString() {
		return "H";
	}
}
