package de.cron.temp;

import de.cron.elements.CronElement;

public interface ComplexCron {
	
	ComplexCron prepend(CronElement part);

}
