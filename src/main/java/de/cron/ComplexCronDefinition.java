package de.cron;

import java.util.List;

public interface ComplexCronDefinition {
	
	List<SimpleCronDefinition> getCrons();

	SimpleCronDefinition getRangeElement();
	
	SimpleCronDefinition getFirstElementCronDefinition();
	
	List<SimpleCronDefinition> getIntermediateElementCronDefinition();
	
	SimpleCronDefinition getLastElementCronDefinition();
	
	int getMaxValueWithinFirstElement();
	
	int getCountOfElements();
	
	boolean isSingleElement();
	
	boolean hasIntermediateElements();

}
