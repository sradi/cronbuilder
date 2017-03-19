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
		return Arrays.asList(getRangeElement());
	}
	
	@Override
	public SimpleCronDefinition getRangeElement() {
		if (isSingleElement()) {
			return new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
					.setMonthDefinition(new CronSpecificMonths(from))
					.build();
		} else {
			return new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
					.setMonthDefinition(new CronMonthRange(from, until))
					.build();
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
	public int getCountOfElements() {
		if (from.equals(until)) {
			return 1;
		} else if (until.getValue() - from.getValue() == 1) {
			return 2;
		} else {
			return 3; // first specific month, month range, last specific
		}
	}

	@Override
	public boolean isSingleElement() {
		if (from.equals(until)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasIntermediateElements() {
		return getCountOfElements() == 3;
	}

}
