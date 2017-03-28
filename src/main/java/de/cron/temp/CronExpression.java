package de.cron.temp;

import de.cron.elements.CronElement;

public interface CronExpression {
	
	CronExpression prepend(CronElement part);

}
