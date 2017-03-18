package de.cron;

import java.util.List;

public interface ComplexCronDefinition {

	List<SimpleCronDefinition> getDayLevelCrons(SimpleCronDefinition cronOfSingleMonth);
	
	

}
