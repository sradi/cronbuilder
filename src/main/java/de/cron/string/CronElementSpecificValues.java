package de.cron.string;

import java.util.Arrays;

public abstract class CronElementSpecificValues<T> {
	
	protected String getStringRepresentation(T[] elements) {
		return removeWhitespacesAndEnclosingBrackets(Arrays.toString(elements));
	}
	
	private String removeWhitespacesAndEnclosingBrackets(String arraysToStringValue) {
		return arraysToStringValue.replaceAll("[\\[\\] ]", "");
	}

}
