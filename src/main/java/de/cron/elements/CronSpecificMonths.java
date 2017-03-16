package de.cron.elements;

import java.time.Month;

public class CronSpecificMonths extends CronElementSpecificValues<Month> implements CronMonth {

	public CronSpecificMonths(Month[] months) {
		this.setElements(months);
	}
	public CronSpecificMonths(Month month) {
		this.setElements(new Month[] { month });
	}

	@Override
	protected String getElementAsString(Month element) {
		return Integer.valueOf(element.getValue()).toString();
	}

}
