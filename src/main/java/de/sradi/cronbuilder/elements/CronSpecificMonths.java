package de.sradi.cronbuilder.elements;

import java.time.Month;

public class CronSpecificMonths extends CronElementSpecificValues<Month> implements CronMonth {

	public CronSpecificMonths(Month... months) {
		this.setElements(months);
	}
	@Override
	protected String getElementAsString(Month element) {
		return Integer.valueOf(element.getValue()).toString();
	}

}
