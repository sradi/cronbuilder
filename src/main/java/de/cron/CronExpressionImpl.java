package de.cron;

import java.util.ArrayList;
import java.util.List;

import de.cron.elements.CronElement;

public class CronExpressionImpl implements CronExpression {
	
	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private List<CronElement> parts = new ArrayList<>();
	
	private CronExpressionImpl() {
	}
	
	public CronExpressionImpl(CronElement firstPart) {
		this.parts.add(firstPart);
	}
	
	/**
	 * Always return an immutable copy of the current object
	 */
	@Override
	public CronExpression prepend(CronElement newPart) {
		CronExpressionImpl complexCron = new CronExpressionImpl();
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
