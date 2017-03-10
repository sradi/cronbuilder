package de.cron.string;

import java.time.Month;

public class MonthStringRepresentation implements DateTimeStringRepresentation {
	
	private Month month;
	
	@Override
	public int getValue() {
		return month.getValue();
	}

}
