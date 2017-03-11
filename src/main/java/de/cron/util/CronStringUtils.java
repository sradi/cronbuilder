package de.cron.util;

public class CronStringUtils {
	
	public static String removeWhitespacesAndEnclosingBrackets(String arraysToStringValue) {
		return arraysToStringValue.replaceAll("[\\[\\] ]", "");
	}

}
