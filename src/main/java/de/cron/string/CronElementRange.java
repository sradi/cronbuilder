package de.cron.string;

public abstract class CronElementRange<T> {
	
	private T from;
	private T until;
	
	protected void setRange(T from, T until) {
		this.from = from;
		this.until = until;
	}
	
	protected abstract String getElementAsString(T element);

	@Override
	public String toString() {
		return getElementAsString(from) + "-" + getElementAsString(until);
	}

}
