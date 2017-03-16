package de.cron.string.month;

import java.time.Month;

import de.cron.string.CronElementSpecificValues;

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
