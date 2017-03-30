package de.cron.elements.period;

import java.util.List;

import de.cron.CronExpression;

public interface CronPeriodPart {
	
	/**
	 * Wird nur aufgerufen, wenn die nächstfeinere Ebene nicht mehr zum Zeitraum gehört.
	 * @return
	 */
	List<CronExpression> getParts();
	
	List<CronExpression> getPartsInternal();
	
	int getLengthOfFromUnit();
	
	boolean isFromEqualToUntil();
	
	boolean hasIntermediateParts();

}
