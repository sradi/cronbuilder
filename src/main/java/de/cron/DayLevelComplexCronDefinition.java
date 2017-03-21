package de.cron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronSpecificDays;
import de.cron.units.Day;

/**
 * <b>Please note:</b> The year of LocalDate instances, used by this class, is
 * ignored, as cron definitions don't support year
 * 
 * @author sradi
 *
 */
class DayLevelComplexCronDefinition implements ComplexCronDefinition, Iterable<CronDefinition> {

	private MonthLevelComplexCronDefinition monthLevelCronDefinition;
	private List<CronDefinition> crons = new ArrayList<>();
	private Day fromDay;
	private Day untilDay;
	
	public DayLevelComplexCronDefinition(MonthLevelComplexCronDefinition monthLevelCronDefinition, Day fromDay, Day untilDay) {
		this.monthLevelCronDefinition = monthLevelCronDefinition;
		if (monthLevelCronDefinition.isFromEqualToUntil()) {
			Preconditions.checkArgument(fromDay.isBefore(untilDay) || fromDay.equals(untilDay));
		}
		
		this.fromDay = fromDay;
		this.untilDay = untilDay;

		crons.addAll(getCrons());
	}

	private CronDay getAllDays() {
		if (isFromEqualToUntil()) {
			return new CronSpecificDays(fromDay);
		} else {
			return new CronDayRange(fromDay, untilDay);
		}
	}

	@Override
	public SimpleCronDefinition getFirstElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(monthLevelCronDefinition.getFirstElementCronDefinition())
				.setDayDefinition(getFirstDayOfFirstMonth())
				.build();
	}
	
	private CronDay getFirstDayOfFirstMonth() {
		return new CronSpecificDays(fromDay);
	}

	private CronDay getAllDaysOfFirstMonth() {
		return new CronDayRange(Day.fromInt(fromDay.getIntValue()), Day.fromInt(monthLevelCronDefinition.getMaxValueWithinFirstElement()));
	}

	@Override
	public int getMaxValueWithinFirstElement() {
		return fromDay.getMaxValue();
	}

	@Override
	public SimpleCronDefinition getIntermediateElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(monthLevelCronDefinition.getIntermediateElementCronDefinition())
				.setDayDefinition(new CronDayRange(Day.fromInt(fromDay.getIntValue() + 1), Day.fromInt(untilDay.getIntValue() - 1)))
				.build();
	}

	@Override
	public SimpleCronDefinition getLastElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(monthLevelCronDefinition.getLastElementCronDefinition())
				.setDayDefinition(getLastDayOfLastMonth())
				.build();
	}
	
	private CronDay getLastDayOfLastMonth() {
		return new CronSpecificDays(untilDay);
	}

	private CronDay getAllDaysOfLastMonth() {
		return new CronDayRange(Day.fromInt(1), Day.fromInt(untilDay.getIntValue()));
	}

	private List<SimpleCronDefinition> addEveryDayToIntermediateMonthElements() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(monthLevelCronDefinition.getIntermediateElementCronDefinition())
		.setDayDefinition(CronDay.EVERY_DAY)
		.build());
		return crons;
	}

	@Override
	public List<SimpleCronDefinition> getCrons() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		SimpleCronDefinition firstMonthDefinition = monthLevelCronDefinition.getFirstElementCronDefinition();
		
		if (monthLevelCronDefinition.isFromEqualToUntil()) {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstMonthDefinition)
					.setDayDefinition(getAllDays())
					.build());
		} else {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstMonthDefinition)
					.setDayDefinition(getAllDaysOfFirstMonth())
					.build());
			if (monthLevelCronDefinition.isFromSeveralBeforeUntil()) {
				crons.addAll(addEveryDayToIntermediateMonthElements());
			}
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(monthLevelCronDefinition.getLastElementCronDefinition())
					.setDayDefinition(getAllDaysOfLastMonth())
					.build());
		}

		return crons;
	}

	public CronDefinition get(int i) {
		// TODO return copy of CronDefinition, in order to make this class
		// immutable
		return crons.get(i);
	}

	public int size() {
		return crons.size();
	}

	@Override
	public Iterator<CronDefinition> iterator() {
		return crons.iterator();
	}

	@Override
	public boolean isFromEqualToUntil() {
		return monthLevelCronDefinition.isFromEqualToUntil() && (fromDay.equals(untilDay));
	}
	
	@Override
	public boolean isFromOneBeforeUntil() {
		return !isFromEqualToUntil() && !isFromSeveralBeforeUntil();
	}

	@Override
	public boolean isFromSeveralBeforeUntil() {
		return (!monthLevelCronDefinition.isFromEqualToUntil()) || (countElementsBetween() > 1);
	}
	
	private int countElementsBetween() {
		Preconditions.checkArgument(fromDay.isBefore(untilDay) || fromDay.equals(untilDay));
		return untilDay.getIntValue() - fromDay.getIntValue();
	}
	
}
