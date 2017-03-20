package de.cron;

import java.util.List;

public interface ComplexCronDefinition {
	
	List<SimpleCronDefinition> getCrons();

	SimpleCronDefinition getFirstElementCronDefinition();
	
	List<SimpleCronDefinition> getIntermediateElementCronDefinition();
	
	SimpleCronDefinition getLastElementCronDefinition();
	
	int getMaxValueWithinFirstElement();
	
	boolean isFromEqualToUntil();
	
	boolean isFromSeveralBeforeUntil();

	boolean isFromOneBeforeUntil();

}
