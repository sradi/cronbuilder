package de.cron.string;

public abstract class CronElementSpecificValues<T> {
	
	private T[] elements;
	
	protected void setElements(T[] elements) {
		this.elements = elements;
	}
	
	protected abstract String getElementAsString(T element);

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T e : elements) {
			sb.append(",").append(getElementAsString(e));
		}
		return sb.substring(1);
	}
	
}
