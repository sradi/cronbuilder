package de.cron.units;

abstract class CronUnit<U> {
	
	private int unit;
	
	protected void setUnit(int unit) {
		this.unit = unit;
	}

	protected int getUnit() {
		return unit;
	}

	public boolean isBefore(CronUnit<U> minute2) {
		return this.unit < minute2.unit;
	}

	public String toString() {
		return Integer.toString(this.unit);
	}

}
