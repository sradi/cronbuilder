package de.cron.temp;

import java.util.List;

public interface ComplexCronPart {
	
	/**
	 * Wird nur aufgerufen, wenn die nächstfeinere Ebene nicht mehr zum Zeitraum gehört.
	 * @return
	 */
	List<ComplexCron> getParts();
	
	List<ComplexCron> getPartsInternal();
	
	boolean isFromEqualToUntil();
	
	boolean hasIntermediateParts();

}
