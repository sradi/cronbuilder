package de.sradi.cronbuilder.units;

abstract class CronUnit<U> {
	
	private int unit;
	private int minValue;
	private int maxValue;
	private int length;
	
	protected void setUnit(int unit, int minValue, int maxValue, int length) {
		this.unit = unit;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.length = length;
	}

	public int getIntValue() {
		return unit;
	}
	
	public int getMinValue() {
		return minValue;
	}
	
	public int getMaxValue() {
		return maxValue;
	}
	
	public int getLength() {
		return length;
	}

	public boolean isBefore(CronUnit<U> minute2) {
		return this.unit < minute2.unit;
	}

	public String toString() {
		return Integer.toString(this.unit);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxValue;
		result = prime * result + minValue;
		result = prime * result + unit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		CronUnit other = (CronUnit) obj;
		if (maxValue != other.maxValue)
			return false;
		if (minValue != other.minValue)
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}

}
