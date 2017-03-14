package de.cron.string.month;

import java.time.Month;

import de.cron.string.CronElementSpecificValues;

public class CronSpecificMonths extends CronElementSpecificValues<Month> implements CronMonth {

	public CronSpecificMonths(Month[] months) {
		this.setElements(months);
	}

	@Override
	protected String getElementAsString(Month element) {
		return Integer.valueOf(element.getValue()).toString();
	}

}
