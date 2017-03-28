package de.cron;

import java.util.ArrayList;
import java.util.List;

import de.cron.elements.CronElement;

public class CronExpression {
	
	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private List<CronElement> parts = new ArrayList<>();
	
	private CronExpression() {
	}
	
	public CronExpression(CronElement firstPart) {
		this.parts.add(firstPart);
	}
	
	/**
	 * Always return an immutable copy of the current object
	 */
	public CronExpression prepend(CronElement newPart) {
		CronExpression complexCron = new CronExpression();
		for (CronElement p : parts) {
			complexCron.parts.add(p);
		}
		complexCron.parts.add(0, newPart);
		return complexCron;
	}

	@Override
	public String toString() {
		StringBuilder cronStringBuilder = new StringBuilder();
		parts.forEach(p ->
			cronStringBuilder
				.append(p)
				.append(CRON_ELEMENT_SEPARATOR)
		);
		return cronStringBuilder.substring(0, cronStringBuilder.length()-1);
	}

}
