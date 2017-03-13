package de.cron.string;

public abstract class CronElementSpecificValues<T> {
	
	protected String getStringRepresentation(T[] elements) {
		StringBuilder sb = new StringBuilder();
		for (T e : elements) {
			sb.append(",").append(getElementAsString(e));
		}
		return sb.substring(1);
	}
	
	protected abstract String getElementAsString(T element);
	
}
