package de.cron.temp;

import java.util.List;

public interface ComplexCronPart {
	
	/**
	 * Wird nur aufgerufen, wenn die nächstfeinere Ebene nicht mehr zum Zeitraum gehört.
	 * @return
	 */
	List<CronExpression> getParts();
	
	List<CronExpression> getPartsInternal();
	
	boolean isFromEqualToUntil();
	
	boolean hasIntermediateParts();

}
