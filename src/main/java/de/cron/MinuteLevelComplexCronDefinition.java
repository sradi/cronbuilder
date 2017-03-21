package de.cron;

public class MinuteLevelComplexCronDefinition implements ComplexCronDefinition {
	
	private ComplexCronDefinition hourLevelCronDefinition;

	public MinuteLevelComplexCronDefinition(ComplexCronDefinition hourLevelCronDefinition) {
		this.hourLevelCronDefinition = hourLevelCronDefinition;
	}

}
