package de.cron;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronHour;
import de.cron.elements.CronHourRange;
import de.cron.units.Hour;

public class HourLevelComplexCronDefinition implements ComplexCronDefinition {
	
	private ComplexCronDefinition dayLevelCronDefinition;
	private Hour fromHour;
	private Hour untilHour;
	
	public HourLevelComplexCronDefinition(ComplexCronDefinition dayLevelCronDefinition, Hour fromHour, Hour untilHour) {
		this.dayLevelCronDefinition = dayLevelCronDefinition;
		Preconditions.checkArgument(fromHour.isBefore(untilHour));
		
		this.fromHour = fromHour;
		this.untilHour = untilHour;
	}

	@Override
	public SimpleCronDefinition getRangeElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CronHour getAllHours() {
		return new CronHourRange(fromHour, untilHour);
	}

	@Override
	public SimpleCronDefinition getFirstElement() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getFirstElement())
				.setHourDefinition(getAllHoursOfFirstDay())
				.build();
	}
	
	private CronHour getAllHoursOfFirstDay() {
		return new CronHourRange(fromHour, Hour.fromInt(fromHour.getMaxValue()));
	}

	@Override
	public List<SimpleCronDefinition> getIntermediateElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleCronDefinition getLastElement() {
		return new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getLastElement())
				.setHourDefinition(getAllHoursOfLastDay())
				.build();
	}
	
	private CronHour getAllHoursOfLastDay() {
		return new CronHourRange(Hour.fromInt(1), untilHour);
	}

	private List<SimpleCronDefinition> addEveryHourToIntermediateDayElements() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		for (SimpleCronDefinition dayLevelCron : dayLevelCronDefinition.getIntermediateElement()) {
			new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCron)
			.setHourDefinition(CronHour.EVERY_HOUR)
			.build();
		}
		return crons;
	}

	@Override
	public List<SimpleCronDefinition> getCrons() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		SimpleCronDefinition firstDayDefinition = dayLevelCronDefinition.getFirstElement();
		
		if (dayLevelCronDefinition.isSingleElement()) {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstDayDefinition)
					.setHourDefinition(getAllHours())
					.build());
		} else {
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(firstDayDefinition)
					.setHourDefinition(getAllHoursOfFirstDay())
					.build());
			if (dayLevelCronDefinition.hasIntermediateElements()) {
				crons.addAll(addEveryHourToIntermediateDayElements());
			}
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(dayLevelCronDefinition.getLastElement())
					.setHourDefinition(getAllHoursOfLastDay())
					.build());
		}
		return crons;
	}

	@Override
	public int getCountOfElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSingleElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasIntermediateElements() {
		// TODO Auto-generated method stub
		return false;
	}

}
