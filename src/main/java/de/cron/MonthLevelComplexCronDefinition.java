package de.cron;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronMonthRange;
import de.cron.elements.CronSpecificMonths;

public class MonthLevelComplexCronDefinition implements ComplexCronDefinition {
	
	private Month from;
	private Month until;
	private SimpleCronDefinition baseCronDefinition;

	public MonthLevelComplexCronDefinition(SimpleCronDefinition baseCronDefinition, Month from, Month until) {
		Preconditions.checkArgument(from.compareTo(until) <= 0);
		this.from = from;
		this.until = until;
		this.baseCronDefinition = baseCronDefinition;
	}
	
	@Override
	public List<SimpleCronDefinition> getCrons() {
		if (isFromEqualToUntil()) {
			return Arrays.asList(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
					.setMonthDefinition(new CronSpecificMonths(from))
					.build());
		} else {
			return Arrays.asList(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
					.setMonthDefinition(new CronMonthRange(from, until))
					.build());
		}
	}
	
	@Override
	public SimpleCronDefinition getFirstElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
				.setMonthDefinition(new CronSpecificMonths(from))
				.build();
	}
	
	@Override
	public int getMaxValueWithinFirstElement() {
		return from.maxLength();
	}
	
	@Override
	public List<SimpleCronDefinition> getIntermediateElementCronDefinition() {
		return Arrays.asList(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
				.setMonthDefinition(new CronMonthRange(Month.of(from.getValue() + 1), Month.of(until.getValue() - 1)))
				.build());
	}

	@Override
	public SimpleCronDefinition getLastElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
					.setMonthDefinition(new CronSpecificMonths(until))
					.build();
	}

	@Override
	public boolean isFromEqualToUntil() {
		return from.equals(until);
	}
	
	@Override
	public boolean isFromOneBeforeUntil() {
		return !isFromEqualToUntil() && !isFromSeveralBeforeUntil();
	}

	@Override
	public boolean isFromSeveralBeforeUntil() {
		return countElementsBetween() > 1;
	}
	
	private int countElementsBetween() {
		Preconditions.checkArgument(from.compareTo(until) <= 0);
		return until.getValue() - from.getValue();
	}

}
