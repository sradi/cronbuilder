package de.cron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronHour;
import de.cron.elements.CronHourRange;
import de.cron.elements.CronSpecificHours;
import de.cron.units.Hour;

public class HourLevelComplexCronDefinition implements ComplexCronDefinition, Iterable<CronDefinition> {
	
	private ComplexCronDefinition dayLevelCronDefinition;
	private List<CronDefinition> crons = new ArrayList<>();
	private Hour fromHour;
	private Hour untilHour;
	
	public HourLevelComplexCronDefinition(ComplexCronDefinition dayLevelCronDefinition, Hour fromHour, Hour untilHour) {
		this.dayLevelCronDefinition = dayLevelCronDefinition;
		Preconditions.checkArgument(fromHour.isBefore(untilHour));
		
		this.fromHour = fromHour;
		this.untilHour = untilHour;
		
		this.crons.addAll(getCrons());
	}

	private CronHour getAllHours() {
		if (isFromEqualToUntil()) {
			return new CronSpecificHours(fromHour);
		} else {
			return new CronHourRange(fromHour, untilHour);
		}
	}

	@Override
	public SimpleCronDefinition getFirstElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getFirstElementCronDefinition())
				.setHourDefinition(getAllHoursOfFirstDay())
				.build();
	}
	
	private CronHour getAllHoursOfFirstDay() {
		return new CronHourRange(fromHour, Hour.fromInt(fromHour.getMaxValue()));
	}

	@Override
	public SimpleCronDefinition getIntermediateElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setHourDefinition(new CronHourRange(Hour.fromInt(fromHour.getIntValue() + 1), Hour.fromInt(untilHour.getIntValue() - 1)))
				.build();
	}

	@Override
	public SimpleCronDefinition getLastElementCronDefinition() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getLastElementCronDefinition())
				.setHourDefinition(getAllHoursOfLastDay())
				.build();
	}
	
	private CronHour getAllHoursOfLastDay() {
		return new CronHourRange(Hour.fromInt(1), untilHour);
	}

	private List<SimpleCronDefinition> addEveryHourToIntermediateDayElements() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getIntermediateElementCronDefinition())
		.setHourDefinition(CronHour.EVERY_HOUR)
		.build();
		return crons;
	}

	@Override
	public List<SimpleCronDefinition> getCrons() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		SimpleCronDefinition firstDayDefinition = dayLevelCronDefinition.getFirstElementCronDefinition();
		
		if (dayLevelCronDefinition.isFromEqualToUntil()) {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstDayDefinition)
					.setHourDefinition(getAllHours())
					.build());
		} else {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstDayDefinition)
					.setHourDefinition(getAllHoursOfFirstDay())
					.build());
			if (dayLevelCronDefinition.isFromSeveralBeforeUntil()) {
				crons.addAll(addEveryHourToIntermediateDayElements());
			}
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getLastElementCronDefinition())
					.setHourDefinition(getAllHoursOfLastDay())
					.build());
		}
		return crons;
	}
	
	@Override
	public int getMaxValueWithinFirstElement() {
		return fromHour.getMaxValue();
	}

	@Override
	public boolean isFromEqualToUntil() {
		return dayLevelCronDefinition.isFromEqualToUntil() && (fromHour.equals(untilHour));
	}
	
	@Override
	public boolean isFromOneBeforeUntil() {
		return !isFromEqualToUntil() && !isFromSeveralBeforeUntil();
	}

	@Override
	public boolean isFromSeveralBeforeUntil() {
		return (!dayLevelCronDefinition.isFromEqualToUntil()) && (countElementsBetween() > 1);
	}
	
	private int countElementsBetween() {
		Preconditions.checkArgument(fromHour.isBefore(untilHour) || fromHour.equals(untilHour));
		return untilHour.getIntValue() - fromHour.getIntValue();
	}

	@Override
	public Iterator<CronDefinition> iterator() {
		return crons.iterator();
	}
	
	public int size() {
		return crons.size();
	}

	public CronDefinition get(int i) {
		// TODO return copy of CronDefinition, in order to make this class
		// immutable
		return crons.get(i);
	}

}
