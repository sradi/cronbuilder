package de.cron.string;

public class MinuteStringRepresentation implements DateTimeStringRepresentation {
	
	int minute;

	@Override
	public int getValue() {
		return minute;
	}

}
