package de.cron;

import java.util.List;

public interface ComplexCronDefinition {
	
	List<SimpleCronDefinition> getCrons();

	SimpleCronDefinition getRangeElement();
	
	SimpleCronDefinition getFirstElement();
	
	List<SimpleCronDefinition> getIntermediateElement();
	
	SimpleCronDefinition getLastElement();
	
	int getCountOfElements();
	
	boolean isSingleElement();
	
	boolean hasIntermediateElements();

}
