package de.cron;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.units.Day;

/**
 * <b>Please note:</b> The year of LocalDate instances, used by this class, is
 * ignored, as cron definitions don't support year
 * 
 * @author sradi
 *
 */
class DayLevelComplexCronDefinition extends BaseComplexCronDefinition implements ComplexCronDefinition, Iterable<CronDefinition> {

	private List<CronDefinition> crons = new ArrayList<>();
	private Day fromDay;
	private Day untilDay;
	
	public DayLevelComplexCronDefinition(ComplexCronDefinition nextLevelCronDefinition, Day fromDay, Day untilDay) {
		super(nextLevelCronDefinition);
		Preconditions.checkArgument(fromDay.isBefore(untilDay));
		
		this.fromDay = fromDay;
		this.untilDay = untilDay;

		crons.addAll(getCrons());
	}

	@Override
	public SimpleCronDefinition getRangeElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CronDay getAllDays() {
		return new CronDayRange(Day.fromInt(fromDay.getIntValue()), Day.fromInt(untilDay.getIntValue()));
	}

	@Override
	public SimpleCronDefinition getFirstElement() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(super.cronDefinition.getFirstElement())
				.setDayDefinition(getAllDaysOfFirstMonth())
				.build();
	}
	
	private CronDay getAllDaysOfFirstMonth() {
		return new CronDayRange(Day.fromInt(fromDay.getIntValue()), Day.fromInt(super.cronDefinition.getMaxValueOfNextLevelElement()));
	}

	@Override
	public List<SimpleCronDefinition> getIntermediateElement() {
		return Arrays.asList(new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setDayDefinition(new CronDayRange(Day.fromInt(fromDay.getIntValue() + 1), Day.fromInt(untilDay.getIntValue() - 1)))
				.build());
	}

	@Override
	public SimpleCronDefinition getLastElement() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(super.cronDefinition.getLastElement())
				.setDayDefinition(getAllDaysOfLastMonth())
				.build();
	}
	
	private CronDay getAllDaysOfLastMonth() {
		return new CronDayRange(Day.fromInt(1), Day.fromInt(untilDay.getIntValue()));
	}

	private List<SimpleCronDefinition> addEveryDayToIntermediateMonthElements() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		for (SimpleCronDefinition monthLevelCron : super.cronDefinition.getIntermediateElement()) {
			new SimpleCronDefinition.SimpleCronDefinitionBuilder(monthLevelCron)
			.setDayDefinition(CronDay.EVERY_DAY)
			.build();
		}
		return crons;
	}

	@Override
	public List<SimpleCronDefinition> getCrons() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		SimpleCronDefinition firstMonthDefinition = super.cronDefinition.getFirstElement();
		
		if (super.cronDefinition.isSingleElement()) {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstMonthDefinition)
					.setDayDefinition(getAllDays())
					.build());
		} else {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstMonthDefinition)
					.setDayDefinition(getAllDaysOfFirstMonth())
					.build());
			if (super.cronDefinition.hasIntermediateElements()) {
				crons.addAll(addEveryDayToIntermediateMonthElements());
			}
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(super.cronDefinition.getLastElement())
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
	public int getCountOfElements() {
		if (super.cronDefinition.getCountOfElements() == 1) {
			if (fromDay.equals(untilDay)) {
				return 1;
			} else if (untilDay.getIntValue() - fromDay.getIntValue() == 1) {
				return 2;
			} else {
				return 3; // first specific month, month range, last specific
			}
		} else {
			return super.cronDefinition.getCountOfElements();
		}
	}

	@Override
	public boolean isSingleElement() {
		return super.cronDefinition.isSingleElement() && (fromDay.equals(untilDay));
	}

	@Override
	public boolean hasIntermediateElements() {
		return getCountOfElements() == 3;
	}

}
