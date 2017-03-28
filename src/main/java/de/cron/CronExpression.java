package de.cron;

import de.cron.elements.CronElement;

public interface CronExpression {
	
	CronExpression prepend(CronElement part);

}
