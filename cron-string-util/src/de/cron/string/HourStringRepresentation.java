package de.cron.string;

public class HourStringRepresentation implements DateTimeStringRepresentation {
	
	private int hour;

	@Override
	public int getValue() {
		return hour;
	}

}
