package de.cron.temp;

import java.util.List;

public interface ComplexCronPart {
	
	/**
	 * Wird nur aufgerufen, wenn die nächstfeinere Ebene nicht mehr zum Zeitraum gehört.
	 * @return
	 */
	List<ComplexCron> getParts();
	
	ComplexCron getFirstPart();

	List<ComplexCron> getIntermediateParts();
	
	ComplexCron getLastPart();
	
	boolean hasIntermediateParts();
	
	boolean isFromEqualToUntil();

}
