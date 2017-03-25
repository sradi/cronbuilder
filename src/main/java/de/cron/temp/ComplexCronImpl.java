package de.cron.temp;

import java.util.ArrayList;
import java.util.List;

import de.cron.elements.CronElement;

public class ComplexCronImpl implements ComplexCron {
	
	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private List<CronElement> parts = new ArrayList<>();
	
	public ComplexCronImpl(CronElement firstPart) {
		this.parts.add(firstPart);
	}
	
	private ComplexCronImpl(ComplexCronImpl base) {
		this.parts = base.parts;
	}
	
	@Override
	public ComplexCron prepend(CronElement part) {
		ComplexCronImpl complexCron = new ComplexCronImpl(this);
		complexCron.parts.add(0, part);
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
