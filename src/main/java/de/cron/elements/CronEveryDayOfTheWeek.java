package de.cron.elements;

public class CronEveryDayOfTheWeek implements CronDayOfWeek {

	@Override
	public String toString() {
		return "*";
	}

}
