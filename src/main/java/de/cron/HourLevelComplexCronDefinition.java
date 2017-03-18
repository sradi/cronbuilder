package de.cron;

import java.util.List;

public class HourLevelComplexCronDefinition implements ComplexCronDefinition {
	
	private ComplexCronDefinition nextLevelCronDefinition;

	public HourLevelComplexCronDefinition(ComplexCronDefinition nextLevelCronDefinition) {
		this.nextLevelCronDefinition = nextLevelCronDefinition;
	}

	@Override
	public List<SimpleCronDefinition> getDayLevelCrons(SimpleCronDefinition cronOfSingleMonth) {
		// TODO Auto-generated method stub
		return null;
	}

}
