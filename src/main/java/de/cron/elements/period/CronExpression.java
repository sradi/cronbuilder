package de.cron.elements.period;

import de.cron.elements.CronElement;

public interface CronExpression {
	
	CronExpression prepend(CronElement part);

}
